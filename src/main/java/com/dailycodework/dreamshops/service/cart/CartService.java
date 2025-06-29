package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.dto.CartDto;
import com.dailycodework.dreamshops.dto.CartItemDto;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import com.dailycodework.dreamshops.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
    }

    @Override
    public CartDto getCartDto(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        List<CartItemDto> cartItemDtos = cart.getCartItems().stream().map(item-> {
            ProductDto productDto = productService.convertToDto(item.getProduct());
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setProduct(productDto);
            cartItemDto.setQuantity(item.getQuantity());
            cartItemDto.setUnitPrice(item.getUnitPrice());
            cartItemDto.setItemId(item.getId());
            return cartItemDto;
        }).toList();

        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalAmount(cart.getTotalAmount());
        cartDto.setCartItems(new HashSet<>(cartItemDtos));
        return cartDto;


    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        System.out.println("Using native SQL to delete cart");

        int deletedItems = cartItemRepository.deleteByCartIdNative(id);
        System.out.println("Deleted " + deletedItems + " cart items");

        int deletedCarts = cartRepository.deleteByIdNative(id);
        System.out.println("Deleted " + deletedCarts + " carts");

        boolean exists = cartRepository.existsById(id);
        System.out.println("Cart still exists: " + exists);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            return cart;
        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

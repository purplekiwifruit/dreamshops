package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.dto.CartDto;
import com.dailycodework.dreamshops.dto.CartItemDto;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import com.dailycodework.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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



    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
//        Long newCartId = cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
        return  cartRepository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return null;
    }
}

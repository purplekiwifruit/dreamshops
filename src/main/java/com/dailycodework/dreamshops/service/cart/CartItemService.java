package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the cart
        //2. Get the product
        //3. Check if the product already exists in cart
        //4. If yes, then increase quantity
        //5. If no, then initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        System.out.println("cart: " + cart);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                                .filter(item -> item.getProduct().getId().equals(productId))
                                .findFirst()
                                .orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else
        {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart); // Cascade will save cart items when the cart is saved.


    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        if(itemToRemove != null){
            cart.removeItem(itemToRemove); //itemToRemove.cart is set to null
            cartRepository.save(cart); //// orphanRemoval will delete the item which has null for cart field.
        }

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems().stream()
                                .filter(item -> item.getProduct().getId().equals(productId))
                                .findFirst()
                                .ifPresent(item -> {
                                            item.setQuantity(quantity);
                                            item.setUnitPrice(item.getProduct().getPrice());
                                            item.setTotalPrice();
                                        }
                                );
        cart.updateTotalAmount();
        cartRepository.save(cart);

    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Product not found."));
    }
}

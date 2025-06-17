package com.dailycodework.dreamshops.controller;
import com.dailycodework.dreamshops.dto.CartDto;
import com.dailycodework.dreamshops.exception.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{id}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long id) {
        try {
            CartDto cart = cartService.getCartDto(id);
            return ResponseEntity.ok(new ApiResponse("Found!", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: Failed to fetch cart", null));
        }
    }

    @DeleteMapping("/{id}/clear-cart")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) {
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok(new ApiResponse("Cart cleared successfully!", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: Failed to fetch cart", null));
        }
    }

    @GetMapping("/{id}/total-price")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long id) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(id);
            return ResponseEntity.ok(new ApiResponse("Found!", totalPrice));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error: Failed to fetch cart", null));
        }
    }
}

package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDto> cartItems;
}

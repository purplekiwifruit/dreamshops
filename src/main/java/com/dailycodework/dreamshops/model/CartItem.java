package com.dailycodework.dreamshops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    public void setTotalPrice() {
        this.totalPrice = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}

package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCartId(Long cartId);

    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId", nativeQuery = true)
    int deleteByCartIdNative(@Param("cartId") Long cartId);
}

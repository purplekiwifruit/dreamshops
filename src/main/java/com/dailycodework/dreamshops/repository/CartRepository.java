package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

    @Modifying
    @Query(value = "DELETE FROM cart WHERE id = :cartId", nativeQuery = true)
    int deleteByIdNative(@Param("cartId") Long cartId);
}

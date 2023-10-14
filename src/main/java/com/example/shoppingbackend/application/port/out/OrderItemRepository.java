package com.example.shoppingbackend.application.port.out;

import com.example.shoppingbackend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

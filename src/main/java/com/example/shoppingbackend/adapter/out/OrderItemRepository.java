package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.persistence.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}

package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}

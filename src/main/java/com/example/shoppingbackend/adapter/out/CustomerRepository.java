package com.example.shoppingbackend.adapter.out;

import com.example.shoppingbackend.adapter.persistence.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}

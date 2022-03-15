package com.code.challange.solution.repository;

import com.code.challange.solution.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstName(String firstName);

    Customer findByCustomerId(Long personId);
}

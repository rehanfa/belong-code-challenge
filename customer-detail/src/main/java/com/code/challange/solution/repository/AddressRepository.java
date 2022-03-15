package com.code.challange.solution.repository;

import com.code.challange.solution.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

}

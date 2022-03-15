package com.code.challange.solution.service;

import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    Page<Phone> findAllPhoneNumbers(Pageable pageable);

    Customer findCustomerById(Long id);

    List<Customer> findCustomerByFirstName(String firstName);

    Phone activateContactNumber(Long contactNumberId);

    Customer saveCustomer(Customer person);

}

package com.code.challange.proxy.service;

import com.code.challange.proxy.common.JwtToken;
import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.CustomerList;
import com.code.challange.proxy.dto.Phone;
import com.code.challange.proxy.dto.PhoneList;
import org.springframework.http.ResponseEntity;

public interface ProxyService {
    SecurityToken hasAccess(String securityToken, String serviceName);

    ResponseEntity<JwtToken> login(SecurityToken securityToken);

    ResponseEntity<Customer> findCustomerById(Long id);

    ResponseEntity<CustomerList> findCustomerByFirstName(String firstName);

    ResponseEntity<Phone> activateContactNumber(Long contactNumberId);

    ResponseEntity<PhoneList> findAllPhoneNumbers(Integer page, Integer size);
}

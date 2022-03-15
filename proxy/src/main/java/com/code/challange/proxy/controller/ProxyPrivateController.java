package com.code.challange.proxy.controller;

import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.CustomerList;
import com.code.challange.proxy.dto.Phone;
import com.code.challange.proxy.dto.PhoneList;
import com.code.challange.proxy.service.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@PreAuthorize("hasRole('USER')")
@RequestMapping("/private")
public class ProxyPrivateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyPrivateController.class);

    @Autowired
    private ProxyService proxyService;

    @GetMapping(value = "/v1/customer/id/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long customerId) {
        ResponseEntity<Customer> retVal;
        try {
            retVal = proxyService.findCustomerById(customerId);
        } catch (Exception ex) {
            LOGGER.error("error in findCustomerById endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @GetMapping(value = "/v1/customer/name/{customerFirstName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerList> findCustomerByFirstName(@PathVariable String customerFirstName) {
        ResponseEntity<CustomerList> retVal;
        try {
            retVal = proxyService.findCustomerByFirstName(customerFirstName);
        } catch (Exception ex) {
            LOGGER.error("error in findCustomerByFirstName endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @GetMapping(value = "/v1/customer/all/phones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneList> getAllContactNumbers(@RequestParam(required = false) Integer page,

                                                          @RequestParam(required = false) Integer size) {
        ResponseEntity<PhoneList> retVal;
        try {
            retVal = proxyService.findAllPhoneNumbers(page, size);
        } catch (Exception ex) {
            LOGGER.error("error in getAllContactNumbers endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @PostMapping(value = "/v1/phone/activate/{contactNumberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Phone> activateContactNumber(@PathVariable Long contactNumberId) {
        ResponseEntity<Phone> retVal;
        try {
            retVal = proxyService.activateContactNumber(contactNumberId);
        } catch (Exception ex) {
            LOGGER.error("error in activateContactNumber endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

}

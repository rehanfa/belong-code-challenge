package com.code.challange.solution.controller;

import com.code.challange.solution.common.CustomerList;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import com.code.challange.solution.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableEurekaClient
public class CustomerController {
    static final int DEFAULT_PAGE_SIZE = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/internal/v1/customer/id/{customerId}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long customerId) {
        ResponseEntity<Customer> retVal;
        try {
            Customer customer = customerService.findCustomerById(customerId);
            if (customer != null) {
                retVal = new ResponseEntity<>(customer, HttpStatus.OK);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("error in findCustomerById endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @GetMapping(value = "/internal/v1/customer/name/{customerFirstName}")
    public ResponseEntity<CustomerList> findCustomerByIdFirstName(@PathVariable String customerFirstName) {
        ResponseEntity<CustomerList> retVal;
        try {
            List<Customer> customers = customerService.findCustomerByFirstName(customerFirstName);
            CustomerList customerList = new CustomerList();
            customerList.setCustomers(customers);
            if (customers != null) {
                retVal = new ResponseEntity<>(customerList, HttpStatus.OK);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("error in findCustomerByFirstName endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @GetMapping(value = "/internal/v1/customer/all/phones")
    public ResponseEntity<Page<Phone>> getAllContactNumbers(@RequestParam(required = false) Integer page,
                                                            @RequestParam(required = false) Integer size) {
        ResponseEntity<Page<Phone>> retVal;
        try {
            if (size == null) {
                size = DEFAULT_PAGE_SIZE;
            }
            if (page == null) {
                page = 0;
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<Phone> phones = customerService.findAllPhoneNumbers(pageable);
            if (phones != null) {
                retVal = new ResponseEntity<>(phones, HttpStatus.OK);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("error in getAllContactNumbers endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @PostMapping(value = "/internal/v1/phone/activate/{contactNumberId}")
    public ResponseEntity<Phone> activateContactNumber(@PathVariable Long contactNumberId) {
        ResponseEntity<Phone> retVal;
        try {
            Phone phone = customerService.activateContactNumber(contactNumberId);
            if (phone != null) {
                retVal = new ResponseEntity<>(phone, HttpStatus.OK);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("error in activateContactNumber endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }


}

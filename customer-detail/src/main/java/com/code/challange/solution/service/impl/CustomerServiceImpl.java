package com.code.challange.solution.service;

import com.code.challange.solution.common.Status;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import com.code.challange.solution.repository.CustomerRepository;
import com.code.challange.solution.repository.PhoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    private final PhoneRepository phoneRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, PhoneRepository phoneRepository) {
        this.customerRepository = customerRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional(readOnly = true)
    public Page<Phone> findAllPhoneNumbers(Pageable pageable) {
        return phoneRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public Customer findCustomerById(Long id) {
        return customerRepository.findByCustomerId(id);
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByFirstName(String firstName) {
        return customerRepository.findByFirstName(firstName);
    }

    @Transactional()
    public Phone activateContactNumber(Long contactNumberId) {
        Phone retVal = null;
        Phone phone = phoneRepository.findByPhoneId(contactNumberId);
        if (phone != null) {
            phone.setStatus(Status.ACTIVE);
            phone.setModifiedDate(new Date());
            retVal = phoneRepository.save(phone);
        }
        return retVal;
    }

    public Customer saveCustomer(Customer person) {
        Customer retVal;
        retVal = customerRepository.save(person);
        return retVal;
    }

}

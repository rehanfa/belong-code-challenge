package com.code.challange.solution.service.Impl;

import com.code.challange.solution.domain.Address;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import com.code.challange.solution.repository.CustomerRepository;
import com.code.challange.solution.repository.PhoneRepository;
import com.code.challange.solution.service.CustomerService;
import com.code.challange.solution.service.CustomerServiceImpl;
import com.code.challange.solution.util.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CustomerServiceImplTest {

    Customer customer = null;
    Phone phone = null;
    Address address = null;
    List<Customer> customerList = null;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PhoneRepository phoneRepository;

    private CustomerService customerService = null;

    @Before
    public void setup() {
        customerService = new CustomerServiceImpl(customerRepository, phoneRepository);
        customer = new Customer();
        address = new Address();
        phone = new Phone();
        TestUtil.setup(customer, address, phone);
        Mockito.when(customerRepository.findByCustomerId(4l))
                .thenReturn(customer);
        customerList = new ArrayList<>(1);
        customerList.add(customer);
        Mockito.when(customerRepository.findByFirstName("some-name"))
                .thenReturn(customerList);

        Mockito.when(phoneRepository.findByPhoneId(4l))
                .thenReturn(phone);

        Mockito.when(phoneRepository.save(phone))
                .thenReturn(phone);
    }

    @Test
    public void findCustomerById_success() {
        Customer response = customerService.findCustomerById(4l);
        Assertions.assertThat(response.getCustomerId())
                .isEqualTo(customer.getCustomerId());
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findByCustomerId(4l);
        Mockito.reset(customerRepository);
    }

    @Test
    public void findCustomerById_failure() {
        Customer response = customerService.findCustomerById(6l);
        Assertions.assertThat(response)
                .isEqualTo(null);
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findByCustomerId(6l);
        Mockito.reset(customerRepository);
    }


    @Test
    public void findCustomerByName_success() {
        List<Customer> response = customerService.findCustomerByFirstName("some-name");
        Assertions.assertThat(response.get(0).getCustomerId())
                .isEqualTo(customer.getCustomerId());
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findByFirstName("some-name");
        Mockito.reset(customerRepository);
    }

    @Test
    public void findCustomerByName_failure() {
        List<Customer> response = customerService.findCustomerByFirstName("some-other-name");
        Assertions.assertThat(response.size())
                .isEqualTo(0);
        Mockito.verify(customerRepository, VerificationModeFactory.times(1)).findByFirstName("some-other-name");
        Mockito.reset(customerRepository);
    }

    @Test
    public void activateContactNumber_success() {
        Phone response = customerService.activateContactNumber(4l);
        Assertions.assertThat(response.getPhoneId())
                .isEqualTo(phone.getPhoneId());
        Mockito.verify(phoneRepository, VerificationModeFactory.times(1)).findByPhoneId(4l);
        Mockito.verify(phoneRepository, VerificationModeFactory.times(1)).save(phone);
        Mockito.reset(phoneRepository);
    }

    @Test
    public void activateContactNumber_failure() {
        Phone response = customerService.activateContactNumber(6l);
        Assertions.assertThat(response)
                .isEqualTo(null);
        Mockito.verify(phoneRepository, VerificationModeFactory.times(1)).findByPhoneId(6l);
        Mockito.verify(phoneRepository, VerificationModeFactory.times(0)).save(phone);
        Mockito.reset(phoneRepository);
    }
}

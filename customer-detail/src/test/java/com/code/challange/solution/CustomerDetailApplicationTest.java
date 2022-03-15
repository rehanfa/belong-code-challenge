package com.code.challange.solution;

import com.code.challange.solution.controller.CustomerController;
import com.code.challange.solution.domain.Address;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import com.code.challange.solution.service.CustomerService;
import com.code.challange.solution.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerDetailApplicationTest {
    Customer customer = null;
    Phone phone = null;
    Address address = null;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setup() {
        customer = new Customer();
        address = new Address();
        phone = new Phone();
        TestUtil.setup(customer, address, phone);
    }

    @Test
    public void findCustomerById_success() throws Exception {

        given(customerService.findCustomerById(4l)).willReturn(customer);
        mvc.perform(get("/internal/v1/customer/id/4")).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(customer.getLastName())));

        verify(customerService, VerificationModeFactory.times(1)).findCustomerById(Mockito.any());
        reset(customerService);
    }

    @Test
    public void findCustomerById_failure() throws Exception {

        given(customerService.findCustomerById(6l)).willReturn(null);
        mvc.perform(get("/internal/v1/customer/id/6")).andExpect(status().is4xxClientError());

        verify(customerService, VerificationModeFactory.times(1)).findCustomerById(Mockito.any());
        reset(customerService);
    }

    @Test
    public void findCustomerByFirstName_success() throws Exception {
        List<Customer> customers = new ArrayList<>(1);
        customers.add(customer);
        given(customerService.findCustomerByFirstName("some-name")).willReturn(customers);
        mvc.perform(get("/internal/v1/customer/name/some-name")).andExpect(status().isOk())
                .andExpect(jsonPath("$.customers[0].firstName", is(customer.getFirstName())))
                .andExpect(jsonPath("$.customers[0].lastName", is(customer.getLastName())));

        verify(customerService, VerificationModeFactory.times(1)).findCustomerByFirstName(Mockito.any());
        reset(customerService);
    }

    @Test
    public void findCustomerByFirstName_failure() throws Exception {
        given(customerService.findCustomerByFirstName("some-other-name")).willReturn(null);
        mvc.perform(get("/internal/v1/customer/name/some-other-name")).andExpect(status().is4xxClientError());
        verify(customerService, VerificationModeFactory.times(1)).findCustomerByFirstName(Mockito.any());
        reset(customerService);
    }


    @Test
    public void activateContactNumber_success() throws Exception {

        given(customerService.activateContactNumber(4l)).willReturn(phone);
        mvc.perform(post("/internal/v1/phone/activate/4")).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.phoneId", is(phone.getPhoneId())))
                .andExpect(jsonPath("$.status", is(phone.getStatus().toString())));

        verify(customerService, VerificationModeFactory.times(1)).activateContactNumber(4l);
        reset(customerService);
    }

    @Test
    public void activateContactNumber_failure() throws Exception {

        given(customerService.activateContactNumber(6l)).willReturn(null);
        mvc.perform(post("/internal/v1/phone/activate/6")).andExpect(status().is4xxClientError());

        verify(customerService, VerificationModeFactory.times(1)).activateContactNumber(6l);
        reset(customerService);
    }

    @Test
    public void findAllPhoneNumbers_success() throws Exception {
        Pageable pageable = PageRequest.of(4, 10);
        Page<Phone> phones = Mockito.mock(Page.class);

        given(customerService.findAllPhoneNumbers(any())).willReturn(phones);
        mvc.perform(get("/internal/v1/customer/all/phones")).andExpect(status().isOk());

        verify(customerService, VerificationModeFactory.times(1)).findAllPhoneNumbers(Mockito.any());
        reset(customerService);
    }

    @Test
    public void findAllPhoneNumbers_failure() throws Exception {
        Pageable pageable = PageRequest.of(6, 10);

        given(customerService.findAllPhoneNumbers(pageable)).willReturn(null);
        mvc.perform(get("/internal/v1/customer/all/phones")).andExpect(status().is4xxClientError());

        verify(customerService, VerificationModeFactory.times(1)).findAllPhoneNumbers(Mockito.any());
        reset(customerService);
    }

}

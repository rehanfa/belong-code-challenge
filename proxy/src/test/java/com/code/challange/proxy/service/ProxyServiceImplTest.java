package com.code.challange.proxy.service;

import com.code.challange.proxy.common.JwtToken;
import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.dto.Address;
import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.CustomerList;
import com.code.challange.proxy.dto.Phone;
import com.code.challange.proxy.dto.PhoneList;
import com.code.challange.proxy.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(ProxyServiceImpl.class)
public class ProxyServiceImplTest {
    private ProxyServiceImpl proxyService = null;
    private String customerServiceUrl = "http://customer-service/api/internal/v1/";
    private String securityServiceUrl = "http://security-service/api/";
    private Address address = null;
    @MockBean
    private RestTemplate restTemplate;
    private SecurityToken securityToken = null;
    private String tokenString = null;
    private JwtToken token = null;
    private Customer customer = null;
    private Phone phone = null;

    @Before
    public void setup() {
        proxyService = new ProxyServiceImpl();
        securityToken = new SecurityToken();
        securityToken.setUserName("some-user-name");
        securityToken.setPassword("some-user-password");
        tokenString = "some-token";
        token = new JwtToken(tokenString);
        proxyService.setRestTemplate(restTemplate);
        customer = new Customer();
        address = new Address();
        phone = new Phone();
        TestUtil.setup(customer, address, phone, null, null);

    }

    @Test
    public void hasAccess() {
        given(restTemplate.getForObject(securityServiceUrl + "token/service", SecurityToken.class)).willReturn(securityToken);
        SecurityToken token = proxyService.hasAccess("token", "service");
        assertEquals(token, securityToken);
    }

    @Test
    public void activateContactNumber() {
        ResponseEntity responseEntity = new ResponseEntity(phone, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(customerServiceUrl + "phone/activate/4",
                        HttpMethod.POST,
                        getHttpEntity(),
                        Phone.class))
                .thenReturn(responseEntity);
        ResponseEntity response = proxyService.activateContactNumber(4l);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), phone);
    }

    @Test
    public void login() {
        ResponseEntity<JwtToken> responseEntity = new ResponseEntity(token, HttpStatus.OK);
        HttpEntity httpEntity = getHttpEntity(securityToken);
        Mockito.when(restTemplate.exchange(securityServiceUrl + "login/",
                        HttpMethod.POST,
                        httpEntity,
                        JwtToken.class))
                .thenReturn(responseEntity);
        ResponseEntity<JwtToken> response = proxyService.login(securityToken);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getAccessToken(), tokenString);
    }

    @Test
    public void findCustomerByFirstName() {
        CustomerList customerList = new CustomerList();
        customerList.getCustomers().add(customer);
        ResponseEntity responseEntity = new ResponseEntity(customerList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(customerServiceUrl + "customer/name/some-name",
                        HttpMethod.GET,
                        getHttpEntity(),
                        CustomerList.class))
                .thenReturn(responseEntity);
        ResponseEntity<CustomerList> response = proxyService.findCustomerByFirstName("some-name");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), customerList);
    }

    @Test
    public void findCustomerById() {
        ResponseEntity responseEntity = new ResponseEntity(customer, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(customerServiceUrl + "customer/id/4",
                        HttpMethod.GET,
                        getHttpEntity(),
                        Customer.class))
                .thenReturn(responseEntity);
        ResponseEntity<Customer> response = proxyService.findCustomerById(4l);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), customer);
    }

    @Test
    public void findAllPhoneNumbers() {
        PhoneList phoneList = new PhoneList();
        phoneList.getContent().add(phone);
        ResponseEntity responseEntity = new ResponseEntity(phoneList, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(customerServiceUrl + "customer/all/phones?page=10&size=4",
                        HttpMethod.GET,
                        getHttpEntity(),
                        PhoneList.class))
                .thenReturn(responseEntity);
        ResponseEntity response = proxyService.findAllPhoneNumbers(10, 4);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), phoneList);
    }

    private <T> HttpEntity<T> getHttpEntity(T body) {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<?> getHttpEntity() {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
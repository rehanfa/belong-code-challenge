package com.code.challange.proxy.service;

import com.code.challange.proxy.common.JwtToken;
import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.CustomerList;
import com.code.challange.proxy.dto.Phone;
import com.code.challange.proxy.dto.PhoneList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProxyServiceImpl implements ProxyService {
    private String customerServiceUrl = "http://customer-service/api/internal/v1/";
    private String securityServiceUrl = "http://security-service/api/";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SecurityToken hasAccess(String securityToken, String serviceName) {
        SecurityToken retVal = restTemplate.getForObject(securityServiceUrl + securityToken + "/" + serviceName,
                SecurityToken.class);
        return retVal;
    }

    @Override
    public ResponseEntity<JwtToken> login(SecurityToken securityToken) {
        ResponseEntity<JwtToken> retVal;
        HttpEntity httpEntity = getHttpEntity(securityToken);

        String urlStr = securityServiceUrl + "login/";
        retVal = restTemplate.exchange(
                urlStr,
                HttpMethod.POST,
                httpEntity,
                JwtToken.class
        );
        return retVal;
    }

    @Override
    public ResponseEntity<Customer> findCustomerById(Long id) {
        ResponseEntity<Customer> retVal;
        String urlStr = customerServiceUrl + "customer/id/" + id;
        HttpEntity<?> entity = getHttpEntity();
        retVal = restTemplate.exchange(
                urlStr,
                HttpMethod.GET,
                entity,
                Customer.class
        );
        return retVal;
    }

    @Override
    public ResponseEntity<CustomerList> findCustomerByFirstName(String firstName) {
        ResponseEntity<CustomerList> retVal;
        String urlStr = customerServiceUrl + "customer/name/" + firstName;
        HttpEntity<?> entity = getHttpEntity();
        retVal = restTemplate.exchange(
                urlStr,
                HttpMethod.GET,
                entity,
                CustomerList.class
        );
        return retVal;
    }

    @Override
    public ResponseEntity<Phone> activateContactNumber(Long contactNumberId) {
        ResponseEntity<Phone> retVal;
        String urlStr = customerServiceUrl + "phone/activate/" + contactNumberId;
        retVal = restTemplate.exchange(
                urlStr,
                HttpMethod.POST,
                getHttpEntity(),
                Phone.class
        );
        return retVal;
    }

    @Override
    public ResponseEntity<PhoneList> findAllPhoneNumbers(Integer page, Integer size) {
        ResponseEntity<PhoneList> retVal;
        String urlStr = customerServiceUrl + "customer/all/phones";
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(urlStr)
                .queryParam("page", page)
                .queryParam("size", size);
        retVal = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                getHttpEntity(),
                PhoneList.class);
        return retVal;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<?> getHttpEntity() {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(headers);
    }

    private <T> HttpEntity<T> getHttpEntity(T body) {
        HttpHeaders headers = getHttpHeaders();
        return new HttpEntity<>(body, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}

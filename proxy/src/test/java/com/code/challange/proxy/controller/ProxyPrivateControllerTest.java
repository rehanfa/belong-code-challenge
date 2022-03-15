package com.code.challange.proxy.controller;

import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.dto.Address;
import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.Phone;
import com.code.challange.proxy.service.ProxyService;
import com.code.challange.proxy.util.TestUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProxyPrivateController.class)
public class ProxyPrivateControllerTest {
    HttpHeaders httpHeaders = null;
    SecurityToken securityToken = null;
    private Customer customer = null;
    private Phone phone = null;
    private Address address = null;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProxyService proxyService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        customer = new Customer();
        address = new Address();
        phone = new Phone();
        httpHeaders = new HttpHeaders();
        securityToken = new SecurityToken();
        TestUtil.setup(customer, address, phone, httpHeaders, securityToken);
    }


    @Test
    public void activateContactNumber_success() throws Exception {
        ResponseEntity<Phone> responseEntity = new ResponseEntity(phone, HttpStatus.OK);
        given(proxyService.activateContactNumber(Mockito.any())).willReturn(responseEntity);
        given(proxyService.hasAccess(Mockito.any(), Mockito.anyString())).willReturn(securityToken);
        mvc.perform(post("/private/v1/phone/activate/1").headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).content
                        (getJson(phone))).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.phoneId", is(phone.getPhoneId())))
                .andExpect(jsonPath("$.status", is(phone.getStatus().toString())));
        verify(proxyService, VerificationModeFactory.times(1)).activateContactNumber(Mockito.any());
        reset(proxyService);
    }

    @Test
    public void activateContactNumber_failure() throws Exception {
        ResponseEntity<Phone> responseEntity = new ResponseEntity(null, HttpStatus.NOT_FOUND);
        given(proxyService.activateContactNumber(Mockito.any())).willReturn(responseEntity);
        given(proxyService.hasAccess(Mockito.any(), Mockito.anyString())).willReturn(new SecurityToken());
        mvc.perform(post("/private/v1/phone/activate/1").headers(httpHeaders).contentType(MediaType.APPLICATION_JSON).content
                (getJson(phone))).andExpect(status().is4xxClientError());
        verify(proxyService, VerificationModeFactory.times(0)).activateContactNumber(Mockito.any());
        reset(proxyService);
    }

    private String getJson(Phone address) throws IOException {
        String retVal;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        retVal = objectMapper.writeValueAsString(address);
        return retVal;
    }

}
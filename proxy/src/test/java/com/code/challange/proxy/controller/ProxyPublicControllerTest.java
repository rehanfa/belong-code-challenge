package com.code.challange.proxy.controller;

import com.code.challange.proxy.common.JwtToken;
import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.common.SecurityTokenConstants;
import com.code.challange.proxy.dto.Address;
import com.code.challange.proxy.service.ProxyService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProxyPublicController.class)
public class ProxyPublicControllerTest {

    private Address address = null;
    private HttpHeaders httpHeaders = null;
    private SecurityToken securityToken = null;
    private String encodedString = "some-encoded-string";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProxyService proxyService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        encodedString = "some-encoded-string";
        httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityTokenConstants.HEADER, SecurityTokenConstants.PREFIX + "some-token");
        securityToken = new SecurityToken();
        securityToken.setUserName("some-user");
        securityToken.setPassword("some-password");
        List<String> roles = new ArrayList<>(1);
        roles.add("USER");
        securityToken.setRoles(roles);
    }

    @Test
    public void login_success() throws Exception {
        JwtToken jwtToken = new JwtToken(encodedString);
        ResponseEntity responseEntity = new ResponseEntity(jwtToken, HttpStatus.OK);
        given(passwordEncoder.encode(Mockito.any())).willReturn(encodedString);
        given(proxyService.login(Mockito.any())).willReturn(responseEntity);
        mvc.perform(post("/public/login").contentType(MediaType.APPLICATION_JSON).content(getJson(securityToken)))
                .andExpect(status().is2xxSuccessful());
        verify(passwordEncoder, VerificationModeFactory.atLeast(1)).encode(Mockito.any());
        verify(proxyService, VerificationModeFactory.atLeast(1)).login(Mockito.any());
        reset(passwordEncoder);
        reset(proxyService);
    }

    @Test
    public void login_failure() throws Exception {
        JwtToken jwtToken = new JwtToken(encodedString);
        ResponseEntity responseEntity = new ResponseEntity(jwtToken, HttpStatus.BAD_REQUEST);
        given(passwordEncoder.encode(Mockito.any())).willReturn(encodedString);
        given(proxyService.login(Mockito.any())).willReturn(responseEntity);
        mvc.perform(post("/public/login").contentType(MediaType.APPLICATION_JSON).content(getJson(new SecurityToken())))
                .andExpect(status().is4xxClientError());
        verify(passwordEncoder, VerificationModeFactory.atLeast(0)).encode(Mockito.any());
        verify(proxyService, VerificationModeFactory.times(0)).login(Mockito.any());
        reset(passwordEncoder);
        reset(proxyService);
    }

    private String getJson(SecurityToken securityToken) throws IOException {
        String retVal;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        retVal = objectMapper.writeValueAsString(securityToken);
        return retVal;
    }
}
package com.code.challange.proxy.controller;

import com.code.challange.proxy.common.JwtToken;
import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.service.ProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@RequestMapping("/public")
public class ProxyPublicController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyPublicController.class);

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String customerServiceUrl = "http://customer-service/api/";

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtToken> login(@RequestBody SecurityToken securityToken) {
        ResponseEntity<JwtToken> retVal;
        try {
            if (securityToken != null && securityToken.getUserName() != null && securityToken.getPassword() != null) {
                securityToken.setPassword(passwordEncoder.encode(securityToken.getPassword()));
                retVal = proxyService.login(securityToken);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            LOGGER.error("error in login endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

}

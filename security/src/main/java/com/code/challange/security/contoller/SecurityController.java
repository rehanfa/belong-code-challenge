package com.code.challange.security.contoller;

import com.code.challange.security.common.JwtToken;
import com.code.challange.security.common.SecurityToken;
import com.code.challange.security.service.SecurityService;
import com.code.challange.security.service.SecurityTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;

@RestController
@EnableEurekaClient
public class SecurityController {


    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private SecurityTokenService securityTokenService;
    @Autowired
    private SecurityService securityService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<JwtToken> login(@RequestBody SecurityToken securityToken) {
        ResponseEntity<JwtToken> retVal;
        try {
            if (securityService.authenticate(securityToken)) {
                JwtToken jwtToken = new JwtToken(securityTokenService.encode(securityToken));
                retVal = new ResponseEntity<>(jwtToken, HttpStatus.OK);
            } else {
                retVal = new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            LOGGER.error("error in login endpoint", ex);
            retVal = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return retVal;
    }

    @GetMapping(value = "/{token}/{serviceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SecurityToken hasAccess(@PathVariable String token, @PathVariable String serviceName) {
        SecurityToken retVal = null;
        try {
            SecurityToken securityToken = securityTokenService.decode(token);
            if (securityToken != null) {
                retVal = securityService.hasAccess(securityToken, serviceName);
            }

        } catch (GeneralSecurityException e) {
            LOGGER.error("Error in hasAccess call", e);
        }
        return retVal;
    }

}

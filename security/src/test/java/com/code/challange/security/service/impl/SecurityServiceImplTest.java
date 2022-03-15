package com.code.challange.security.service.impl;

import com.code.challange.security.common.SecurityToken;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SecurityServiceImplTest {
    private SecurityServiceImpl securityService;
    private SecurityToken securityToken;

    @Before
    public void setUp() throws Exception {
        securityService = new SecurityServiceImpl();
        securityService.init();
        securityToken = new SecurityToken();
        securityToken.setUserName("user01");
        securityToken.setPassword("secret01");
    }

    @Test
    public void authenticate_success() {

        assertTrue(securityService.authenticate(securityToken));
    }

    @Test
    public void authenticate_failure() {
        securityToken.setUserName("some-user");
        assertFalse(securityService.authenticate(securityToken));
    }

    @Test
    public void hasAccess_success() {
        assertNotNull(securityService.hasAccess(securityToken, ""));
    }

    @Test
    public void hasAccess_failure() {
        securityToken.setUserName("some-user");
        assertNull(securityService.hasAccess(securityToken, ""));
    }
}
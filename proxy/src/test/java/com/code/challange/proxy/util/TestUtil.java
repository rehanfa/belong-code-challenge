package com.code.challange.proxy.util;

import com.code.challange.proxy.common.SecurityToken;
import com.code.challange.proxy.common.SecurityTokenConstants;
import com.code.challange.proxy.common.Status;
import com.code.challange.proxy.dto.Address;
import com.code.challange.proxy.dto.Customer;
import com.code.challange.proxy.dto.Phone;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static void setup(Customer customer, Address address, Phone phone, HttpHeaders httpHeaders,
                             SecurityToken securityToken) {
        if (customer != null && address != null && phone != null) {
            customer.setFirstName("firstName");
            customer.setLastName("lastName");
            customer.setMiddleName("middleName");
            address.setCity("city");
            address.setLine1("line");
            address.setPostCode("3000");
            address.setState("vic");
            customer.setAddresses(address);

            phone.setPhoneNumber("0400000000");
            phone.setCreatedDate(new Date());
            phone.setStatus(Status.ACTIVE);

            customer.setPhones(phone);
        }
        if (httpHeaders != null && securityToken != null) {
            httpHeaders.add(SecurityTokenConstants.HEADER, SecurityTokenConstants.PREFIX + "some-token");
            securityToken.setUserName("some-user");
            securityToken.setPassword("some-password");
            List<String> roles = new ArrayList<>(1);
            roles.add("USER");
            securityToken.setRoles(roles);
        }
    }
}

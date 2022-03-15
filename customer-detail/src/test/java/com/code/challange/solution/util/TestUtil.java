package com.code.challange.solution.util;

import com.code.challange.solution.common.Status;
import com.code.challange.solution.domain.Address;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;

import java.util.Date;

public class TestUtil {
    public static void setup(Customer customer, Address address, Phone phone) {
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
    }
}

package com.code.challange.solution.init;

import com.code.challange.solution.common.Status;
import com.code.challange.solution.domain.Address;
import com.code.challange.solution.domain.Customer;
import com.code.challange.solution.domain.Phone;
import com.code.challange.solution.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class DataBaseInitializer implements CommandLineRunner {

    private Customer customer = null;
    private Phone phone = null;
    private Address address = null;

    @Autowired
    private CustomerService customerService;

    private static String generatePhoneNumber() {
        Random rand = new Random();
        int num = rand.nextInt(100000000);

        DecimalFormat df = new DecimalFormat("00000000");

        String phoneNumber = "04" + df.format(num);
        return phoneNumber;
    }

    private static Date generateDob() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1990, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return java.util.Date.from(randomBirthDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            customer = new Customer();
            customer.setFirstName("firstName-" + i);
            customer.setLastName("lastName -" + i);
            customer.setMiddleName("middleName " + i);
            customer.setDateOfBirth(generateDob());

            for (int j = 0; j < 2; j++) {
                address = new Address();
                address.setCity("city -" + i + j);
                address.setLine1("line-" + i + j);
                address.setPostCode("30" + i + j);
                address.setState("vic");
                customer.setAddresses(address);
            }
            for (int k = 0; k < 5; k++) {
                phone = new Phone();
                phone.setPhoneNumber(generatePhoneNumber());
                phone.setCreatedDate(new Date());
                if (k % 2 == 0) {
                    phone.setStatus(Status.INACTIVE);
                } else {
                    phone.setStatus(Status.ACTIVE);
                }
                customer.setPhones(phone);
            }

            customerService.saveCustomer(customer);
        }

    }

}

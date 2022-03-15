package com.code.challange.solution.common;

import com.code.challange.solution.domain.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerList {
    private List<Customer> customers;

    public CustomerList() {
        setCustomers(new ArrayList<>());
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}

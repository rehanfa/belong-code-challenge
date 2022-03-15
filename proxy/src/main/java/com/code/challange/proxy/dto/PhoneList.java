package com.code.challange.proxy.dto;

import java.util.ArrayList;
import java.util.List;

public class PhoneList {
    private List<Phone> content;
    private Integer totalPages;
    private Integer totalElements;
    private Integer numberOfElements;
    private Integer size;
    private Integer number;

    public PhoneList() {
        setContent(new ArrayList<>());
    }

    public List<Phone> getContent() {
        return content;
    }

    public void setContent(List<Phone> phones) {
        this.content = phones;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}

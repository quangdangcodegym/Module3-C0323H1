package com.codegym.customermanager.model;

public class Pageable {
    private String kw;
    private int customerType;
    private int page;
    private int limit;

    private int total;

    public Pageable(String kw, int customerType, int page, int limit) {
        this.kw = kw;
        this.customerType = customerType;
        this.page = page;
        this.limit = limit;
    }

    public Pageable() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

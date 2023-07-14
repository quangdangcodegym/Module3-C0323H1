package com.codegym.manager_products.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Product {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate createAt;
    private Category category;
    private ESize size;
    private Instant updateAt;

    public ESize getSize() {

        return size;
    }

    public void setSize(ESize size) {
        this.size = size;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Date getUpdateAtTypeUtil(){
        return Date.from(this.getUpdateAt());
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {
    }

    public Product(long id, String name, String description, BigDecimal price, LocalDate createAt, Instant updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Product(long id, String name, String description, BigDecimal price, LocalDate createAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createAt = createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}

package com.example.webpos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "pid")
    @NotEmpty
    private String pid;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "price")
    @NotNull
    private double price;

    @Column(name = "image")
    @NotEmpty
    private String image;

    public Product() {

    }

    public boolean isNew() {
        return this.pid == null;
    }

    @Override
    public String toString() {
        return getPid() + "\t" + getName() + "\t" + getPrice();
    }
}

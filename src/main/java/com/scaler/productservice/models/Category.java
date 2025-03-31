package com.scaler.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;

@Entity
public class Category extends BaseModel implements Serializable {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
  //  @OneToMany
    List<Product> products;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

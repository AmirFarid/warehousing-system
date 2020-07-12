package com.parchenegar.capsule.domain.product;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.domain.base.media.ProductMedia;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "PRODUCTS")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Product implements  Serializable
{
    @Id
    long id;
    String name;
    String description;
    String isAbstract;
    String isActive;
    String unitsOnOrder;
    String color;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_TYPE_ID")
    ProductType type;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    List<ProductMedia> media;

    @ManyToMany
    @JoinTable(
            name = "CATEGORY_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    @ToString.Exclude
    List<Category> categories;


    public boolean isActive()
    {
        if ("true".equals(isActive)) {
            return true;
        }
        return false;
    }

    public Product changeToActive()
    {
        this.isActive = "true";
        return this;
    }

    public Product changeToDeActive()
    {
        this.isActive = "false";
        return this;
    }
}

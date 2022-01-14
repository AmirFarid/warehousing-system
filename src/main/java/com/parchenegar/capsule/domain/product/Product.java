package com.parchenegar.capsule.domain.product;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.domain.base.media.ProductMedia;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCTS")
@JsonIgnoreProperties(value = {"type", "media", "categories", "attributes"})
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    String isActive;
    String unitsOnOrder;
    String color;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_TYPE_ID")
    @ToString.Exclude
    ProductType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @ToString.Exclude
    List<ProductMedia> media;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CATEGORY_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    @ToString.Exclude
    List<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATTRIBUTE_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_ID")
    )
    @ToString.Exclude
    List<Attribute> attributes;

    public boolean active()
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

package com.parchenegar.capsule.domain.product;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.domain.base.media.ProductMedia;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    String color;
    String isActive;
    String description;
    String unitsOnOrder;
    String qtyUnit;
    String price;
    String promotionPrice;
    String priceLevelOne;
    String priceLevelTwo;
    long qty;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
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


    public Product addCategory(Category category)
    {
        this.categories.add(category);
        return this;
    }

    public Product addMedia(ProductMedia media)
    {
        this.media.add(media);
        return this;
    }

    public boolean getIsActive()
    {
        return "true".equals(isActive) ? true : false;
    }

    public boolean isActive()
    {
        return "true".equals(isActive) ? true : false;
    }

    public void setIsActive(boolean isActive)
    {
        if (isActive) {
            this.isActive = "true";
        } else {
            this.isActive = "false";
        }
    }

    public Product changeToActive()
    {
        setIsActive(true);
        return this;
    }

    public Product changeToDeActive()
    {
        setIsActive(false);
        return this;
    }
}

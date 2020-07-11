package com.parchenegar.capsule.domain.product;


import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.domain.base.Media;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Table(name = "PRODUCTS")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product
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

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    List<Media> media;

    @ManyToMany
    @JoinTable(
            name = "CATEGORY_PRODUCT",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    Set<Category> categories;


    public boolean isActive()
    {
        if ("true".equals(isActive)) {
            return true;
        }
        return false;
    }

    public Product setIsActive(boolean status)
    {
        if (status) {
            this.isActive = "true";
        } else {
            this.isActive = "false";
        }

        return this;
    }
}

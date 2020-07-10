package com.parchenegar.capsule.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Product parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    List<Product> children;
}

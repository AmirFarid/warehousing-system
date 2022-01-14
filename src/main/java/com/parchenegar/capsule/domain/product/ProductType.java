package com.parchenegar.capsule.domain.product;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT_TYPES")
public class ProductType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    Date created;
    Date modified;
}

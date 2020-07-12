package com.parchenegar.capsule.domain.product;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "PRODUCT_TYPES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductType
{
    @Id
    long id;
    String name;
    String description;
    Date created;
    Date modified;
}

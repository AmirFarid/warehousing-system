package com.parchenegar.capsule.domain.product;


import com.parchenegar.capsule.domain.attribute.Attribute;
import com.parchenegar.capsule.domain.attribute.AttributeValue;
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
@Table(name = "PRODUCT_TYPES")
public class ProductType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATTRIBUTE_VALUE_PRODUCT_TYPE",
            joinColumns = @JoinColumn(name = "PRODUCT_TYPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_VALUE_ID")
    )
    @ToString.Exclude
    List<AttributeValue> attributeValues;
}

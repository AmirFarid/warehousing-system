package com.parchenegar.capsule.domain.base.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.product.Product;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("product")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProductMedia extends Media
{
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    @PrimaryKeyJoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    Product product;
}

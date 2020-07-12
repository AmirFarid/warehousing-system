package com.parchenegar.capsule.domain.base.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.product.Product;
import lombok.Data;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@ToString
@DiscriminatorValue("product")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class ProductMedia extends Media
{
    @ManyToOne
    @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    Product product;
}

package com.parchenegar.capsule.domain.attribute;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ATTRIBUTES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Attribute
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    List<AttributeCollection> attributeCollections;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    List<AttributeValue> attributeValues;
}

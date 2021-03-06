package com.parchenegar.capsule.domain.attribute;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ATTRIBUTE_VALUES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AttributeValue
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String value;
    String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ATTRIBUTE_ID")
    Attribute attribute;
}

package com.parchenegar.capsule.domain.product;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ATTRIBUTES")
public class Attribute
{
    @Id
    long id;
    String key;
    String value;
    String type;
}

package com.parchenegar.capsule.domain.product;


import lombok.*;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String key;
    String value;
    String type;
}

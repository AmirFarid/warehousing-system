package com.parchenegar.capsule.domain.address;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESSES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ENTITY", discriminatorType = DiscriminatorType.STRING)
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String alias;
    String addressLine;
    String postalCode;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    Region region;
}

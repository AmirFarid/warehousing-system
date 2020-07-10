package com.parchenegar.capsule.domain;


import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ADDRESSES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Address
{
    @Id
    long id;
    String alias;
    String addressLine;
    String postalCode;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    private Region region;

}

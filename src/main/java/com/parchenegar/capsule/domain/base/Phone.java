package com.parchenegar.capsule.domain.base;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHONES")
public class Phone
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String number;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    Address address;
}

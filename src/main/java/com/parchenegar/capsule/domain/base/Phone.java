package com.parchenegar.capsule.domain.base;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Table(name = "PHONES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Phone
{
    @Id
    long id;
    String number;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    Address address;
}

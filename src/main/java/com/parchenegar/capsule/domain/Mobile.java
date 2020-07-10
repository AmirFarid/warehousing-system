package com.parchenegar.capsule.domain;


import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Table(name = "MOBILES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class Mobile
{
    @Id
    long id;
    String number;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User user;
}

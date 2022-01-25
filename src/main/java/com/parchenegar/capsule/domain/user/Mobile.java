package com.parchenegar.capsule.domain.user;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOBILES")
public class Mobile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String number;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User user;
}

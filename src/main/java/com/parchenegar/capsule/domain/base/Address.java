package com.parchenegar.capsule.domain.base;


import com.parchenegar.capsule.domain.user.User;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESSES")
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
    Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User user;

}

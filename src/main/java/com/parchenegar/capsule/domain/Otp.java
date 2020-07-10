package com.parchenegar.capsule.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "OTPS")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Otp
{
    @Id
    long id;
    String recipient;
    String secret;
    String code;
    String channel;
    String length;
    Date created;
    Date modified;
}

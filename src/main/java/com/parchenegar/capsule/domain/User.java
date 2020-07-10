package com.parchenegar.capsule.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "USERS")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    @Id
    long id;
    String firstName;
    String lastName;
    String mobile;
    Date created;
    Date modified;
}

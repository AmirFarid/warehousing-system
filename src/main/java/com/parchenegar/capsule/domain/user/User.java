package com.parchenegar.capsule.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User
{
    @Id
    long id;
    String firstName;
    String lastName;
    String username;
    @JsonIgnore
    String password;
    Date created;
    Date modified;
}

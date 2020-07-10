package com.parchenegar.capsule.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private static final long serialVersionUID = -2343243243242432341L;

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

package com.parchenegar.capsule.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parchenegar.capsule.domain.base.Branch;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String firstName;
    String lastName;
    String username;
    @JsonIgnore
    String password;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    Branch branch;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;


}

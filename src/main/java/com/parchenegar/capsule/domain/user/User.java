package com.parchenegar.capsule.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parchenegar.capsule.domain.base.Branch;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BRANCH_USER",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "BRANCH_ID")
    )
    @ToString.Exclude

    List<Branch> branches;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;


}

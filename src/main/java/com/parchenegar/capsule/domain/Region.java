package com.parchenegar.capsule.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "REGIONS")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Region
{
    @Id
    long id;
    String name;
    String slug;
    String coordinate;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Region parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    List<Region> children;
}

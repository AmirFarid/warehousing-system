package com.parchenegar.capsule.domain.address;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REGIONS")
public class Region
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String slug;
    String coordinate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    Region parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    List<Region> children;
}

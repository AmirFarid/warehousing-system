package com.parchenegar.capsule.domain.base;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.media.CategoryMedia;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "CATEGORIES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Category implements Serializable
{
    @Id
    long id;
    String name;
    String description;
    String isActive;
    Date created;
    Date modified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private List<Category> subCategories;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @ToString.Exclude
    List<CategoryMedia> media;

    public boolean isActive()
    {
        if ("true".equals(isActive)) {
            return true;
        }
        return false;
    }

    public Category changeToActive()
    {
        this.isActive = "true";
        return this;
    }

    public Category changeToDeActive()
    {
        this.isActive = "false";
        return this;
    }
}

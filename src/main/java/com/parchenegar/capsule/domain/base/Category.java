package com.parchenegar.capsule.domain.base;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "CATEGORIES")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category
{
    @Id
    long id;
    String name;
    String description;
    String isActive;
    Date created;
    Date modified;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> subCategories;


    public boolean isActive()
    {
        if ("true".equals(isActive)) {
            return true;
        }
        return false;
    }

    public Category setIsActive(boolean status)
    {
        if (status) {
            this.isActive = "true";
        } else {
            this.isActive = "false";
        }

        return this;
    }
}

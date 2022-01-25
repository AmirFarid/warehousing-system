package com.parchenegar.capsule.domain.base;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BRANCHES")
public class Branch
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    String type;
    String isActive;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;

    public boolean isWarehouse()
    {
        return "WAREHOUSE".equals(type.toUpperCase());
    }

    public boolean isStore()
    {
        return "STORE".equals(type.toUpperCase());
    }

    public boolean getIsActive()
    {
        return "true".equals(isActive) ? true : false;
    }

    public boolean isActive()
    {
        return "true".equals(isActive) ? true : false;
    }

    public void setIsActive(boolean isActive)
    {
        if (isActive) {
            this.isActive = "true";
        } else {
            this.isActive = "false";
        }
    }

    public Branch changeToActive()
    {
        setIsActive(true);
        return this;
    }

    public Branch changeToDeActive()
    {
        setIsActive(false);
        return this;
    }
}

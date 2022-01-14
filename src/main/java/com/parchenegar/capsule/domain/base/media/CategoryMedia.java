package com.parchenegar.capsule.domain.base.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.parchenegar.capsule.domain.base.Category;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("category")
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class CategoryMedia extends Media
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")
    Category category;
}

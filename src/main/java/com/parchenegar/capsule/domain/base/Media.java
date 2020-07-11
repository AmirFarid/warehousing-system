package com.parchenegar.capsule.domain.base;

import com.parchenegar.capsule.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "MEDIA")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@AnyMetaDef(name = "entityMetaDef", metaType = "string", idType = "long", metaValues = {
        @MetaValue(targetEntity = Product.class, value = "product")
})
public class Media
{
    @Id
    long id;
    String name;
    String fileName;
    String mimeType;
    String size;
    String disk;
    String path;
    Date created;
    Date modified;

    @Any(metaColumn = @Column(name = "entity"), metaDef = "entityMetaDef", fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_ID")
    Object entity;
}

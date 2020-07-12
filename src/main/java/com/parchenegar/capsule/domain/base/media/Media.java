package com.parchenegar.capsule.domain.base;

import com.parchenegar.capsule.domain.product.Product;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "MEDIA")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "ENTITY", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@AnyMetaDef(name = "EntityMetaDef", metaType = "string", idType = "long", metaValues = {
//        @MetaValue(targetEntity = Product.class, value = "product"),
//        @MetaValue(targetEntity = Category.class, value = "category")
//})
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
}

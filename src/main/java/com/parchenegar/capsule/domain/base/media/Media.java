package com.parchenegar.capsule.domain.base.media;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEDIA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ENTITY", discriminatorType = DiscriminatorType.STRING)
public abstract class Media
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

package com.parchenegar.capsule.domain.base.media;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "MEDIA")
@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "ENTITY", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

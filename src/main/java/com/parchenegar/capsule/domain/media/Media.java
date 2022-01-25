package com.parchenegar.capsule.domain.media;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    String fileName;
    String mimeType;
    String size;
    String disk;
    String path;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date modified;
}

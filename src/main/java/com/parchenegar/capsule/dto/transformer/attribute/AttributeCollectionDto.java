package com.parchenegar.capsule.dto.transformer.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeCollectionDto
{
    long id;
    String name;
    String description;
}

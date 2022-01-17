package com.parchenegar.capsule.dto.request.producttype;

import com.parchenegar.capsule.dto.request.attribute.AttributeValueDto;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductTypeDto
{
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 155)
    String name;

    @NotNull
    @NotEmpty
    List<AttributeValueDto> attributeValues;
}
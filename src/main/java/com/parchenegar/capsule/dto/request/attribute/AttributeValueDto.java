package com.parchenegar.capsule.dto.request.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeValueDto
{
    @NotNull
    @NotEmpty
    @Digits(integer = Integer.MAX_VALUE, fraction = 2)
    Long attributeId;

    @NotNull
    @NotEmpty
    @Min(2)
    String value;
}

package com.parchenegar.capsule.dto.request.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto
{
    @NotNull
    @NotEmpty
    @JsonProperty("category_ids")
    Long[] categoryIds;

    @NotNull
    @NotEmpty
    @JsonProperty("product_type_id")
    Long productTypeId;

    @NotNull
    @NotEmpty
    @Min(2)
    String color;

    @NotNull
    @NotEmpty
    @NegativeOrZero
    Long qty;

    @NotNull
    @NotEmpty
    @Min(2)
    @JsonProperty("qty_unit")
    String qtyUnit;

    @NotNull
    @NotEmpty
    @NegativeOrZero
    String price;

    @JsonIgnore
    MultipartFile picture;

    @JsonProperty("is_active")
    Boolean isActive;
}
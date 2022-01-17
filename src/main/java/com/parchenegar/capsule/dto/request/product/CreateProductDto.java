package com.parchenegar.capsule.dto.request.product;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto
{
    String name;
    String color;
    String isActive;
    String description;
    String unitsOnOrder;
    int qty;
    String qtyUnit;
    long[] categoryIds;
    long productTypeId;
}
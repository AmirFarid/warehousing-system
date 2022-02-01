package com.parchenegar.capsule.dto.transformer.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto
{
    long id;
    String name;
    String color;
    String isActive;
    String description;
    String unitsOnOrder;
    String qtyUnit;
    String price;
    String promotionPrice;
    String priceLevelOne;
    String priceLevelTwo;
    long qty;
}

package com.parchenegar.capsule.controller.product;


import com.parchenegar.capsule.common.i18n.Translator;
import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.domain.product.ProductType;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.request.producttype.CreateProductTypeDto;
import com.parchenegar.capsule.dto.transformer.product.ProductTypeDto;
import com.parchenegar.capsule.service.product.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/product-type")
public class ProductTypeController extends BaseRestController
{
    private ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService)
    {
        this.productTypeService = productTypeService;
    }


    @PostMapping("")
    public ResponseEntity create(@Valid @RequestBody CreateProductTypeDto productTypeDto)
    {
        try {
            ProductType productType = productTypeService.save(productTypeDto);

            ProductTypeDto productTypeDtoResponse = ProductTypeDto
                    .builder()
                    .id(productType.getId())
                    .name(productType.getName())
                    .description(productType.getDescription())
                    .build();

            return Response.ok(productTypeDtoResponse, translator.get("http.ok"));
        } catch (Throwable throwable) {

            return Response.serverError(translator.get("http.ok"));
        }

    }


}

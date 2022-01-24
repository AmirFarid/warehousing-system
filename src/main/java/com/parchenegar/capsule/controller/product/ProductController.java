package com.parchenegar.capsule.controller.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parchenegar.capsule.common.storage.StorageService;
import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.domain.product.Product;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.request.product.CreateProductDto;
import com.parchenegar.capsule.service.product.CreateProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/product")
public class ProductController extends BaseRestController
{
    private CreateProductService createProductService;

    @Autowired
    StorageService storageService;

    @Autowired
    public ProductController(CreateProductService createProductService)
    {
        this.createProductService = createProductService;
    }

    @PostMapping(value = "")
    public ResponseEntity create(@Valid @RequestParam("product") String productString,
                                 @RequestParam("picture") MultipartFile picture) throws Exception
    {

        CreateProductDto productDto = new ObjectMapper().readValue(productString, CreateProductDto.class);
        productDto.setPicture(picture);
        Product product = createProductService.create(productDto);

        return Response.ok(product, translator.get("http.ok"));
    }

}


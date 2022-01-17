package com.parchenegar.capsule.controller.product;

import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/product")
public class ProductController extends BaseRestController
{
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

}

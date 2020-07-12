package com.parchenegar.capsule.controller.product;


import com.parchenegar.capsule.common.i18n.Translator;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.service.product.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductTypeController
{
    private Translator translator;
    private ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService, Translator translator)
    {
        this.translator = translator;
        this.productTypeService = productTypeService;
    }

    @GetMapping("/product-type")
    public ResponseEntity finAll()
    {
        return Response.builder()
                .message(translator.translate("http.ok"))
                .description(translator.translate("http.ok"))
                .body(productTypeService.findAll())
                .build()
                .ok();
    }

}

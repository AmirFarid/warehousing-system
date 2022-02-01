package com.parchenegar.capsule.controller.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parchenegar.capsule.common.Transformer;
import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.domain.product.Product;
import com.parchenegar.capsule.domain.user.User;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.transformer.product.ProductDto;
import com.parchenegar.capsule.repository.product.ProductRepository;
import com.parchenegar.capsule.repository.user.UserRepository;
import com.parchenegar.capsule.service.SecurityUtils;
import com.parchenegar.capsule.service.product.ProductBarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/product")
public class SearchProductController extends BaseRestController
{
    private UserRepository userRepository;
    private ProductRepository productRepository;
    ProductBarcodeService productBarcodeService;

    @Autowired
    public SearchProductController(UserRepository userRepository,
                                   ProductRepository productRepository,
                                   ProductBarcodeService productBarcodeService)
    {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productBarcodeService = productBarcodeService;
    }

    @GetMapping("/search/{barcode}")
    public ResponseEntity search(@PathVariable Long barcode)
    {
        Optional<User> user = userRepository.findByUsername(SecurityUtils.auth().getName());

        if (!user.isPresent()) throw new EntityNotFoundException();

        List<Product> products = productRepository.findAllByIdAndBranchIn(
                productBarcodeService.decode(barcode),
                user.get().getBranches()
        );


        return Response.ok(Transformer.transformList(products, ProductDto.class), translator.get("http.ok"));
    }
}

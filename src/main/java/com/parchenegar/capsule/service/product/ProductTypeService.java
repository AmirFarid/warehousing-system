package com.parchenegar.capsule.service.product;

import com.parchenegar.capsule.domain.product.ProductType;
import com.parchenegar.capsule.repository.product.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeService
{
    ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository)
    {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductType> findAll()
    {
        return productTypeRepository.findAll(Sort.by("name").ascending());
    }
}

package com.parchenegar.capsule.service.product;

import com.parchenegar.capsule.domain.attribute.Attribute;
import com.parchenegar.capsule.domain.attribute.AttributeValue;
import com.parchenegar.capsule.domain.product.ProductType;
import com.parchenegar.capsule.dto.request.producttype.CreateProductTypeDto;
import com.parchenegar.capsule.repository.attribute.AttributeRepository;
import com.parchenegar.capsule.repository.attribute.AttributeValueRepository;
import com.parchenegar.capsule.repository.product.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService
{
    EntityManager entityManager;
    AttributeRepository attributeRepository;
    AttributeValueRepository attributeValueRepository;
    ProductTypeRepository productTypeRepository;

    @Autowired
    public ProductTypeService(ProductTypeRepository productTypeRepository, AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository, EntityManager entityManager)
    {
        this.productTypeRepository = productTypeRepository;
        this.attributeRepository = attributeRepository;
        this.attributeValueRepository = attributeValueRepository;
        this.entityManager = entityManager;
    }

    public List<ProductType> findAllSortedByName()
    {
        return productTypeRepository.findAll(Sort.by("name").ascending());
    }

    @Transactional
    public ProductType save(CreateProductTypeDto productTypeDto)
    {
        List<AttributeValue> attributeValues = productTypeDto.getAttributeValues().stream().map(attributeValue -> {
            return AttributeValue.builder()
                    .attribute(entityManager.getReference(Attribute.class, attributeValue.getAttributeId()))
                    .value(attributeValue.getValue())
                    .build();
        }).collect(Collectors.toList());

        attributeValueRepository.saveAll(attributeValues);

        ProductType productType = ProductType.builder()
                .name(productTypeDto.getName())
                .attributeValues(attributeValues)
                .created(new Date())
                .modified(new Date())
                .build();

        return productTypeRepository.save(productType);
    }
}

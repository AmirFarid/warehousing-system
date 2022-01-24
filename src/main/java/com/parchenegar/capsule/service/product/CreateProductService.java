package com.parchenegar.capsule.service.product;


import com.parchenegar.capsule.common.storage.StorageService;
import com.parchenegar.capsule.domain.base.Category;
import com.parchenegar.capsule.domain.base.media.ProductMedia;
import com.parchenegar.capsule.domain.product.Product;
import com.parchenegar.capsule.domain.product.ProductType;
import com.parchenegar.capsule.dto.request.product.CreateProductDto;
import com.parchenegar.capsule.repository.base.MediaRepository;
import com.parchenegar.capsule.repository.product.ProductRepository;
import com.parchenegar.capsule.repository.product.ProductTypeRepository;
import com.parchenegar.capsule.service.base.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CreateProductService
{
    private EntityManager entityManager;
    private StorageService storageService;
    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;
    private MediaRepository mediaRepository;
    private CategoryService categoryService;

    @Autowired
    public CreateProductService(ProductRepository productRepository,
                                StorageService storageService,
                                ProductTypeRepository productTypeRepository,
                                EntityManager entityManager,
                                MediaRepository mediaRepository,
                                CategoryService categoryService)
    {
        this.productRepository = productRepository;
        this.storageService = storageService;
        this.productTypeRepository = productTypeRepository;
        this.entityManager = entityManager;
        this.mediaRepository = mediaRepository;
        this.categoryService = categoryService;
    }


    public Product create(CreateProductDto productDto) throws Exception
    {
        Optional<ProductType> productType = productTypeRepository.findById(productDto.getProductTypeId());
        errorIfProductTypeNotFound(productType, productDto.getProductTypeId());

        List<Category> categories = getCategories(productDto);


        Product product = Product.builder()
                .name(productType.get().getName() + "-" + productDto.getColor())
                .color(productDto.getColor())
                .categories(categories)
                .qty(productDto.getQty())
                .qtyUnit(productDto.getQtyUnit())
                .price(productDto.getPrice())
                .type(productType.get())
                .unitsOnOrder("CENTIMETER")
                .build();

        if (productDto.getIsActive()) {
            product.changeToActive();
        } else {
            product.changeToDeActive();
        }

        ProductMedia productMedia = storeMedia(productDto, product);


        return product;
    }

    private List<Category> getCategories(CreateProductDto productDto)
    {
        if (productDto.getCategoryIds().length <= 0) return null;

        List<Category> categories = categoryService.findAllById(Arrays.asList(productDto.getCategoryIds()));
        errorIfCategoryNotFound(categories);

        return categories;
    }

    private void errorIfCategoryNotFound(List<Category> categories)
    {
        if (categories == null || categories.isEmpty()) {
            throw new EntityNotFoundException(
                    "Entity " + Category.class + " not found"
            );
        }
    }

    private ProductMedia storeMedia(CreateProductDto productDto, Product product)
    {
        String filename = storageService.store(productDto.getPicture(), product.getClass());
        ProductMedia productMedia = new ProductMedia();
        productMedia.setFileName(filename);
        productMedia.setDisk("local");
        productMedia.setPath("/");
        productMedia.setProduct(product);
        productMedia.setMimeType(productDto.getPicture().getContentType());
        productMedia.setSize(String.valueOf(productDto.getPicture().getSize()));
        mediaRepository.save(productMedia);

        return productMedia;
    }

    private void errorIfProductTypeNotFound(Optional<ProductType> productType, long id)
    {
        if (!productType.isPresent()) {
            throw new EntityNotFoundException(
                    "Entity " + ProductType.class + " by id " + id + " not found"
            );
        }
    }

}

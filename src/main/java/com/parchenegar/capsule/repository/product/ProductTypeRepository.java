package com.parchenegar.capsule.repository.product;

import com.parchenegar.capsule.domain.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>
{
}

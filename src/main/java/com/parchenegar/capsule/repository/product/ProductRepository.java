package com.parchenegar.capsule.repository.product;

import com.parchenegar.capsule.domain.base.Branch;
import com.parchenegar.capsule.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findAllByIdAndBranchIn(Long id, List<Branch> branches);
}

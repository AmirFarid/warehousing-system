package com.parchenegar.capsule.repository.product;


import com.parchenegar.capsule.domain.product.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long>
{
}

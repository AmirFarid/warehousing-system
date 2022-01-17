package com.parchenegar.capsule.repository.attribute;


import com.parchenegar.capsule.domain.attribute.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long>
{
}

package com.parchenegar.capsule.repository.attribute;


import com.parchenegar.capsule.domain.attribute.AttributeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeCollectionRepository extends JpaRepository<AttributeCollection, Long>
{
    List<AttributeCollection> findByAttributeId(long attributeId);
}

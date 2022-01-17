package com.parchenegar.capsule.repository.attribute;


import com.parchenegar.capsule.domain.attribute.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long>
{
}

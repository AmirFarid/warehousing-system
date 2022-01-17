package com.parchenegar.capsule.service.attribute;

import com.parchenegar.capsule.domain.attribute.AttributeCollection;
import com.parchenegar.capsule.repository.attribute.AttributeCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeCollectionService
{
    AttributeCollectionRepository attributeCollectionRepository;

    @Autowired
    public AttributeCollectionService(AttributeCollectionRepository attributeCollectionRepository)
    {
        this.attributeCollectionRepository = attributeCollectionRepository;
    }

    public List<AttributeCollection> findAll()
    {
        return attributeCollectionRepository.findAll();
    }

    public List<AttributeCollection> findByAttributeId(long attributeId)
    {
        return attributeCollectionRepository.findByAttributeId(attributeId);
    }

}

package com.parchenegar.capsule.service.attribute;

import com.parchenegar.capsule.domain.attribute.Attribute;
import com.parchenegar.capsule.repository.attribute.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttributeService
{
    AttributeRepository attributeRepository;

    @Autowired
    public AttributeService(AttributeRepository attributeRepository)
    {
        this.attributeRepository = attributeRepository;
    }


    public List<Attribute> findAll()
    {
        return attributeRepository.findAll();
    }

    public Optional<Attribute> findOneById(Long id)
    {
        return attributeRepository.findById(id);
    }

}

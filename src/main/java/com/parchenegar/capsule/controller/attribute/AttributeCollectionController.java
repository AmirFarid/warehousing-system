package com.parchenegar.capsule.controller.attribute;

import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.domain.attribute.AttributeCollection;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.transformer.attribute.AttributeCollectionDto;
import com.parchenegar.capsule.service.attribute.AttributeCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/attribute-collection")
public class AttributeCollectionController extends BaseRestController
{
    AttributeCollectionService attributeCollectionService;

    @Autowired
    public AttributeCollectionController(AttributeCollectionService attributeCollectionService)
    {
        this.attributeCollectionService = attributeCollectionService;
    }

    @GetMapping("")
    public ResponseEntity all()
    {
        List<AttributeCollection> attributeCollections = attributeCollectionService.findAll();

        if (attributeCollections.isEmpty()) {
            return Response.notFound(
                    attributeCollections,
                    translator.get("http.not_found")
            );
        }

        List<AttributeCollectionDto> result = attributeCollections.stream().map(ac -> {
            return AttributeCollectionDto.builder().id(ac.getId()).name(ac.getName()).description(ac.getDescription()).build();
        }).collect(Collectors.toList());


        return Response.ok(result, translator.get("http.ok"));
    }
}

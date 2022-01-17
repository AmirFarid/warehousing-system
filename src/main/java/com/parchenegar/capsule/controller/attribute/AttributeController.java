package com.parchenegar.capsule.controller.attribute;

import com.parchenegar.capsule.controller.BaseRestController;
import com.parchenegar.capsule.domain.attribute.Attribute;
import com.parchenegar.capsule.domain.attribute.AttributeCollection;
import com.parchenegar.capsule.dto.base.Response;
import com.parchenegar.capsule.dto.transformer.attribute.AttributeCollectionDto;
import com.parchenegar.capsule.dto.transformer.attribute.AttributeDto;
import com.parchenegar.capsule.service.attribute.AttributeCollectionService;
import com.parchenegar.capsule.service.attribute.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/attribute")
public class AttributeController extends BaseRestController
{
    AttributeService attributeService;
    AttributeCollectionService attributeCollectionService;

    @Autowired
    public AttributeController(AttributeCollectionService attributeCollectionService, AttributeService attributeService)
    {
        this.attributeService = attributeService;
        this.attributeCollectionService = attributeCollectionService;
    }


    @GetMapping("/{id}/collection")
    public ResponseEntity getCollection(@PathVariable Long id)
    {
        List<AttributeCollection> attributeCollections = attributeCollectionService.findByAttributeId(id);

        if (attributeCollections.isEmpty()) {
            return Response.notFound(attributeCollections, translator.get("http.not_found"));
        }

        List<AttributeCollectionDto> result = attributeCollections.stream().map(c -> {
            return AttributeCollectionDto.builder().id(c.getId())
                    .name(c.getName()).description(c.getDescription()).build();
        }).collect(Collectors.toList());


        return Response.ok(result, translator.get("http.ok"));

    }

    @GetMapping("")
    public ResponseEntity all()
    {
        List<Attribute> attributes = attributeService.findAll();

        if (attributes.isEmpty()) {
            return Response.notFound(attributes, translator.get("http.not_found"));
        }

        List<AttributeDto> result = attributes.stream().map(a -> {
            return AttributeDto
                    .builder()
                    .id(a.getId()).name(a.getName()).description(a.getDescription())
                    .build();
        }).collect(Collectors.toList());


        return Response.ok(result, translator.get("http.ok"));
    }

    @GetMapping("/{id}")
    public ResponseEntity all(@PathVariable Long id)
    {

        Optional<Attribute> attribute = attributeService.findOneById(id);

        if (!attribute.isPresent()) {
            return Response.notFound(attribute, translator.get("http.not_found"));
        }

        AttributeDto result = AttributeDto
                .builder()
                .id(attribute.get().getId())
                .name(attribute.get().getName())
                .description(attribute.get().getDescription())
                .build();

        return Response.ok(result, translator.get("http.ok"));
    }
}

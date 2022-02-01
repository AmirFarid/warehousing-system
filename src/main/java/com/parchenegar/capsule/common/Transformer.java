package com.parchenegar.capsule.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parchenegar.capsule.dto.transformer.product.ProductDto;

import java.util.List;

public class Transformer
{

    /**
     *
     * @param fromValue
     * @param toValue
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T, E> List<E> transformList(T fromValue, Class<E> toValue)
    {
        ObjectMapper mapper = mapper();
        return mapper.convertValue(
                fromValue,
                mapper.getTypeFactory().constructCollectionType(List.class, toValue)
        );
    }

    /**
     *
     * @return
     */
    private static ObjectMapper mapper()
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}

package com.parchenegar.capsule.service.product;


import org.springframework.stereotype.Service;

@Service
public class ProductUnitService
{
    public Double meterToCentimeter(Double meter)
    {
        return meter * 100;
    }

    public Double meterToCentimeter(Integer meter)
    {
        return meter * 100.0;
    }

    public Double yardToCentimeter(Double yard)
    {
        return yard * 91.44;
    }

    public Double yardToCentimeter(Integer yard)
    {
        return yard * 91.44;
    }
}

package com.parchenegar.capsule.service.product;

import org.springframework.stereotype.Service;

@Service
public class ProductBarcodeService
{
    public long encode(Long productId)
    {
        return 10000000L + productId;
    }

    public long decode(Long barcode)
    {
        return barcode - 10000000L;
    }

    public long decode(String barcode)
    {
        return Long.valueOf(barcode) - 10000000L;
    }
}

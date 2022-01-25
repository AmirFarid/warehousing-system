package com.parchenegar.capsule.domain.payment;

public enum InvoiceEnum
{

    BUY("BUY"),
    SALE("SALE");

    private String value;

    InvoiceEnum(final String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return this.getValue();
    }

}

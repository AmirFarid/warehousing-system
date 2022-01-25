package com.parchenegar.capsule.domain.order;

public enum OrderStatusEnum
{

    INIT("INIT");

    private String value;

    OrderStatusEnum(final String value)
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

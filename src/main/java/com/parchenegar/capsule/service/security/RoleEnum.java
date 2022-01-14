package com.parchenegar.capsule.service.security;

public enum RoleEnum
{
    CASHIER("CASHIER"),
    SELLER("SELLER"),
    SUPERVISOR("SUPERVISOR"),
    WAREHOUSE_KEEPER("WAREHOUSE_KEEPER");

    private String value;

    RoleEnum(final String value)
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

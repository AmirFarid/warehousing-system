package com.parchenegar.capsule.domain.base;

public enum BranchEnum
{

    STORE("STORE"),
    WAREHOUSE("WAREHOUSE");

    private String value;

    BranchEnum(final String value)
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

package com.parchenegar.capsule.domain.address;

public enum RegionEnum
{

    COUNTRY("1"),
    PROVINCE("2"),
    CITY("3");

    private String value;

    RegionEnum(final String value)
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

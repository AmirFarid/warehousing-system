package com.parchenegar.capsule.domain.product;

public enum QtyUnitEnum
{
    METER("METER"),
    YARD("YARD"),
    ADAD("ADAD"),
    METER_FA("متر"),
    YARD_FA("یارد"),
    ADAD_FA("عدد");

    private String value;

    QtyUnitEnum(final String value)
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

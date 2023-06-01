package com.nosz.projectsem2be.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nosz.projectsem2be.exception.UnknownEnumValueException;

public enum InvoiceStatus {
    Success(0),Pending(1),Delivering(2);

    private int value;

    InvoiceStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static InvoiceStatus of(Integer value) {
        if (null == value) {
            return null;
        }

        for (InvoiceStatus item : InvoiceStatus.values()) {
            if (value.equals(item.getValue())) {
                return item;
            }
        }

        throw new UnknownEnumValueException("GenderEnum: unknown value: " + value);
    }

}

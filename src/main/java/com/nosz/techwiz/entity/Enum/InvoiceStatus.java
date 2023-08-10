package com.nosz.techwiz.entity.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nosz.techwiz.exception.CustomException;

public enum InvoiceStatus {
    Success(0),Pending(1),Delivering(2),Cancel(3);

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

        throw new CustomException("GenderEnum: unknown value: " + value);
    }

}

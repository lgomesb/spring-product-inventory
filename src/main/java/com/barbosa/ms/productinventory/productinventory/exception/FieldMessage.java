package com.barbosa.ms.productinventory.productinventory.exception;

import lombok.Getter;

@Getter
public class FieldMessage {

    private String fieldName;
    private String message;

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;

    }

}

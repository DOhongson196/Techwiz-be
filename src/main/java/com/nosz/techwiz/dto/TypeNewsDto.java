package com.nosz.techwiz.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;


@Data
public class TypeNewsDto implements Serializable {
    private Long id;

    @NotEmpty(message = "Type news is empty.")
    private String name;
}

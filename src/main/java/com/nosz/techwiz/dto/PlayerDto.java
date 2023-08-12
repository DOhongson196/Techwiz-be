package com.nosz.techwiz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class PlayerDto implements Serializable {
    private Long id;
    private String name;
    private String position;
    private String national;
    private String image;
    private Integer weight;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private Integer height;
    private Integer number;
}

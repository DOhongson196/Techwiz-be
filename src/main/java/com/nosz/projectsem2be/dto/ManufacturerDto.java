package com.nosz.projectsem2be.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ManufacturerDto implements Serializable {
    private Long id;
    private String name;
    private String logo;

    @JsonIgnore
    private MultipartFile logoFile;
}

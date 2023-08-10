package com.nosz.techwiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class NewsDto implements Serializable {
    private Long id;
    private String title;
    private String thumbnail;
    @JsonIgnore
    private MultipartFile newsFile;
}
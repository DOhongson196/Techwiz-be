package com.nosz.techwiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class NewsDto implements Serializable {
    private Long id;

    @NotEmpty(message = "News title is empty")
    private String title;

    private String description;

    private String content;

    private String thumbnail;

    private String typeNews;
}
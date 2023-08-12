package com.nosz.techwiz.controller;


import com.nosz.techwiz.dto.NewsDto;
import com.nosz.techwiz.service.MapValidationErrorService;
import com.nosz.techwiz.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @Secured({"ROLE_ADMIN"})
    @PostMapping("/add")
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewsDto dto, BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity != null){
            return responseEntity;
        }
        var productDto = newsService.insertNews(dto);

        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllNews(){
        List<?> result = newsService.findAll();
        return new ResponseEntity<>(result,  HttpStatus.OK);
    }

    @GetMapping("/type={type}")
    public ResponseEntity<?> getNewsByType(@PathVariable String type ){
        List<?> result = newsService.getNewsByType(type);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/id={id}")
    public ResponseEntity<?> getNewsById(@PathVariable Long id){
        var result = newsService.getNewsById(id);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
}

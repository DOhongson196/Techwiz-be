package com.nosz.techwiz.controller;

import com.nosz.techwiz.dto.CategoryDto;
import com.nosz.techwiz.entity.Category;
import com.nosz.techwiz.service.CategoryService;
import com.nosz.techwiz.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;


    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid  @RequestBody CategoryDto dto,
                                            BindingResult bindingResult){
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(bindingResult);
        if(responseEntity != null){
            return  responseEntity;
        }
        Category entity = categoryService.save(dto);
        dto.setId(entity.getId());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,@RequestBody CategoryDto dto){

        Category entity = categoryService.update(id,dto);
        dto.setId(entity.getId());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }



    @GetMapping()
    public ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/status")
    public ResponseEntity<?> getCategoriesBtyStatus(){
        return new ResponseEntity<>(categoryService.findByStatus(),HttpStatus.OK);
    }


    @GetMapping("/page")
    public ResponseEntity<?> getCategories(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable){
        return new ResponseEntity<>(categoryService.findAll(pageable),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategories(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteById(id);
        return new ResponseEntity<>("Category id " + id + " delete success",HttpStatus.OK);
    }

}
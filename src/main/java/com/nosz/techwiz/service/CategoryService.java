package com.nosz.techwiz.service;

import com.nosz.techwiz.dto.CategoryDto;
import com.nosz.techwiz.entity.Category;
import com.nosz.techwiz.entity.Enum.CategoryStatus;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.CategoryRepository;
import com.nosz.techwiz.respository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public Category save(CategoryDto dto) {
        if(categoryRepository.existsByName(dto.getName())){
            throw new CustomException("Category name is existed");
        }
        Category entity = new Category();
        BeanUtils.copyProperties(dto,entity);

        return categoryRepository.save(entity);
    }

    public Category update(Long id,CategoryDto dto) {
        Optional<Category> existed = categoryRepository.findById(id);
        if(existed.isEmpty()){
            throw new CustomException("Category id " + id + " does not existed");
        }

        try {
            Category existedCategory = existed.get();
            existedCategory.setName(dto.getName());
            existedCategory.setStatus(dto.getStatus());

            return categoryRepository.save(existedCategory);
        }catch (Exception ex){
            throw new CustomException("Update category fail");
        }

    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByStatus() {
        return categoryRepository.findByStatus(CategoryStatus.Visible);
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> findById(Long id) {
        Optional<Category> found  =  categoryRepository.findById(id);

        if(found.isEmpty()){
            throw new CustomException("Category id " + id + " does not existed");
        }

        return found;
    }

    public void deleteById(Long id) {
        var found = categoryRepository.findById(id).orElseThrow(() ->
                new CustomException("Product id " + id + " not existed"));
            if(productRepository.existsByCategory_Id(id)){
                found.setStatus(CategoryStatus.Invisible);
                categoryRepository.save(found);
                throw new CustomException("Category has related products cannot remove,changed its status In-Visible");
            }
            categoryRepository.deleteById(found.getId());
    }
}

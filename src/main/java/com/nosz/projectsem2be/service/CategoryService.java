package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.dto.CategoryDto;
import com.nosz.projectsem2be.entity.Category;
import com.nosz.projectsem2be.entity.CategoryStatus;
import com.nosz.projectsem2be.exception.CategoryException;
import com.nosz.projectsem2be.respository.CategoryRepository;
import com.nosz.projectsem2be.respository.ProductRepository;
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
            throw new CategoryException("Category name is existed");
        }
        Category entity = new Category();
        BeanUtils.copyProperties(dto,entity);

        return categoryRepository.save(entity);
    }

    public Category update(Long id,CategoryDto dto) {
        Optional<Category> existed = categoryRepository.findById(id);
        if(existed.isEmpty()){
            throw new CategoryException("Category id " + id + " does not existed");
        }

        try {
            Category existedCategory = existed.get();
            existedCategory.setName(dto.getName());
            existedCategory.setStatus(dto.getStatus());

            return categoryRepository.save(existedCategory);
        }catch (Exception ex){
            throw new CategoryException("Update category fail");
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
            throw new CategoryException("Category id " + id + " does not existed");
        }

        return found;
    }

    public void deleteById(Long id) {
        Optional<Category> found = this.findById(id);
        if(found.isPresent()){
            if(productRepository.existsByCategory_Id(id)){
                throw new CategoryException("Category has related products cannot remove,changed its status In-Visible");
            }
            categoryRepository.delete(found.get());
        }else{
            throw new CategoryException("Category is not existed");
        }
    }
}

package com.nosz.techwiz.service;


import com.nosz.techwiz.dto.ProductDto;
import com.nosz.techwiz.dto.ProductDtoBrief;
import com.nosz.techwiz.entity.Category;
import com.nosz.techwiz.entity.Enum.CategoryStatus;
import com.nosz.techwiz.entity.Enum.ProductStatus;
import com.nosz.techwiz.entity.Product;
import com.nosz.techwiz.exception.CustomException;
import com.nosz.techwiz.respository.InvoiceDetailsRepository;
import com.nosz.techwiz.respository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    InvoiceDetailsRepository invoiceDetailsRepository;


    public List<?> findAll() {
        return productRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto insertProduct(ProductDto dto) {

        if (productRepository.existsByName(dto.getName())) {
            throw new CustomException("Product name is existed");
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);

        var cate = new Category();
        cate.setId(dto.getCategoryId());
        entity.setCategory(cate);

        var savedProduct = productRepository.save(entity);
        dto.setId(savedProduct.getId());

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto updateProduct(Long id, ProductDto dto) {
        var founded = productRepository.findById(id).orElseThrow(() ->
                new CustomException("Not found product"));
        String[] ignoreFields = new String[]{"createdDate", "image", "sizes", "viewCount", "volume"};
        String preImage = founded.getImage();
        BeanUtils.copyProperties(dto, founded, ignoreFields);
        if (dto.getImage() != null) {
            if (founded.getImage() != null) {
                fileStorageService.deleteProductImageFile(founded.getImage());
            }
            founded.setImage(dto.getImage());
        } else {
            founded.setImage(preImage);
        }

        var cate = new Category();
        cate.setId(dto.getCategoryId());
        founded.setCategory(cate);

        productRepository.save(founded);
        dto.setId(founded.getId());
        return dto;
    }

    public Page<ProductDtoBrief> getProductBriefByName(String name, Pageable pageable) {
        var list = productRepository.findByNameContainsIgnoreCase(name, pageable);

        return getProductDtoBriefs(list);
    }

    private Page<ProductDtoBrief> getProductDtoBriefs(Page<Product> list) {
        var newList = list.stream().map(item -> {
            ProductDtoBrief dto = new ProductDtoBrief();
            BeanUtils.copyProperties(item, dto);

            dto.setCategoryName(item.getCategory().getName());

            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(newList, list.getPageable(), list.getTotalElements());
    }

    public List<ProductDtoBrief> getProductBriefSearchHeader(String name) {
        var list = productRepository.findByNameContainsAndStatusNotAndCategory_Status(name, ProductStatus.Discontinued, CategoryStatus.Visible);

        return getProductDtoBriefs(list);
    }

    private List<ProductDtoBrief> getProductDtoBriefs(List<Product> list) {
        return list.stream().map(item -> {
            ProductDtoBrief dto = new ProductDtoBrief();
            BeanUtils.copyProperties(item, dto);

            dto.setCategoryName(item.getCategory().getName());

            return dto;
        }).collect(Collectors.toList());
    }



    public Page<ProductDtoBrief> getProductsByCategories(String query, Long id, Double start, Double end, Pageable pageable) {
        var list = productRepository.findByStatusNotAndCategory_IdAndNameContainsIgnoreCaseAndPriceBetween(ProductStatus.Discontinued, id, query, start, end, pageable);
        //var newList = getProductDtoBriefs(list);
        return getProductDtoBriefs(list);
    }


    public Page<ProductDtoBrief> getProductBrief(Pageable pageable) {
        var list = productRepository.findAll(pageable);

        return getProductDtoBriefs(list);
    }

    public ProductDto getProductById(Long id) {
        var found = productRepository.findById(id).orElseThrow(() ->
                new CustomException("Not found product id " + id));
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(found, dto);
        dto.setCategoryId(found.getCategory().getId());

        productRepository.save(found);

        return dto;
    }

    public ProductDtoBrief getProductBriefById(Long id) {
        var found = productRepository.findById(id).orElseThrow(() ->
                new CustomException("Not found product id " + id));
        ProductDtoBrief dto = new ProductDtoBrief();
        BeanUtils.copyProperties(found, dto);
        dto.setCategoryName(found.getCategory().getName());

        productRepository.save(found);

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductById(Long id) {
        var found = productRepository.findById(id).orElseThrow(() ->
                new CustomException("Product id " + id + " not found"));
        if (invoiceDetailsRepository.existsByProduct_Id(id)) {
            found.setStatus(ProductStatus.Discontinued);
            productRepository.save(found);
            throw new CustomException("Product already exists in invoice details cannot remove! Change product status to Discontinued");
        }
        if (found.getImage() != null) {
            fileStorageService.deleteProductImageFile(found.getImage());
        }
        productRepository.delete(found);
    }

    public List<ProductDtoBrief> getProductTop10Featured(){
        var list = productRepository.selectTop10Featured();

        return getProductDtoBriefs(list);
    }
}

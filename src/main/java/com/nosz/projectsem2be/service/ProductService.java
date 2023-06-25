package com.nosz.projectsem2be.service;


import com.nosz.projectsem2be.dto.ProductDto;
import com.nosz.projectsem2be.dto.ProductDtoBrief;
import com.nosz.projectsem2be.entity.*;
import com.nosz.projectsem2be.exception.ProductException;
import com.nosz.projectsem2be.respository.InvoiceDetailsRepository;
import com.nosz.projectsem2be.respository.ProductRepository;
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


    public List<?> findAll(){
        return productRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto insertProduct(ProductDto dto){

        if(productRepository.existsByName(dto.getName())){
            throw new ProductException("Product name is existed");
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto,entity);

        var manuF = new Manufacturer();
        manuF.setId(dto.getManufacturerId());
        entity.setManufacturer(manuF);

        var cate = new Category();
        cate.setId(dto.getCategoryId());
        entity.setCategory(cate);


        var savedProduct = productRepository.save(entity);
        dto.setId(savedProduct.getId());

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDto updateProduct(Long id, ProductDto dto){
        var founded = productRepository.findById(id).orElseThrow(() ->
                new ProductException("Not found product"));
        String [] ignoreFields = new String[]{"createdDate","image","sizes","viewCount","volume"};
        String preImage = founded.getImage();
        BeanUtils.copyProperties(dto,founded,ignoreFields);
        if(dto.getImage() != null){
            if(founded.getImage() !=null){
                fileStorageService.deleteProductImageFile(founded.getImage());
            }
            founded.setImage(dto.getImage());
        }else{
            founded.setImage(preImage);
        }

        var manuF = new Manufacturer();
        manuF.setId(dto.getManufacturerId());
        founded.setManufacturer(manuF);

        var cate = new Category();
        cate.setId(dto.getCategoryId());
        founded.setCategory(cate);


        productRepository.save(founded);
        dto.setId(founded.getId());
        return dto;
    }
    public Page<ProductDtoBrief> getProductBriefByName(String name, Pageable pageable){
        var list = productRepository.findByNameContainsIgnoreCase(name,pageable);

        return getProductDtoBriefs(list);
    }

    private Page<ProductDtoBrief> getProductDtoBriefs(Page<Product> list) {
        var newList = list.stream().map(item -> {
            ProductDtoBrief dto = new ProductDtoBrief();
            BeanUtils.copyProperties(item,dto);

            dto.setCategoryName(item.getCategory().getName());
            dto.setManufacturerName(item.getManufacturer().getName());

            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(newList,list.getPageable(), list.getTotalElements());
    }

    public List<ProductDtoBrief> getProductBriefSearchHeader(String name){
        var list = productRepository.findByNameContainsAndStatusNotAndCategory_Status(name, ProductStatus.Discontinued, CategoryStatus.Visible);

        return getProductDtoBriefs(list);
    }

    private List<ProductDtoBrief> getProductDtoBriefs(List<Product> list) {
        return list.stream().map(item -> {
            ProductDtoBrief dto = new ProductDtoBrief();
            BeanUtils.copyProperties(item,dto);

            dto.setCategoryName(item.getCategory().getName());
            dto.setManufacturerName(item.getManufacturer().getName());

            return dto;
        }).collect(Collectors.toList());
    }

    public List<ProductDtoBrief> getProductTop10View(){
        var list = productRepository.selectTop10View();

        return getProductDtoBriefs(list);
    }

    public Page<ProductDtoBrief> getProductsByCategories(String query,Long id,Double start,Double end,Pageable pageable){
        var list = productRepository.findByStatusNotAndCategory_IdAndNameContainsIgnoreCaseAndPriceBetween(ProductStatus.Discontinued,id,query,start,end,pageable);
        //var newList = getProductDtoBriefs(list);
        return getProductDtoBriefs(list);
    }

    public List<ProductDtoBrief> getProductTop10Buy(){
        var list = productRepository.selectTop10Buy();

        return getProductDtoBriefs(list);
    }


    public List<ProductDtoBrief> getProductTopView(){
        var list = productRepository.selectTopView();

        return getProductDtoBriefs(list);
    }

    public List<ProductDtoBrief> getProductTopBuy(){
        var list = productRepository.selectTopBuy();

        return getProductDtoBriefs(list);
    }

    public List<ProductDtoBrief> getProductTop10Featured(){
        var list = productRepository.selectTop10Featured();

        return getProductDtoBriefs(list);
    }

    public Page<ProductDtoBrief> getProductBrief( Pageable pageable){
        var list = productRepository.findAll(pageable);

        return getProductDtoBriefs(list);
    }

    public ProductDto getProductById(Long id){
        var found = productRepository.findById(id).orElseThrow(() ->
                new ProductException("Not found product id " + id));
        ProductDto dto = new ProductDto();
        BeanUtils.copyProperties(found,dto);
        dto.setCategoryId(found.getCategory().getId());
        dto.setManufacturerId(found.getManufacturer().getId());

        var view = found.getViewCount() + 1L;
        found.setViewCount(view);
        productRepository.save(found);

        return dto;
    }

    public ProductDtoBrief getProductBriefById(Long id){
        var found = productRepository.findById(id).orElseThrow(() ->
                new ProductException("Not found product id " + id));
        ProductDtoBrief dto = new ProductDtoBrief();
        BeanUtils.copyProperties(found,dto);
        dto.setCategoryName(found.getCategory().getName());
        dto.setManufacturerName(found.getManufacturer().getName());

        var view = found.getViewCount() + 1L;
        found.setViewCount(view);
        productRepository.save(found);

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductById(Long id){
        var found = productRepository.findById(id).orElseThrow(() ->
                new ProductException("Product id " + id + " not found"));
        if(invoiceDetailsRepository.existsByProduct_Id(id)){
            throw new ProductException("Product already exists in invoice details cannot remove! You can edit product status to Discontinued");
        }
        if(found.getImage() != null){
            fileStorageService.deleteProductImageFile(found.getImage());
        }

        productRepository.delete(found);
    }



}

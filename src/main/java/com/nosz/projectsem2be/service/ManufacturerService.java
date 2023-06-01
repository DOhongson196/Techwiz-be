package com.nosz.projectsem2be.service;

import com.nosz.projectsem2be.dto.ManufacturerDto;
import com.nosz.projectsem2be.entity.Manufacturer;
import com.nosz.projectsem2be.exception.ManufacturerException;
import com.nosz.projectsem2be.respository.ManufacturerRepository;
import com.nosz.projectsem2be.respository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductRepository productRepository;

    public Manufacturer insertManufacturer(ManufacturerDto dto){
        List<?> foundedList = manufacturerRepository.findByNameContainsIgnoreCase(dto.getName());

        if(foundedList.size()>0){
            throw new ManufacturerException("Manufacturer name is existed");
        }

        Manufacturer entity = new Manufacturer();
        BeanUtils.copyProperties(dto,entity);
        if(dto.getLogoFile() != null){
            String filename = fileStorageService.storeLogoFile(dto.getLogoFile());

            entity.setLogo(filename);
            dto.setLogoFile(null);
        }
        return manufacturerRepository.save(entity);
    }

    public Manufacturer updateManufacturer(Long id,ManufacturerDto dto){
        var founded = manufacturerRepository.findById(id);

        if(founded.isEmpty()){
            throw new ManufacturerException("Manufacturer not found");
        }
        List<?> foundedList = manufacturerRepository.findByNameContainsIgnoreCase(dto.getName());

        if(foundedList.size()>0){
            throw new ManufacturerException("Manufacturer name is existed");
        }

        Manufacturer entity = new Manufacturer();
        BeanUtils.copyProperties(dto,entity);
        if(dto.getLogoFile() != null){
            if(founded.get().getLogo() !=null){
                fileStorageService.deleteLogoFile(founded.get().getLogo());
            }
            String filename = fileStorageService.storeLogoFile(dto.getLogoFile());
            entity.setLogo(filename);
            dto.setLogoFile(null);
        }else{
            entity.setLogo(founded.get().getLogo());
        }
        return manufacturerRepository.save(entity);
    }

    public List<?> findAll(){
        return  manufacturerRepository.findAll();
    }

    public Page<Manufacturer> findByName(String name,Pageable pageable){
        return  manufacturerRepository.findByNameContainsIgnoreCase(name,pageable);
    }

    public Page<Manufacturer> findAll(Pageable pageable){
        return  manufacturerRepository.findAll(pageable);
    }

    public Manufacturer findById(Long id){
        Optional<Manufacturer> found = manufacturerRepository.findById(id);
        if(found.isEmpty()){
            throw new ManufacturerException("Manufacturer with id " + id + " is not existed");
        }
        return found.get();
    }

    public void deleteById(Long id){
        Manufacturer existed = findById(id);
        if(productRepository.existsByManufacturer_Id(id)){
            throw new ManufacturerException("The author has related products, cannot remove");
        }
        if(existed.getLogo() != null){
            fileStorageService.deleteLogoFile(existed.getLogo());
        }
        manufacturerRepository.delete(existed);
    }

}

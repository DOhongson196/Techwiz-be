package com.nosz.techwiz.service;

import com.nosz.techwiz.dto.StadiumDto;
import com.nosz.techwiz.dto.filter.StadiumFilter;
import com.nosz.techwiz.entity.Stadium;
import com.nosz.techwiz.exception.ResourceNotFoundException;
import com.nosz.techwiz.respository.StadiumRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StadiumService {
    private final StadiumRepository stadiumRepository;

    public Stadium addStadium(StadiumDto stadiumDto) {
        Stadium newStadium = new Stadium();
        BeanUtils.copyProperties(stadiumDto, newStadium);
        return stadiumRepository.save(newStadium);
    }

    public Stadium updateStadium(Long id, StadiumDto stadiumDto) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Stadium id %d not found", id)));
        BeanUtils.copyProperties(stadiumDto, stadium);
        return stadiumRepository.save(stadium);
    }

    public Page<Stadium> getPageStadium(StadiumFilter filter, Pageable pageable) {
        return stadiumRepository.getAllWithFilter(filter, pageable);
    }
    public List<Stadium> getAll() {
        return stadiumRepository.findAll();
    }

    public Stadium getById(Long id) {
        return stadiumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Stadium id %d not found", id)));
    }

    public void deleteStadium(Long id) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Stadium id %d not found", id)));
        stadiumRepository.delete(stadium);
    }
}

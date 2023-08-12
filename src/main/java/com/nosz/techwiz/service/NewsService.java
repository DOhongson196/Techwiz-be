package com.nosz.techwiz.service;


import com.nosz.techwiz.dto.NewsDto;
import com.nosz.techwiz.entity.News;
import com.nosz.techwiz.exception.NewsException;
import com.nosz.techwiz.respository.NewsRepository;
import com.nosz.techwiz.respository.TypeNewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TypeNewsRepository typeNewsRepository;

    public List<?> findAll() {
        return newsRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public NewsDto insertNews(NewsDto dto ){
        if(newsRepository.existsByTitle(dto.getTitle()) ){
            throw new NewsException("This news is existed!");
        }

        if(!typeNewsRepository.existsByName(dto.getTypeNews().toLowerCase())){
            throw new NewsException("Type of news does not exited.");
        }

        News entity = new News();
        BeanUtils.copyProperties(dto,entity);


        var typeNews = typeNewsRepository.findByName(dto.getTypeNews().toLowerCase());
        entity.setTypeNews(typeNews);

        var saved = newsRepository.save(entity);
        dto.setId(saved.getId());
        return  dto;
    }

    public List<?> getNewsByType(String type){
        var typeNews = typeNewsRepository.findByName(type.toLowerCase());

        return newsRepository.findNewsByTypeNews(typeNews);
    }

    public News getNewsById(Long id){

        return newsRepository.findById(id).orElseThrow(() ->
                new NewsException("Not found product id " + id));
    }



}

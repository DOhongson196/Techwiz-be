package com.nosz.techwiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "news")
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;


    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "content", nullable = false)
    private String content;


    @Column(name ="thumbnail", nullable = false)
    private String thumbnail;

    @ManyToOne
    @JoinColumn(name = "type_news")
    private TypeNews typeNews;



}
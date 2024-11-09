package com.example.springbootsp.models;

import java.time.LocalDateTime;


public class ArticleDto {
    private Long id;
    private String name;

    private University university;
    private boolean isRecent = true;
    private LocalDateTime createdDate = LocalDateTime.now();

    private Author author;
    private String content;

    public Article toArticle() {
        return new Article(
                this.id,
                this.name,
                this.university,
                this.isRecent,
                this.createdDate,
                this.author,
                this.content
        );
    }

    public ArticleDto(Long id, String name, University university, boolean isRecent, LocalDateTime createdDate, Author author, String content) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.isRecent = isRecent;
        this.createdDate = createdDate;
        this.author = author;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public boolean isRecent() {
        return isRecent;
    }

    public void setRecent(boolean recent) {
        isRecent = recent;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
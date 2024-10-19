package com.example.springbootsp.repositories;

import com.example.springbootsp.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    void deleteAllByUniversityId(Long universityId);

    void deleteAllByAuthorId(Long id);
}
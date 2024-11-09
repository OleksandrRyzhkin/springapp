package com.example.springbootsp.controllers;

import com.example.springbootsp.models.Article;
import com.example.springbootsp.models.ArticleDto;
import com.example.springbootsp.services.ArticleService;
import com.example.springbootsp.services.UniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UniversityService universityService;

    public ArticleController(ArticleService articleService, UniversityService universityService) {
        this.articleService = articleService;
        this.universityService = universityService;
    }


    @GetMapping
    public List<ArticleDto> getAllArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ArticleDto createArticle(@RequestBody Article article) {
        return universityService.createArticle(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDetails) {
        return ResponseEntity.ok(articleService.updateArticle(id, articleDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}

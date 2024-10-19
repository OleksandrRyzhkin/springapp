package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Article;
import com.example.springbootsp.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {

    private static final String ARTICLE_NOT_FOUND = "Article not found. id=";
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }


    @Transactional
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND + id));
        articleRepository.delete(article);
    }


    public Article updateArticle(Long id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND + id));

        article.setName(articleDetails.getName());
        article.setUniversity(articleDetails.getUniversity());
        article.setAuthor(articleDetails.getAuthor());
        article.setContent(articleDetails.getContent());
        article.setContent(articleDetails.getContent());

        return articleRepository.save(article);
    }


    public void deleteAllByUniversityId(Long universityId) {
        articleRepository.deleteAllByUniversityId(universityId);
    }

    public void deleteAllByAuthorId(Long id) {
        articleRepository.deleteAllByAuthorId(id);
    }
}

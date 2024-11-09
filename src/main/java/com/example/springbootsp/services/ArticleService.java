package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Article;
import com.example.springbootsp.models.ArticleDto;
import com.example.springbootsp.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleService {

    private static final String ARTICLE_NOT_FOUND = "Article not found. id=";
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private ArticleDto convertToDto(Article article) {
        return new ArticleDto(
                article.getId(),
                article.getName(),
                article.getUniversity(),
                article.isRecent(),
                article.getCreatedDate(),
                article.getAuthor(),
                article.getContent()
        );
    }

    private Article convertToEntity(ArticleDto articleDto) {
        return new Article(
                articleDto.getId(),
                articleDto.getName(),
                articleDto.getUniversity(),
                articleDto.isRecent(),
                articleDto.getCreatedDate(),
                articleDto.getAuthor(),
                articleDto.getContent()
        );
    }

    public Optional<ArticleDto> getArticleById(Long id) {
        return articleRepository.findById(id).map(this::convertToDto);
    }

    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public ArticleDto createArticle(Article article) {
        Article savedArticle = articleRepository.save(article);
        return convertToDto(savedArticle);
    }

    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND + id));
        articleRepository.delete(article);
    }

    public ArticleDto updateArticle(Long id, ArticleDto articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ARTICLE_NOT_FOUND + id));

        article.setName(articleDetails.getName());
        article.setUniversity(articleDetails.getUniversity());
        article.setAuthor(articleDetails.getAuthor());
        article.setContent(articleDetails.getContent());
        Article updatedArticle = articleRepository.save(article);
        return convertToDto(updatedArticle);
    }

    public void deleteAllByUniversityId(Long universityId) {
        articleRepository.deleteAllByUniversityId(universityId);
    }

    public void deleteAllByAuthorId(Long id) {
        articleRepository.deleteAllByAuthorId(id);
    }
}
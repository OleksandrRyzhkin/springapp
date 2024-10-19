package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Article;
import com.example.springbootsp.models.University;
import com.example.springbootsp.repositories.AuthorRepository;
import com.example.springbootsp.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UniversityService {
    private static final String UNIVERSITY_NOT_FOUND = "University not found. id=";
    private final ArticleService articleService;
    private final UniversityRepository universityRepository;
    private final AuthorRepository authorRepository;

    public UniversityService(ArticleService articleService, UniversityRepository universityRepository, AuthorRepository authorRepository) {
        this.articleService = articleService;
        this.universityRepository = universityRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<University> getUniversityById(Long id) {
        return universityRepository.findById(id);
    }

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }


    public List<University> getAllCategories() {
        return universityRepository.findAll();
    }

    public University updateUniversity(Long id, University universityDetails) {
        University university = universityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(UNIVERSITY_NOT_FOUND + id));

        university.setName(universityDetails.getName());

        return universityRepository.save(university);
    }

    @Transactional
    public Article createArticle(Article article) {
        article.setUniversity(universityRepository.findById(article.getUniversity().getId()).orElseThrow(() -> new ResourceNotFoundException(UNIVERSITY_NOT_FOUND + article.getUniversity().getId())));
        article.setAuthor(authorRepository.findById(article.getAuthor().getId()).orElseThrow(() -> new ResourceNotFoundException("Author not found. id=" + article.getAuthor().getId())));
        return articleService.createArticle(article);
    }


    @Transactional
    public void deleteUniversity(Long id) {
        University university = universityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(UNIVERSITY_NOT_FOUND + id));
        articleService.deleteAllByUniversityId(university.getId());
        universityRepository.delete(university);
    }

}

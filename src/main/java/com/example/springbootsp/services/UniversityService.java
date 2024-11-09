package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Article;
import com.example.springbootsp.models.ArticleDto;
import com.example.springbootsp.models.University;
import com.example.springbootsp.models.UniversityDto;
import com.example.springbootsp.repositories.AuthorRepository;
import com.example.springbootsp.repositories.UniversityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private UniversityDto convertToDto(University university) {
        return new UniversityDto(university.getId(), university.getName());
    }

    private University convertToEntity(UniversityDto universityDto) {
        return new University(universityDto.getId(), universityDto.getName());
    }

    public Optional<UniversityDto> getUniversityById(Long id) {
        return universityRepository.findById(id).map(this::convertToDto);
    }

    public UniversityDto createUniversity(UniversityDto universityDto) {
        University university = convertToEntity(universityDto);
        University savedUniversity = universityRepository.save(university);
        return convertToDto(savedUniversity);
    }

    public List<UniversityDto> getAllCategories() {
        return universityRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public UniversityDto updateUniversity(Long id, UniversityDto universityDetails) {
        University university = universityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(UNIVERSITY_NOT_FOUND + id));
        university.setName(universityDetails.getName());
        University updatedUniversity = universityRepository.save(university);
        return convertToDto(updatedUniversity);
    }

    @Transactional
    public ArticleDto createArticle(Article article) {
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
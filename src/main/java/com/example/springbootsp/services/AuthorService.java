package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Author;
import com.example.springbootsp.models.AuthorDto;
import com.example.springbootsp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private static final String AUTHOR_NOT_FOUND = "Author not found. id=";
    private final AuthorRepository authorRepository;
    private final ArticleService articleService;

    public AuthorService(AuthorRepository authorRepository, ArticleService articleService) {
        this.authorRepository = authorRepository;
        this.articleService = articleService;
    }

    private AuthorDto convertToDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getEmail()
        );
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id).map(this::convertToDto);
    }

    public AuthorDto createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return convertToDto(savedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND + id));
        articleService.deleteAllByAuthorId(author.getId());
        authorRepository.delete(author);
    }

    public AuthorDto updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND + id));

        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());

        Author updatedAuthor = authorRepository.save(author);
        return convertToDto(updatedAuthor);
    }
}
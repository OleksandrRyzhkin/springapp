package com.example.springbootsp.services;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Author;
import com.example.springbootsp.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND + id));
        articleService.deleteAllByAuthorId(author.getId());
        authorRepository.delete(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AUTHOR_NOT_FOUND + id));

        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());

        return authorRepository.save(author);
    }

}

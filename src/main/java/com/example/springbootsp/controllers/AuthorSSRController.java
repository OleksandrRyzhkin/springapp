package com.example.springbootsp.controllers;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Author;
import com.example.springbootsp.models.AuthorDto;
import com.example.springbootsp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorSSRController {

    private final AuthorService authorService;

    public AuthorSSRController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public String listAuthors(Model model) {
        List<AuthorDto> authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "AuthorForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AuthorDto author = authorService.getAuthorById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        model.addAttribute("author", author);
        return "AuthorForm";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute Author author) {
        authorService.createAuthor(author);
        return "redirect:/authors";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author author) {
        authorService.updateAuthor(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
package com.example.springbootsp.controllers;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.Article;
import com.example.springbootsp.models.ArticleDto;
import com.example.springbootsp.services.ArticleService;
import com.example.springbootsp.services.AuthorService;
import com.example.springbootsp.services.UniversityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleSSRController {

    private final ArticleService articleService;
    private final UniversityService universityService;
    private final AuthorService aut;

    public ArticleSSRController(ArticleService articleService, UniversityService universityService, AuthorService aut) {
        this.articleService = articleService;
        this.universityService = universityService;
        this.aut = aut;
    }


    @GetMapping
    public String listArticles(Model model) {
        List<ArticleDto> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "listArticles";
    }

    @GetMapping("/new")
    public String showArticleForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", universityService.getAllCategories());
        model.addAttribute("authors", aut.getAllAuthors());
        return "ArticleForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ArticleDto article = articleService.getArticleById(id).orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        model.addAttribute("article", article);
        model.addAttribute("categories", universityService.getAllCategories());
        model.addAttribute("authors", aut.getAllAuthors());
        return "ArticleForm";
    }

    @PostMapping("/add")
    public String addArticle(@ModelAttribute Article article) {
        articleService.createArticle(article);
        return "redirect:/articles";
    }

    @PostMapping("/update/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute ArticleDto article) {
        articleService.updateArticle(id, article);
        return "redirect:/articles";
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/articles";
    }
}

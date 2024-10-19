package com.example.springbootsp.controllers;

import com.example.springbootsp.exception.ResourceNotFoundException;
import com.example.springbootsp.models.University;
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
@RequestMapping("/categories")
class UniversitySSRController {

    private final UniversityService universityService;

    UniversitySSRController(UniversityService universityService) {
        this.universityService = universityService;
    }


    @GetMapping
    public String listCategories(Model model) {
        List<University> categories = universityService.getAllCategories();
        model.addAttribute("categories", categories);
        return "listUniversities";
    }

    @GetMapping("/new")
    public String showUniversityForm(Model model) {
        model.addAttribute("university", new University());
        return "UniversityForm";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        University university = universityService.getUniversityById(id).orElseThrow(() -> new ResourceNotFoundException("University not found"));
        model.addAttribute("university", university);
        return "UniversityForm";
    }

    @PostMapping("/add")
    public String addUniversity(@ModelAttribute University university) {
        universityService.createUniversity(university);
        return "redirect:/categories";
    }

    @PostMapping("/update/{id}")
    public String updateUniversity(@PathVariable Long id, @ModelAttribute University university) {
        universityService.updateUniversity(id, university);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return "redirect:/categories";
    }
}

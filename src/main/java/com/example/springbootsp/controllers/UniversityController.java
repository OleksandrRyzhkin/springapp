package com.example.springbootsp.controllers;

import com.example.springbootsp.models.University;
import com.example.springbootsp.models.UniversityDto;
import com.example.springbootsp.services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/categories")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    public List<UniversityDto> getAllCategories() {
        return universityService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) {
        return universityService.getUniversityById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UniversityDto createUniversity(@RequestBody UniversityDto university) {
        return universityService.createUniversity(university);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversityDto> updateUniversity(@PathVariable Long id, @RequestBody UniversityDto universityDetails) {
        return ResponseEntity.ok(universityService.updateUniversity(id, universityDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.springbootsp.services.sceduled;

import com.example.springbootsp.repositories.ArticleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class ArticleCronService {

    private final ArticleRepository articleRepository;

    public ArticleCronService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // кожен день о 00:00
    public void increaseArticleContent() {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minus(2, ChronoUnit.WEEKS);
        articleRepository.findAll().stream().filter(article -> article.getCreatedDate().isBefore(twoWeeksAgo)).forEach(article -> {
            article.setRecent(false);
            articleRepository.save(article);
        });
    }


}
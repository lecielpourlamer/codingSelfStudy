package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ArticleController {

    @Autowired  // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;   // articleRepository 객체 선언

    @GetMapping("/articles/new")    //url 요청 접수
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 폼 데이터를 DTO로 받기
        //System.out.println("form.toString() = " + form.toString());    // DTO에 폼 데이터가 잘 담겼는지 확인
        log.info(form.toString());

        // 1. DTO를 Entity로 변환
        Article article = form.toEntity();
        //System.out.println("article.toString() = " + article.toString());
        log.info(article.toString());

        // 2. Repository로 Entity를 DB에 저장
        Article saved = articleRepository.save(article);

        // article 엔티티를 저장해 saved 객체에 반환
        //System.out.println("saved.toString() = " + saved.toString());
        log.info(saved.toString());

        return "";
    }
}

package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

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

        return "redirect:/articles/" + saved.getId();  // redirect를 작성할 위치
    }


    @GetMapping("/articles/{id}")   // 데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기
        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기
        // 1. id를 조회해 데이터 가져오기
        //Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        // article이라는 이름으로 articleEntity 객체를 등록
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        //List<Article> articleList = articleRepository.findAll();
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // DB에서 수정할 데이터 가져와서 entity 변수에 넣기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기 articleEntity를 article로 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정하기
        return "articles/edit"; // 반환할 수정 페이지 articles/edit.mustache
    }


    @PostMapping("/articles/update")
    public String update(ArticleForm form) {    // 매개변수로 DTO 받아 오기
        log.info(form.toString());
        // 1. DTO를 Entity로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. Entity를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져와 엔티티인 변수 target에 저장
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null) {
            articleRepository.save(articleEntity);  // 엔티티를 DB에 저장(갱신)
        }

        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");

        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);   // 해당 데이터 찾기
        log.info(target.toString());

        // 2. 대상 엔티티 삭제하기
        if (target != null) {   // 삭제할 대상이 있는지 확인
            articleRepository.delete(target);   // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }






}

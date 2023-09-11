package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j  // 로그를 찍을 수 있도록 어노테이션 추가
@RestController
public class ArticleApiController {
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    // GET
//    @GetMapping("/api/articles")
//    private List<Article> index() {
//        return articleRepository.findAll();
//    }
//
//    @GetMapping("api/articles/{id}")
//    private Article show(@PathVariable Long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    // POST
//    @PostMapping("api/articles")
//    private Article create(@RequestBody ArticleForm dto) {
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//
//    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id,
//                          @RequestBody ArticleForm dto) {
//        // 1. DTO -> 엔티티 변환하기 (수정용 엔티티 생성하기)
//        Article article = dto.toEntity();   // dto를 엔티티로 변환
//        log.info("id: {}, article: {}", id, article.toString());
//
//        // 2. 타깃 조회하기 (DB에 대상 엔티티가 있는지 조회하기)
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리하기 (대상 엔티티가 없거나 수정하려는 id가 잘못됐을 경우 처리하기
//        if (target == null || id != article.getId()) {
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 4. 업데이트 및 정상 응답(200)하기, 대상 엔티티가 있으면 수정 내용으로 업데이트하고, 정상 응답(200) 보내기
//        target.patch(article);                              // 기존 데이터에 새 데이터 붙이기
//        Article updated = articleRepository.save(target);  // 수정 내용 DB에 최종 저장
//
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }
//
//
//    // DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id) {
//        // 1. DB에서 대상 엔티티가 있는지 조회하기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 2. 대상 엔티티가 없어서 요청 자체가 잘못됐을 경우 처리하기
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 3. 대상 엔티티가 있으면 삭제하고, 정상 응답(200) 반환하기
//        articleRepository.delete(target);
//
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }


    @Autowired
    private ArticleService articleService;

    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);   // 서비스로 게시글 생성
        return (created != null) ?  // 생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);       // 서비스를 통해 게시글 수정
        return (updated != null) ?                              // 수정되면 정상, 안 되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);    // 서비스를 통해 게시글 삭제
        return (deleted != null) ?                      // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    @PostMapping("/api/transaction-test")   // 여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest
            (@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos); // 서비스 호출
        return (createdList != null) ?              // 생성 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}

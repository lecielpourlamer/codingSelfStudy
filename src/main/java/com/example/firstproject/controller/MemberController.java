package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signupPage() {
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm) {
        log.info("memberForm.toString() = " + memberForm.toString());

        Member member = memberForm.toEntity();
        log.info("member.toString() = " + member.toString());

        Member saved = memberRepository.save(member);
        log.info("saved.toString() = " + saved.toString());
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 1. id를 조회해 데이터 가져오기
        Member member = memberRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("member", member);

        // 3. 뷰 페이지 반환하기
        return "members/show";
    }


    @GetMapping("/members")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        Iterable<Member> members = memberRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("members", members);

        // 3. 뷰 페이지 설정하기
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // 1. DB에서 수정할 데이터를 가져와서 entity 변수에 넣기
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);

        // 3. 뷰 페이지 설정하기
        return "members/edit";
    }


    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());

        // 1. DTO를 Entity로 변환하기
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());

        // 2. Entity를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져와 엔티티인 변수 target에 저장
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null) {
            memberRepository.save(memberEntity);    // 엔티티를 DB에 저장(갱신)
        }

        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/members/" + memberEntity.getId();
    }












}


















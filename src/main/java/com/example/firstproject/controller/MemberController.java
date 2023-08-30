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

import java.util.ArrayList;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signup() {
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm) {
        //System.out.println(memberForm.toString());
        log.info("memberForm.toString() = " + memberForm.toString());

        Member member = memberForm.toEntity();
        //System.out.println(member.toString());
        log.info("member.toString() = " + member.toString());

        Member saved = memberRepository.save(member);
        //System.out.println(saved.toString());
        log.info("saved.toString() = " + saved.toString());
        return "";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 1. id를 조회해 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);

        // 3. 뷰 페이지 반환하기
        return "members/new";
    }


    @GetMapping("/members")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        ArrayList<Member> memberEntityList = memberRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("memberList", memberEntityList);

        // 3. 뷰 페이지 설정하기
        return "members/index";
    }

}


















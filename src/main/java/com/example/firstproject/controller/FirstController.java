package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")  // 웹 브라우저에서 localhost:8080/hi로 접속하면 greetings.mustache 파일을 찾아 반환해라
    public String niceToMeetYou(Model model) {  // model 객체 받아 오기
        model.addAttribute("username", "le Ciel");
        return "greetings"; // greetings.mustache 파일 반환
    }

    @GetMapping("/bye") // url 요청 접수
    public String seeYouNext(Model model) { // model 객체 받아 오기
        model.addAttribute("nickname", "ocean");
        return "goodbye";   // goodbye.mustache 반환
    }
}

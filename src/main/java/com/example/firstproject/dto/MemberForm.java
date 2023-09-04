package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MemberForm {

    private Long id;

    private String email;   // email을 받을 필드

    private String password;    // password를 받을 필드

    public Member toEntity() {
        return new Member(id, email, password);
    }
}

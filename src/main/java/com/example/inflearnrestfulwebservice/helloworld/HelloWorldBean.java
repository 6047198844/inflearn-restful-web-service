package com.example.inflearnrestfulwebservice.helloworld;
// lombok

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter/setter 자동 생성
@AllArgsConstructor // 필드값이 모두 있는 생성자
@NoArgsConstructor // 디폴드 생성자
public class HelloWorldBean {
    private String message;
}
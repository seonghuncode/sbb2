package com.mysite.sbb2.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AnswerForm {

    @NotEmpty(message = "내용을 필수 항목 입니다")
    private String content;

}

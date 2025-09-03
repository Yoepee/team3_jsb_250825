package com.back.domain.question.question.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionSearchDto {
    private String kwType = "all";
    private String kw = "";
}

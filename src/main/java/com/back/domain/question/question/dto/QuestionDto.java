package com.back.domain.question.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDto {
    @NotBlank(message = "주제를 입력해주세요.")
    @Size(min = 1, max = 20, message = "제목은 1~10 자 이내로 입력해주세요.")
    String subject;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 2, max = 100, message = "내용은 2~100 자 이내로 입력해주세요.")
    String content;
}

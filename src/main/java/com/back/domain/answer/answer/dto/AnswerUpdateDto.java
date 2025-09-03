package com.back.domain.answer.answer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnswerUpdateDto (
        @NotBlank(message = "내용을 작성해주세요.")
        @Size(min = 2, max = 100, message = "2~100자 이내로 작성해주세요.")
        String content,
        @NotBlank(message = "잘못된 접근입니다.")
        long questionId
){
}

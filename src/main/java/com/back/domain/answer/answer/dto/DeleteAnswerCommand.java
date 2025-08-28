package com.back.domain.answer.answer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteAnswerCommand {
    @NotNull
    @Positive
    Long questionId;
}

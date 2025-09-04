package com.back.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupDto(
        @Size(min = 3, max = 25)
        @NotBlank(message = "아이디는 필수 입니다.")
        String username,
        @NotBlank(message = "비밀번호는 필수 입니다.")
        String password,
        @NotBlank(message = "비밀번호는 필수 입니다.")
        String password_confirm,
        @NotBlank(message = "이름은 필수 입니다.")
        String nickname
) {
}

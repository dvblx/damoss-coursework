package ru.melnikov.computershop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequest {

    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}

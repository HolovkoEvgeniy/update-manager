package com.lumiring.updateManager.domain.api.user.auth;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingInRequest {


//    @Size(min = 5, max = 255, message = "login должен содержать от 5 до 255 символов")
//    @Schema(description = "Адрес электронной почты или имя пользователя", example = "jondoe@gmail.com или John")
//    @NotBlank(message = "login должен быть заполнен")
    private String login;

//    @Schema(description = "Пароль", example = "my_1secret1_password")
//    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
//    @NotBlank(message = "password должен быть заполнен")
//    @Pattern(regexp = RegExp.password, message = "incorrect password")
    private String password;

}

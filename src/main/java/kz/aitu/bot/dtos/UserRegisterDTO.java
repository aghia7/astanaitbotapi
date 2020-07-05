package kz.aitu.bot.dtos;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String role;
}

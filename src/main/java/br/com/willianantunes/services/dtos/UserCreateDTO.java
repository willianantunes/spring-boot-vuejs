package br.com.willianantunes.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {

    @NotBlank
    private String name;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String password;
}
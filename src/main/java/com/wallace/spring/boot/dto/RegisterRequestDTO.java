package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @Schema(description = "Primeiro nome do usuário", example = "Wallace")
        @NotBlank(message = "O nome não pode estar em branco")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras e espaços")
        String nome,

        @Schema(description = "Sobrenome do usuário", example = "Santos")
        @NotBlank(message = "O sobrenome não pode estar em branco")
        @Size(min = 2, max = 100, message = "O sobrenome deve ter entre 2 e 100 caracteres")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O sobrenome deve conter apenas letras e espaços")
        String sobreNome,

        @Schema(description = "E-mail do usuário para login", example = "wallace.santos@example.com")
        @Email(message = "O formato do e-mail é inválido")
        @NotBlank(message = "O e-mail não pode estar em branco")
        @Size(max = 100, message = "O e-mail não pode ter mais de 100 caracteres")
        String email,

        @Schema(description = "Senha do usuário. Deve conter no mínimo 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial.", example = "StrongPwd123!")
        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 8, max = 128, message = "A senha deve ter entre 8 e 128 caracteres")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
                 message = "A senha deve conter pelo menos: uma letra minúscula, uma maiúscula, um número e um caractere especial (@$!%*?&)")
        String senha
) {}
package med.voll.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(

        @NotNull
        Long id,

        @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F]{0,}$",
                message = "O nome completo deve conter:" +
                        "Nome e sobrenome com iniciais em Letra Maiúscula!                                  " +
                        "Devem ser inseridos o nome e apenas 2 sobrenomes(Caso você possua mais de 2 sobrenomes, insira seu primeiro e o seu último sobrenome!")
        String nome,

        @Email
        String email,


        @Pattern(regexp = "\\d{11}", message = "O telefone deve ser: " +
                "No formato: XXXXXXXXXXX (Apenas números)                                                    +" +
                "Devem ser inseridos 11 números (DDD, 9 na frente e o número em si")
        String telefone,

        DadosEndereco endereco

        ) {}

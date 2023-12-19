package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;
import org.w3c.dom.ranges.RangeException;

public record DadosCadastroMedico(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F]{0,}$",
                message = "O nome completo deve conter: " +
                        "Nome e sobrenome com iniciais em Letra Maiúscula!                                  " +
                        "Devem ser inseridos o nome e apenas 2 sobrenomes(Caso você possua mais de 2 sobrenomes, insira seu primeiro e o seu último sobrenome!")
        String nome,

        @NotBlank
        @Email(message = "Ex: exemplo_email@gmail.com")
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "O telefone deve ser: " +
                "No formato: XXXXXXXXXXX (Apenas números)                                                 " +
                "Devem ser inseridos 11 números (DDD, 9 na frente e o número em si")

        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{6}", message = "O CRM a ser inserido deve conter 6 números no seguinte formato: XXXXXX")
        String crm,

        @NotNull
        @Valid
        Especialidade especialidade,

        @NotNull
        @Valid
        DadosEndereco endereco) {}



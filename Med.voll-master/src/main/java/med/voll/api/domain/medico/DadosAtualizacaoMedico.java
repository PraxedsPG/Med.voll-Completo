package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        @Pattern(regexp = "^[a-zA-Z\u00C0-\u017F´]+\s+[a-zA-Z\u00C0-\u017F´]+\s+[a-zA-Z\u00C0-\u017F]{0,}$",
                message = "O nome completo deve conter: " +
                        "Nome e sobrenome com iniciais em letra Maiúscula!                                       " +
                        "Devem ser inseridos o nome e apenas 2 sobrenomes!(Caso você possua mais de 2 sobrenomes, insira seu primeiro e o seu último sobrenome!)")
        String nome,

        @Pattern(regexp = "\\d{11}",  message = "O telefone deve ser:" +
                "No formato: XXXXXXXXXXX (Apenas números)                                               " +
                "Devem ser inseridos 11 números (DDD, 9 na frente e o número em si")
        String telefone,

        @Valid
        DadosEndereco endereco
        ) {
}

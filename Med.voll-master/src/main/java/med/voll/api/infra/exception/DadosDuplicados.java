package med.voll.api.infra.exception;

import java.lang.RuntimeException;

public class DadosDuplicados extends RuntimeException{
    public DadosDuplicados(String mensagem) {
        super(mensagem);
    }
}

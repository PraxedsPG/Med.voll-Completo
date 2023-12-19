package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros extends RuntimeException {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> tratarErro404() {
        String mensagemDeErro = "A pessoa que você busca não foi encontrada, por favor pesquise por outra";
        return new ResponseEntity<>(mensagemDeErro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception) {
        var erros400 = exception.getFieldErrors();

        return ResponseEntity.badRequest().body(erros400.stream().map( DadosErroValidacao::new).toList());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErro400Especialidades() {
        String mensagen = "Especialidade inválida, por favor insira em letra maiúscula uma das seguintes especialidades: CARDIOLOGIA,DERMATOLOGIA,GINECOLOGIA,ORTOPEDIA";

        return new ResponseEntity<>(mensagen, HttpStatus.BAD_REQUEST);
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}

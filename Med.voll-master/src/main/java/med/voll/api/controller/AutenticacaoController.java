package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.exception.DadosDuplicados;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

   @Autowired
   private AuthenticationManager manager;

   @Autowired
   private UsuarioRepository repository;

   @Autowired
   private TokenService tokenService;

   @PostMapping("/cadastrar")
   @Transactional
   public ResponseEntity cadastrar(@RequestBody @Valid DadosAutenticacao dados, UriComponentsBuilder uribuilder){

       try {
           var usuario = new Usuario(dados);
           repository.save(usuario);

           String message = "Usuário cadastro com sucesso!";
           return ResponseEntity.ok(message);

       } catch (DataIntegrityViolationException e) {
           throw new DadosDuplicados("Os dados inseridos já estão cadastrados em nosso banco de dados, por favor insira outros");
       }
   }

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication =  manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}

package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.infra.exception.DadosDuplicados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {

        try{
            var paciente = new Paciente(dados);
            repository.save(paciente);
            var uri = uriBuilder.path("pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
        }catch (DataIntegrityViolationException e) {
            throw new DadosDuplicados("Os dados inseridos já estão cadastrados no banco de dados, por favor insira outros");
        }
    }

    @GetMapping
    public ResponseEntity <Page<DadosListagemPacientes>> listarPacientes(@PageableDefault(size = 10, sort = {"nome"})Pageable paginacao) {
        var page = repository.findAllByFrequenteTrue(paginacao).map(DadosListagemPacientes::new);
        return(ResponseEntity.ok(page));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarPacientes(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarinformacoesPaciente(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirPaciente(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
        String mensagem = "Paciente excluído com sucesso";

        return ResponseEntity.ok(mensagem);
    }

    @GetMapping ("/{id}")
    public ResponseEntity detalharPaciente(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}

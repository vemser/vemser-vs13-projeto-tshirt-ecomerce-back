package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.PessoaControllerInterface;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController implements PessoaControllerInterface {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<Pessoa>> buscarTodasPessoas() throws RegraDeNegocioException {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Pessoa> pessoas = pessoaService.buscarTodasPessoas(pageable);
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(idPessoa);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @GetMapping("/buscarPorCpf/{cpf}")
    public ResponseEntity<Pessoa> buscarPorCpf(@Valid @PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Pessoa> cadastrarPessoa(@Valid @RequestBody PessoaCreateDTO pessoaDTO) throws RegraDeNegocioException {
        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoaDTO);
        return new ResponseEntity<>(pessoaCadastrada, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{idPessoa}")
    public ResponseEntity<Pessoa> atualizarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa, @RequestBody PessoaUpdateDTO pessoaDTO) throws RegraDeNegocioException {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(idPessoa, pessoaDTO);
        return new ResponseEntity<>(pessoaAtualizada, HttpStatus.OK);
    }

    @PutMapping("/inativar/{idPessoa}")
    public ResponseEntity<Void> inativarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.inativarPessoa(idPessoa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/ativar/{idPessoa}")
    public ResponseEntity<Void> ativarPessoa(@Valid @PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.ativarPessoa(idPessoa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{idPessoa}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        pessoaService.deletarPessoa(idPessoa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<Pessoa>> buscarTodasPessoas(Pageable pageable) throws RegraDeNegocioException {
        Page<Pessoa> pessoas = pessoaService.buscarTodasPessoas(pageable);
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @GetMapping("/{idPessoa}")
    public ResponseEntity<Pessoa> buscarPorId(Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(idPessoa);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @GetMapping("/buscarPorCpf/{cpf}")
    public ResponseEntity<Pessoa> buscarPorCpf(@PathVariable("cpf") String cpf) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.buscarPessoaPorCpf(cpf);
        return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Pessoa> cadastrarPessoa(PessoaCreateDTO pessoaDTO) throws RegraDeNegocioException {
        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoaDTO);
        return new ResponseEntity<>(pessoaCadastrada, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{idPessoa}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Integer idPessoa, @RequestBody PessoaCreateDTO pessoaDTO) throws RegraDeNegocioException {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(idPessoa, pessoaDTO);
        return new ResponseEntity<>(pessoaAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{idPessoa}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Integer idPessoa) {
        pessoaService.deletarPessoa(idPessoa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

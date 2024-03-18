package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public Pessoa criarPessoa(PessoaCreateDTO pessoaCreateDTO) {
        validarPessoa(pessoaCreateDTO);

        Pessoa pessoa = objectMapper.convertValue(pessoaCreateDTO, Pessoa.class);

        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());
        pessoa.setEndereco(pessoaCreateDTO.getEndereco());

        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Integer idPessoa, PessoaCreateDTO pessoaCreateDTO) {
        Pessoa pessoa = existID(idPessoa);
        validarPessoa(pessoaCreateDTO);

        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());
        pessoa.setEndereco(pessoaCreateDTO.getEndereco());

        try {
            return pessoaRepository.save(pessoa);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao atualizar a pessoa: " + e.getMessage());
        }
    }

    public Page<Pessoa> buscarTodasPessoas(Pageable pageable) {
        Pageable pageableOrdenadoPorId = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("idPessoa"));
        return pessoaRepository.findAll(pageableOrdenadoPorId);
    }

    public Pessoa buscarPessoaPorId(Integer idPessoa) {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RuntimeException("ID não encontrado"));
    }

    public void deletarPessoa(Integer idPessoa) {
        if (!pessoaRepository.existsById(idPessoa)) {
            throw new RuntimeException("ID não encontrado");
        }

        pessoaRepository.deleteById(idPessoa);
    }

    private Pessoa existID(Integer idPessoa) {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RuntimeException("ID não encontrado"));
    }

    private void validarPessoa(PessoaCreateDTO pessoaCreateDTO) {
        if (pessoaCreateDTO.getCpf() != null && pessoaCreateDTO.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido");
        }
        if (pessoaCreateDTO.getDataNascimento() != null && pessoaCreateDTO.getDataNascimento().after(new Date())) {
            throw new IllegalArgumentException("A data de nascimento não pode estar no futuro");
        }
        if (pessoaRepository.existsByCpf(pessoaCreateDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já existente");
        }
    }

}

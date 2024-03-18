package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public Pessoa criarPessoa(PessoaCreateDTO pessoaCreateDTO) {
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

    public Pessoa buscarPessoaPorId(Integer idPessoa) {
        return pessoaRepository.findById(idPessoa).orElse(null);
    }

}

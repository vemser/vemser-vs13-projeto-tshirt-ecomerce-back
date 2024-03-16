package br.com.dbc.vemser.iShirts.services;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.PessoaRepository;
import br.com.dbc.vemser.iShirts.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    private String MESSAGE_CPF_CADASTRADO = "CPF já cadastrado";
    private String MESSAGE_EMAIL_CADASTRADO = "Email já cadastrado";


    public Pessoa create(PessoaCreateDTO pessoaDTO) {
        Pessoa pessoa = objectMapper.convertValue(pessoaDTO, Pessoa.class);

        if(pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new RuntimeException(MESSAGE_CPF_CADASTRADO);

        } if (usuarioRepository.existsByEmail(pessoa.getUsuario().getEmail())) {
            throw new RuntimeException(MESSAGE_EMAIL_CADASTRADO);
        }

        Pessoa pessoaCreate = objectMapper.convertValue(pessoaDTO, Pessoa.class);

        pessoaCreate.setNome(pessoaDTO.getNome());
        pessoaCreate.setSobrenome(pessoaDTO.getSobrenome());
        pessoaCreate.setCpf(pessoaDTO.getCpf());
        pessoaCreate.setCelular(pessoaDTO.getCelular());
        pessoaCreate.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoaCreate.setPreferencia(pessoaDTO.getPreferencia());
        pessoaCreate.setAtivo('S');
        pessoaCreate.setCriado(new Date());
        pessoaCreate.setEditado(new Date());

        return pessoaRepository.save(pessoaCreate);

    }
}

package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.model.enums.Ativo;
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

    private static final String CPF_INVALIDO = "CPF inválido";
    private static final String CPF_EXISTENTE = "CPF já existente";
    private static final String DATA_NASCIMENTO_FUTURA = "A data de nascimento não pode estar no futuro";
    private static final String ID_NAO_ENCONTRADO = "ID não encontrado";

    public Pessoa cadastrarPessoa(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        validarPessoa(pessoaCreateDTO);

        Pessoa pessoa = objectMapper.convertValue(pessoaCreateDTO, Pessoa.class);

        if (pessoa == null) {
            pessoa = new Pessoa();
        }

        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());
        pessoa.setIdUsuario(pessoaCreateDTO.getIdUsuario());

        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Integer idPessoa, PessoaUpdateDTO pessoaUpdateDTO) throws RegraDeNegocioException {
        Pessoa pessoa = existID(idPessoa);

        pessoa.setNome(pessoaUpdateDTO.getNome());
        pessoa.setSobrenome(pessoaUpdateDTO.getSobrenome());
        pessoa.setCpf(pessoaUpdateDTO.getCpf());
        pessoa.setCelular(pessoaUpdateDTO.getCelular());
        pessoa.setDataNascimento(pessoaUpdateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaUpdateDTO.getPreferencia());
        pessoa.setAtivo(pessoaUpdateDTO.getAtivo());

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

    public Pessoa buscarPessoaPorId(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RegraDeNegocioException(ID_NAO_ENCONTRADO));
    }

    public void inativarPessoa(Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RegraDeNegocioException(ID_NAO_ENCONTRADO));

        pessoa.setAtivo(String.valueOf(Ativo.INATIVO.getIndex()));

        pessoaRepository.save(pessoa);
    }

    public void ativarPessoa(Integer idPessoa) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new RegraDeNegocioException(ID_NAO_ENCONTRADO));

        pessoa.setAtivo(String.valueOf(Ativo.ATIVO.getIndex()));

        pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPessoaPorCpf(String cpf) throws RegraDeNegocioException {
        return pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException(CPF_INVALIDO));
    }

    public Pessoa buscarPessoaPorUsuario(Usuario usuario){
        return pessoaRepository.listarPorIdUsuario(usuario);
    }

    private Pessoa existID(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RegraDeNegocioException(ID_NAO_ENCONTRADO));
    }

    private void validarPessoa(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        if (pessoaCreateDTO.getCpf() != null && pessoaCreateDTO.getCpf().length() != 11) {
            throw new RegraDeNegocioException(CPF_INVALIDO);
        }
        if (pessoaCreateDTO.getDataNascimento() != null && pessoaCreateDTO.getDataNascimento().after(new Date())) {
            throw new RegraDeNegocioException(DATA_NASCIMENTO_FUTURA);
        }
        if (pessoaRepository.existsByCpf(pessoaCreateDTO.getCpf())) {
            throw new RegraDeNegocioException(CPF_EXISTENTE);
        }
    }

}

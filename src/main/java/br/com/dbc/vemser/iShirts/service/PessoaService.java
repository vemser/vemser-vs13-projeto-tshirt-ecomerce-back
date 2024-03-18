package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.pessoa.PessoaCreateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Endereco;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.EnderecoRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    private static final String CPF_INVALIDO = "CPF inválido";
    private static final String CPF_EXISTENTE = "CPF já existente";
    private static final String DATA_NASCIMENTO_FUTURA = "A data de nascimento não pode estar no futuro";
    private static final String ID_NAO_ENCONTRADO = "ID não encontrado";

    public Pessoa cadastrarPessoa(PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        validarPessoa(pessoaCreateDTO);

        Pessoa pessoa = objectMapper.convertValue(pessoaCreateDTO, Pessoa.class);

        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());

        Endereco endereco = new Endereco();

        endereco.setLogradouro(pessoaCreateDTO.getEndereco().getLogradouro());
        endereco.setNumero(pessoaCreateDTO.getEndereco().getNumero());
        endereco.setComplemento(pessoaCreateDTO.getEndereco().getComplemento());
        endereco.setReferencia(pessoaCreateDTO.getEndereco().getReferencia());
        endereco.setBairro(pessoaCreateDTO.getEndereco().getBairro());
        endereco.setCep(pessoaCreateDTO.getEndereco().getCep());
        endereco.setEstado(pessoaCreateDTO.getEndereco().getEstado());
        endereco.setPais(pessoaCreateDTO.getEndereco().getPais());

        endereco.setPessoa(pessoa);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        pessoa.setEndereco(enderecoSalvo);

        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Integer idPessoa, PessoaCreateDTO pessoaCreateDTO) throws RegraDeNegocioException {
        Pessoa pessoa = existID(idPessoa);

        validarPessoa(pessoaCreateDTO);

        pessoa.setNome(pessoaCreateDTO.getNome());
        pessoa.setSobrenome(pessoaCreateDTO.getSobrenome());
        pessoa.setCpf(pessoaCreateDTO.getCpf());
        pessoa.setCelular(pessoaCreateDTO.getCelular());
        pessoa.setDataNascimento(pessoaCreateDTO.getDataNascimento());
        pessoa.setPreferencia(pessoaCreateDTO.getPreferencia());
        pessoa.setAtivo(pessoaCreateDTO.getAtivo());

        Endereco endereco = pessoa.getEndereco();
        if (endereco != null) {
            endereco.setLogradouro(pessoaCreateDTO.getEndereco().getLogradouro());
            endereco.setNumero(pessoaCreateDTO.getEndereco().getNumero());
            endereco.setComplemento(pessoaCreateDTO.getEndereco().getComplemento());
            endereco.setReferencia(pessoaCreateDTO.getEndereco().getReferencia());
            endereco.setBairro(pessoaCreateDTO.getEndereco().getBairro());
            endereco.setCep(pessoaCreateDTO.getEndereco().getCep());
            endereco.setEstado(pessoaCreateDTO.getEndereco().getEstado());
            endereco.setPais(pessoaCreateDTO.getEndereco().getPais());

            enderecoRepository.save(endereco);
        }

        try {
            return pessoaRepository.save(pessoa);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro ao atualizar a pessoa: " + e.getMessage());
        }
    }

    public Page<Pessoa> buscarTodasPessoas(Pageable pageable) throws RegraDeNegocioException {
        Pageable pageableOrdenadoPorId = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("idPessoa"));
        return pessoaRepository.findAll(pageableOrdenadoPorId);
    }

    public Pessoa buscarPessoaPorId(Integer idPessoa) throws RegraDeNegocioException {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RuntimeException(ID_NAO_ENCONTRADO));
    }

    public void deletarPessoa(Integer idPessoa) {
        if (!pessoaRepository.existsById(idPessoa)) {
            throw new RuntimeException(ID_NAO_ENCONTRADO);
        }

        pessoaRepository.deleteById(idPessoa);
    }

    public Pessoa buscarPessoaPorCpf(String cpf) throws RegraDeNegocioException {
        return pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException(CPF_INVALIDO));
    }

    private Pessoa existID(Integer idPessoa) {
        return pessoaRepository.findById(idPessoa).orElseThrow(() -> new RuntimeException(ID_NAO_ENCONTRADO));
    }

    private void validarPessoa(PessoaCreateDTO pessoaCreateDTO) {
        if (pessoaCreateDTO.getCpf() != null && pessoaCreateDTO.getCpf().length() != 11) {
            throw new IllegalArgumentException(CPF_INVALIDO);
        }
        if (pessoaCreateDTO.getDataNascimento() != null && pessoaCreateDTO.getDataNascimento().after(new Date())) {
            throw new IllegalArgumentException(DATA_NASCIMENTO_FUTURA);
        }
        if (pessoaRepository.existsByCpf(pessoaCreateDTO.getCpf())) {
            throw new IllegalArgumentException(CPF_EXISTENTE);
        }
    }

}

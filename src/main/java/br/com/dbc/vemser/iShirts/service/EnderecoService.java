package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Endereco;
import br.com.dbc.vemser.iShirts.model.Pessoa;
import br.com.dbc.vemser.iShirts.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    private final PessoaService pessoaService;
    private final ObjectMapper objectMapper;


    public Page<EnderecoDTO> listarTodos(Integer tamanhoPagina, Integer paginaSolicitada ){

        Pageable pageable = PageRequest.of(paginaSolicitada,tamanhoPagina);
        Page<EnderecoDTO> enderecosPage = this.enderecoRepository.findAll(pageable).map(endereco -> retornarDTO(endereco));

        return enderecosPage;
    }

    public Page<EnderecoDTO> listarPorPessoa (Integer idPessoa,Integer tamanhoPagina, Integer paginaSolicitada){
        Pageable pageable = PageRequest.of(paginaSolicitada,tamanhoPagina);
        Page<EnderecoDTO> enderecoDTOs = this.enderecoRepository.findAllByIdPessoaIs(idPessoa,pageable).map(endereco -> retornarDTO(endereco));

        return enderecoDTOs;
    }

    public EnderecoDTO salvarEndereco(EnderecoCreateDTO dto) throws RegraDeNegocioException {
        Endereco enderecoEntidade = retornarEntidade(dto);

        Endereco novoEndereco = this.enderecoRepository.save(enderecoEntidade);

        return retornarDTO(novoEndereco);

    }

    public Endereco buscarPorId(Integer idEndereco) throws RegraDeNegocioException {
        Endereco endereco = this.enderecoRepository.findById(idEndereco).orElseThrow(
                () ->    new RegraDeNegocioException("Não foi encontrado nenhum endereço com este ID")
        );
        return endereco;
    }

    public EnderecoDTO buscarPorIdDto(Integer idEndereco) throws RegraDeNegocioException {
        Endereco endereco = this.enderecoRepository.findById(idEndereco).orElseThrow(
                () ->    new RegraDeNegocioException("Não foi encontrado nenhum endereço com este ID")
        );
        return retornarDTO(endereco);
    }


    public EnderecoDTO editarEndereco(EnderecoCreateDTO dto,Integer idEndereco) throws RegraDeNegocioException {
        Endereco enderecoAtualizado = buscarPorId(idEndereco);
        enderecoAtualizado.setBairro(dto.getBairro());
        enderecoAtualizado.setCep(dto.getCep());
        enderecoAtualizado.setEstado(dto.getEstado());
        enderecoAtualizado.setComplemento(dto.getComplemento());
        enderecoAtualizado.setReferencia(dto.getReferencia());
        enderecoAtualizado.setLogradouro(dto.getLogradouro());
        enderecoAtualizado.setNumero(dto.getNumero());
        enderecoAtualizado.setPais(dto.getPais());
        this.enderecoRepository.save(enderecoAtualizado);
        return retornarDTO(enderecoAtualizado);
    }

    public void deletar(Integer idEndereco) throws RegraDeNegocioException {
        buscarPorId(idEndereco);
        this.enderecoRepository.deleteById(idEndereco);
    }



    private Endereco retornarEntidade(EnderecoCreateDTO dto) throws RegraDeNegocioException {
        Endereco endereco = this.objectMapper.convertValue(dto,Endereco.class);
        Pessoa pessoa = this.pessoaService.buscarPessoaPorId(dto.getIdPessoa());
        endereco.setPessoa(pessoa);

        return endereco;

    }

    private EnderecoDTO retornarDTO(Endereco endereco){
    EnderecoDTO dto = this.objectMapper.convertValue(endereco,EnderecoDTO.class);
        return dto;
    }

}

package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.EnderecoControllerInterface;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.EnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/endereco")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoControllerInterface {

    private final EnderecoService enderecoService;



   public ResponseEntity<Page<EnderecoDTO>> listarTodos( Integer tamanhoPagina,  Integer paginaSolicitada ){
    return ResponseEntity.ok(this.enderecoService.listarTodos(tamanhoPagina,paginaSolicitada));
   }

   public ResponseEntity<EnderecoDTO> buscarPorIdDto( Integer idEndereco) throws RegraDeNegocioException {
        return ResponseEntity.ok(this.enderecoService.buscarPorIdDto(idEndereco));
   }

    public ResponseEntity<Page<EnderecoDTO>> listarTodos( Integer idPessoa, Integer tamanhoPagina,  Integer paginaSolicitada ){
        return ResponseEntity.ok(this.enderecoService.listarPorPessoa(idPessoa,tamanhoPagina,paginaSolicitada));
    }

    public ResponseEntity<EnderecoDTO> salvarEndereco(  EnderecoCreateDTO dto) throws RegraDeNegocioException {
        return new ResponseEntity<>(this.enderecoService.salvarEndereco(dto), HttpStatus.CREATED);
    }

    public ResponseEntity<EnderecoDTO> editarEndereco(  EnderecoCreateDTO dto, Integer idEndereco) throws RegraDeNegocioException {
        return ResponseEntity.ok().body(this.enderecoService.editarEndereco(dto,idEndereco));
    }

    public ResponseEntity<Void>deletar(Integer idEndereco) throws RegraDeNegocioException {
        this.enderecoService.deletar(idEndereco);
        return ResponseEntity.ok().build();
    }
}

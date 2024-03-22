package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.cargo.CargoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cargo.CargoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CargoControllerInterface {

    @Operation(summary = "Listagem dos cargos Cadastrados no banco", description = "Listagem dos cargos Cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem dos cargos Cadastrados  realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    public ResponseEntity<List<CargoDTO>> listarCargos() throws Exception;


    @Operation(summary = "Cadastra um cargo no banco", description = "Cadastra um cargo  no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro de um cargos  realizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    public ResponseEntity<String> adicionar(@RequestBody @Valid CargoCreateDTO cargoCreateDTO) ;


    @Operation(summary = "Deleta um cargo no banco", description = "Deleta um cargo  no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleção do cargos realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na busca de dados."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção.")
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer idCargo);
}

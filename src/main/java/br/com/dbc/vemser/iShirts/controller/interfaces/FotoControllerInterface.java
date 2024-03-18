package br.com.dbc.vemser.iShirts.controller.interfaces;

import br.com.dbc.vemser.iShirts.dto.foto.FotoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FotoControllerInterface {

    @Operation(summary = "Cadastrar uma foto", description = "Cadastra uma nova foto no banco de dados. Formatos suportados: WEBP, JPG, JPEG, GIF, PNG, BMP")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Foto criada com sucesso!"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<FotoDTO> createFoto(@RequestBody(required = true) MultipartFile arquivo) throws IOException, RegraDeNegocioException;

    @Operation(summary = "Atualizar uma foto", description = "Atualiza uma foto no banco de dados, formatos suportados: WEBP, JPG, JPEG, GIF, PNG, BMP")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foto atualizada com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Foto não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idFoto}")
    public ResponseEntity<FotoDTO> update(@PathVariable("idFoto") Integer idFoto,
                                          @RequestBody(required = true) MultipartFile arquivo) throws IOException, RegraDeNegocioException;

    @Operation(summary = "Buscar uma foto por ID", description = "Busca uma foto por ID no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foto encontrada com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Foto não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idFoto}")
    ResponseEntity<FotoDTO> getById(@PathVariable("idFoto") Integer idFoto) throws RegraDeNegocioException;


    @Operation(summary = "Deletar uma foto", description = "Deleta uma foto no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Foto deletada com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Foto não encontrada"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idFoto}")
    ResponseEntity<Void> delete(@PathVariable("idFoto") Integer idFoto) throws RegraDeNegocioException;
}

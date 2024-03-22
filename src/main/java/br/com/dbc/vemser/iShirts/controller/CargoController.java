package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.CargoControllerInterface;
import br.com.dbc.vemser.iShirts.dto.cargo.CargoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cargo.CargoDTO;
import br.com.dbc.vemser.iShirts.service.CargoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cargo")
@Validated
@Tag(name = "Cargo", description = "Controller responsável pelas operações relacionadas à cargo.")
@RequiredArgsConstructor
public class CargoController implements CargoControllerInterface {
    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoDTO>> listarCargos() throws Exception {
        return ResponseEntity.ok(cargoService.listarCargos());
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> adicionar(@RequestBody @Valid CargoCreateDTO cargoCreateDTO) {
        cargoService.criarCargo(cargoCreateDTO);
        return ResponseEntity.ok("Cargo cadastrado com sucesso!");
    }

    @DeleteMapping("/{idCargo}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idCargo) {
        cargoService.deletarCargo(idCargo);
        return ResponseEntity.ok().build();
    }
}

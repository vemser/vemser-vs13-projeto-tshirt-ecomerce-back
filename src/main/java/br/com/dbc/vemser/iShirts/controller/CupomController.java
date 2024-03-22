package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.CupomControllerInterface;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.CupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/cupom")
public class CupomController implements CupomControllerInterface {
    private final CupomService cupomService;

    @GetMapping
    public ResponseEntity<Page<CupomDTO>> listar(@PageableDefault(value = 10, page = 0, sort = "validade") Pageable pageable) {
        return new ResponseEntity<>(cupomService.listar(pageable), HttpStatus.OK);
    }

    @GetMapping("{idCupom}")
    public ResponseEntity<CupomDTO> buscarPorId(@PathVariable Integer idCupom) throws RegraDeNegocioException {
        return new ResponseEntity<>(cupomService.buscarPorId(idCupom), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CupomDTO> criar(@Valid @RequestBody CupomCreateDTO cupomDto) {
        return new ResponseEntity<>(cupomService.criar(cupomDto), HttpStatus.CREATED);
    }

    @PutMapping("{idCupom}")
    public ResponseEntity<CupomDTO> editar(@PathVariable Integer idCupom, @Valid @RequestBody CupomUpdateDTO cupomDto) throws RegraDeNegocioException {
        return new ResponseEntity<>(cupomService.editar(idCupom, cupomDto), HttpStatus.OK);
    }

    @DeleteMapping("{idCupom}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idCupom) throws RegraDeNegocioException {
        cupomService.deletar(idCupom);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

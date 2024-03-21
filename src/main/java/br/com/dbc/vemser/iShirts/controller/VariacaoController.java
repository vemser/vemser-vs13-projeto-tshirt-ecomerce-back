package br.com.dbc.vemser.iShirts.controller;

import br.com.dbc.vemser.iShirts.controller.interfaces.VariacaoControllerInterface;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.service.VariacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/variacao")
@Validated
@RequiredArgsConstructor
@Tag(name = "Variação", description = "Crud de variação")
public class VariacaoController implements VariacaoControllerInterface {

    private final VariacaoService variacaoService;

    @PostMapping
    public ResponseEntity<VariacaoDTO> criarVariacao(@RequestBody @Valid VariacaoCreateDTO variacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(variacaoService.criarVariacao(variacaoCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/por-id/{id}")
    public ResponseEntity<VariacaoDTO> listarPorID(@PathVariable("id") Integer id) throws Exception {
        return new ResponseEntity<>(variacaoService.listarPorID(id), HttpStatus.OK);
    }

    @GetMapping("/todos-variacoes")
    public ResponseEntity<Page<VariacaoDTO>> listarVariacoes(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return new ResponseEntity<>(variacaoService.listarVariacoes(pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariacaoDTO> editarVariacao(@PathVariable("id") Integer id, @RequestBody VariacaoDTO variacaoDTO) throws Exception {
        return new ResponseEntity<>(variacaoService.editarVariacao(id, variacaoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarVariacao(@PathVariable("id") Integer id) throws Exception {
        variacaoService.deletarVariacao(id);
        return new ResponseEntity<>("Variação deletada com sucesso", HttpStatus.OK);
    }

}

package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.cupom.CupomCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Cupom;
import br.com.dbc.vemser.iShirts.repository.CupomRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockCupom;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CupomService - Test")
public class CupomServiceTest {
    @InjectMocks
    private CupomService cupomService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CupomRepository cupomRepository;

    private static final Cupom cupomMock = MockCupom.retornarCupom();
    private static final CupomDTO cupomDTOMock = MockCupom.retornarCupomDTO(cupomMock);
    private static final CupomCreateDTO cupomCreateDTOMock = MockCupom.retornarCupomCreateDTO(cupomMock);
    private static final CupomUpdateDTO cupomUpdateDTOMock = MockCupom.retornarCupomUpdateDTO(cupomMock);
    private static final List<Cupom> cuponsMock = MockCupom.retornarCupons(cupomMock);
    private static final List<CupomDTO> cuponsDTOMock = MockCupom.retornarCuponsDTO(cupomDTOMock);

    @Test
    @DisplayName("Deveria listar cupons com sucesso")
    public void deveriaListarCupons() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cupom> listaMock = new PageImpl<>(cuponsMock, pageable, cuponsMock.size());

        when(objectMapper.convertValue(any(Cupom.class), eq(CupomDTO.class))).thenReturn(cupomDTOMock);
        when(cupomRepository.findAll(pageable)).thenReturn(listaMock);

        Page<CupomDTO> listaDTORetornada = cupomService.listar(pageable);

        assertNotNull(listaDTORetornada);
        assertEquals(cuponsDTOMock.size(), listaDTORetornada.getContent().size());
        assertIterableEquals(cuponsDTOMock, listaDTORetornada.getContent());
    }

    @Test
    @DisplayName("Deveria encontrar cupom por id com sucesso")
    public void deveriaEncontrarCupomPorId() throws Exception {
        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomDTOMock);

        CupomDTO cupomDTO = cupomService.buscarPorId(cupomMock.getIdCupom());

        assertNotNull(cupomDTO);
        assertEquals(cupomMock.getIdCupom(), cupomDTO.getIdCupom());
        assertEquals(cupomMock.getValorMinimo(), cupomDTO.getValorMinimo());
        assertEquals(cupomMock.getValor(), cupomDTO.getValor());
        assertEquals(cupomMock.getValidade(), cupomDTO.getValidade());
        assertEquals(cupomMock.getLimiteUso(), cupomDTO.getLimiteUso());
    }

    @Test
    @DisplayName("Não deveria encontrar um cupom inexistente")
    public void naoDeveriaEncontrarCupomInexistente() {
        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> cupomService.buscarPorId(cupomMock.getIdCupom()));
    }

    @Test
    @DisplayName("Deveria criar um cupom com sucesso")
    public void deveriaCriarCupom() {
        when(objectMapper.convertValue(cupomCreateDTOMock, Cupom.class)).thenReturn(cupomMock);
        when(cupomRepository.save(any())).thenReturn(cupomMock);
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomDTOMock);

        CupomDTO cupom = cupomService.criar(cupomCreateDTOMock);

        assertNotNull(cupom);
        assertEquals(cupomDTOMock, cupom);
    }

    @Test
    @DisplayName("Deveria editar um cupom com sucesso")
    public void deveriaEditarCupom() throws RegraDeNegocioException {
        CupomDTO cupomAntigo = new CupomDTO();
        BeanUtils.copyProperties(cupomDTOMock, cupomAntigo);
        cupomDTOMock.setCodigo(123456);

        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));
        when(cupomRepository.save(any())).thenReturn(cupomMock);
        when(objectMapper.convertValue(cupomUpdateDTOMock, Cupom.class)).thenReturn(cupomMock);
        when(objectMapper.convertValue(cupomMock, CupomDTO.class)).thenReturn(cupomAntigo);

        CupomDTO cupomAtualizado = cupomService.editar(cupomMock.getIdCupom(), cupomUpdateDTOMock);

        assertNotNull(cupomAtualizado);
        assertNotEquals(cupomDTOMock, cupomAtualizado);
        assertNotEquals(cupomDTOMock.getCodigo(), cupomAtualizado.getCodigo());
    }

    @Test
    @DisplayName("Deveria deletar um cupom com sucesso")
    public void deveriaDeletarCupom() throws Exception {
        when(cupomRepository.findById(cupomMock.getIdCupom())).thenReturn(Optional.of(cupomMock));

        cupomService.deletar(cupomMock.getIdCupom());

        verify(cupomRepository, times(1)).deleteById(cupomMock.getIdCupom());
    }
}

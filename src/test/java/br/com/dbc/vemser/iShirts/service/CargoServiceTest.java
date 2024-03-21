package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.cargo.CargoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cargo.CargoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Cargo;
import br.com.dbc.vemser.iShirts.repository.CargoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private CargoService cargoService;

    @Test
    @DisplayName("Deve criar cargo com sucesso")
    public void criarCargoComSucesso() {
        CargoCreateDTO cargoCreateDTOMock = retornarCargoCreateDTO();
        Cargo cargoMock = retornarCargoEntity();

        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargoMock);

        cargoService.criarCargo(cargoCreateDTOMock);

        verify(cargoRepository, times(1)).save(any(Cargo.class));
    }

    @Test
    @DisplayName("Deve deletar cargo com sucesso")
    public void deletarCargoComSucesso() {
        Integer idAleatorio = new Random().nextInt();

        cargoService.deletarCargo(idAleatorio);

        verify(cargoRepository, times(1)).deleteById(idAleatorio);
    }

    @Test
    public void listarCargosComSucesso() throws Exception {
        when(cargoRepository.findAll()).thenReturn(List.of(retornarCargoEntity()));

        List<CargoDTO> cargoDTOS = cargoService.listarCargos();

        assertNotNull(cargoDTOS);
        assertEquals(1, cargoDTOS.size());
    }

    @Test
    @DisplayName("Deve retornar uma exceção ao tentar listar uma lista vazia de cargos")
    public void retornarExecaoAolistarCargos() {
        when(cargoRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RegraDeNegocioException.class, () -> {
            cargoService.listarCargos();
        });
    }

    @Test
    @DisplayName("Deve retornar um cargo pelo id")
    public void retornarCargoPorId() throws RegraDeNegocioException {
        Cargo cargoMock = retornarCargoEntity();
        Integer idAleatorio = new Random().nextInt();

        when(cargoRepository.findById(idAleatorio)).thenReturn(Optional.of(cargoMock));

        Cargo cargoRetornado = cargoService.buscarCargoPorId(idAleatorio);

        assertEquals(cargoMock, cargoRetornado);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar buscar um cargo com id inexistente")
    public void retornarExcecaoQuandoCargoNaoExistente() {
        Integer idAleatorio = new Random().nextInt();

        when(cargoRepository.findById(idAleatorio)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            cargoService.buscarCargoPorId(idAleatorio);
        });
    }

    @Test
    @DisplayName("Deve retornar um cargo pelo nome")
    public void retornarCargoPorNome() throws RegraDeNegocioException {
        Cargo cargoMock = retornarCargoEntity();
        String nome = cargoMock.getDescricao();

        when(cargoRepository.findByDescricao(nome)).thenReturn(Optional.of(cargoMock));

        Cargo cargoRetornado = cargoService.buscarCargoPorDescricao(nome);

        assertEquals(cargoMock, cargoRetornado);
    }

    @Test
    @DisplayName("Deve retornar uma exceção ao tentar buscar um cargo com nome inexistente")
    public void retornarExcecaoQuandoCargoNaoExistentePorNome() {
        String nome = "Cargo de Teste";

        when(cargoRepository.findByDescricao(nome)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            cargoService.buscarCargoPorDescricao(nome);
        });
    }

    private static CargoCreateDTO retornarCargoCreateDTO() {
        CargoCreateDTO cargoCreateDTO = new CargoCreateDTO();
        cargoCreateDTO.setDescricao("Cargo de Teste");
        return cargoCreateDTO;
    }

    private static Cargo retornarCargoEntity() {
        Cargo cargo = new Cargo();
        cargo.setIdCargo(1);
        cargo.setDescricao("Cargo de Teste");
        return cargo;
    }

}

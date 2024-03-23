package br.com.dbc.vemser.iShirts.service;

import org.springframework.mock.web.MockMultipartFile;
import br.com.dbc.vemser.iShirts.dto.foto.FotoDTO;
import br.com.dbc.vemser.iShirts.model.Foto;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.FotoRepository;
import br.com.dbc.vemser.iShirts.service.mocks.MockFoto;
import br.com.dbc.vemser.iShirts.service.mocks.MockVariacao;
import br.com.dbc.vemser.iShirts.utils.MediaTypeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Foto Service - Test")
class FotoServiceTest {

    @Mock
    private FotoRepository fotoRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private VariacaoService variacaoService;
    @Mock
    private  MediaTypeUtil mediaTypeUtil;
    @InjectMocks
    private FotoService fotoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Deve criar foto com sucesso")
    void createFotoEdicao() throws RegraDeNegocioException, IOException {
        Variacao variacao = MockVariacao.retornarEntity();
        Foto fotoEntity = MockFoto.retornarEntity();
        FotoDTO foto = MockFoto.retornarFotoDTOPorEntity(fotoEntity);
        MultipartFile arquivo = MockFoto.retornarArquivo();

        when(mediaTypeUtil.getTipoArquivo(any())).thenReturn("JPEG");
        when(variacaoService.buscarPorId(variacao.getIdVariacao())).thenReturn(variacao);
        when(objectMapper.convertValue(any(), eq(FotoDTO.class))).thenReturn(foto);
        when(fotoRepository.save(any())).thenReturn(fotoEntity);

        FotoDTO fotoResponse = fotoService.criar(arquivo, variacao.getIdVariacao());

        assertNotNull(fotoResponse);
        assertEquals(fotoEntity.getIdFoto(), fotoResponse.getIdFoto());
        assertEquals(fotoEntity.getArquivo(), fotoResponse.getArquivo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar foto com nome do arquivo muito grande")
    void createFotoNomeMuitoGrande() throws RegraDeNegocioException, IOException {
        Variacao variacao = MockVariacao.retornarEntity();
        MultipartFile arquivo = new MockMultipartFile(
                "example.jpg",
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                            "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                            "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd" +
                        ".jpg",
                "image/jpeg",
                "conteudo".getBytes()
        );

        when(mediaTypeUtil.getTipoArquivo(any())).thenReturn("JPEG");
        when(variacaoService.buscarPorId(variacao.getIdVariacao())).thenReturn(variacao);

        assertThrows(RegraDeNegocioException.class, () -> {
            fotoService.criar(arquivo, variacao.getIdVariacao());
        });
    }


    @Test
    @DisplayName("Deve atualizar a foto no banco com sucesso")
    void updateFoto() throws RegraDeNegocioException, IOException {
        Foto fotoEntity = MockFoto.retornarEntity();
        FotoDTO foto = MockFoto.retornarFotoDTOPorEntity(fotoEntity);
        MultipartFile arquivo = MockFoto.retornarArquivo();

        when(fotoRepository.findById(foto.getIdFoto())).thenReturn(Optional.of(fotoEntity));
        when(objectMapper.convertValue(any(), eq(FotoDTO.class))).thenReturn(foto);
        when(fotoRepository.save(any())).thenReturn(fotoEntity);
        when(mediaTypeUtil.getTipoArquivo(arquivo)).thenReturn("jpg");

        FotoDTO fotoResponse = fotoService.atualizar(foto.getIdFoto(), arquivo);

        assertNotNull(fotoResponse);
        assertEquals(fotoEntity.getIdFoto(), fotoResponse.getIdFoto());
    }
    @Test
    @DisplayName("Deve lançar exceção FormatoInválido")
    void deveLancarExcecaoFormatoInvalido() throws RegraDeNegocioException, IOException {
        Foto fotoEntity = MockFoto.retornarEntity();
        FotoDTO foto = MockFoto.retornarFotoDTOPorEntity(fotoEntity);
        MultipartFile arquivo = MockFoto.retornarArquivo();

        when(fotoRepository.findById(foto.getIdFoto())).thenReturn(Optional.of(fotoEntity));
        when(objectMapper.convertValue(any(), eq(FotoDTO.class))).thenReturn(foto);
        when(fotoRepository.save(any())).thenReturn(fotoEntity);
        when(mediaTypeUtil.getTipoArquivo(arquivo)).thenReturn("pdf");

        assertThrows(RegraDeNegocioException.class, () -> {
            FotoDTO fotoResponse = fotoService.atualizar(foto.getIdFoto(), arquivo);
        });

    }

    @Test
    @DisplayName("Deveria retornar uma foto DTO por ID com sucesso")
    void deveRetornarFotoDTO() throws RegraDeNegocioException {

        Foto fotoEntity = new Foto();
        fotoEntity.setIdFoto(1);
        when(fotoRepository.findById(anyInt())).thenReturn(java.util.Optional.of(fotoEntity));

        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setIdFoto(1);
        when(objectMapper.convertValue(fotoEntity, FotoDTO.class)).thenReturn(fotoDTO);

        FotoDTO returnedFotoDTO = fotoService.obterPorId(1);

        assertEquals(fotoDTO, returnedFotoDTO);
    }

    @Test
    @DisplayName("Não deveria retornar uma foto DTO por ID")
    void naoDeveRetornarFotoDTO() {

        when(fotoRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            fotoService.obterPorId(1);
        });
    }

    @Test
    @DisplayName("Deveria deletar uma foto com sucesso")
    void deveDeletarFoto() throws RegraDeNegocioException {

        Foto fotoEntity = new Foto();
        fotoEntity.setIdFoto(1);
        when(fotoRepository.findById(anyInt())).thenReturn(java.util.Optional.of(fotoEntity));

        fotoService.deletar(1);

        verify(fotoRepository, times(1)).delete(fotoEntity);
    }

    @Test
    @DisplayName("Não deveria deletar uma foto inexistente")
    void naoDeveDeletarFotoInexistente() {

        when(fotoRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            fotoService.deletar(1);
        });

        verify(fotoRepository, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("Deveria encontrar a foto por ID com sucesso")
    void deveEncontrarFotoPorId() throws RegraDeNegocioException {

        Foto fotoEntity = new Foto();
        fotoEntity.setIdFoto(1);
        when(fotoRepository.findById(anyInt())).thenReturn(Optional.of(fotoEntity));

        Foto fotoEncontrada = fotoService.buscarPorId(1);

        assertEquals(fotoEntity, fotoEncontrada);
    }

    @Test
    @DisplayName("Não deveria encontrar a foto por ID")
    void deveriaNaoEncontrarFotoPorId() {

        when(fotoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> {
            fotoService.buscarPorId(1);
        });
    }

    private static FotoDTO getFotoDTO() {

        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setIdFoto(10);
        fotoDTO.setArquivo(new byte[]{0x12, 0x34, 0x56, 0x78});

        return fotoDTO;
    }

    private Foto getFoto() {
        Foto fotoEntity = new Foto();
        fotoEntity.setIdFoto(10);
        fotoEntity.setArquivo(new byte[]{0x12, 0x34, 0x56, 0x78});

        return fotoEntity;
    }

}
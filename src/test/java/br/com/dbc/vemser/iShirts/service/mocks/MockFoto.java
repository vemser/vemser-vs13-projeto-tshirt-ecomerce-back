package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.foto.FotoDTO;
import br.com.dbc.vemser.iShirts.model.Foto;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class MockFoto {
    public static Foto retornarEntity() throws IOException {
        MultipartFile arquivo = retornarArquivo();
        String tipoArquivo = MediaType.parseMediaType(arquivo.getContentType()).getSubtype();
        String nome = LocalDateTime.now() + "_" + arquivo.getOriginalFilename();
        Foto fotoEntity = new Foto();
        fotoEntity.setArquivo(arquivo.getBytes());
        fotoEntity.setIdFoto(1);
        return fotoEntity;
    }

    public static FotoDTO retornarFotoDTOPorEntity(Foto fotoEntity) throws IOException {
        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setIdFoto(fotoEntity.getIdFoto());
        fotoDTO.setArquivo(fotoEntity.getArquivo());
        return fotoDTO;
    }
    public static MultipartFile retornarArquivo() throws IOException {

        MultipartFile arquivo = new MockMultipartFile(
                "file",
                "example.jpg",
                "image/jpeg",
                new byte[1024]
        );
        return arquivo;
    }

}

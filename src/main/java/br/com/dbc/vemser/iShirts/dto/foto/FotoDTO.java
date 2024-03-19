package br.com.dbc.vemser.iShirts.dto.foto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FotoDTO {

    @Schema(description = "Id da Foto", example = "1")
    private Integer idFoto;
    @Schema(description = "Arquivo")
    private byte[] arquivo;
}

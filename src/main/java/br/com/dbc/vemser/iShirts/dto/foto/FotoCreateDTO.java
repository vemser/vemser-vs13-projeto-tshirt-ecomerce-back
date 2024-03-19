package br.com.dbc.vemser.iShirts.dto.foto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FotoCreateDTO {
    @Schema(description = "Arquivo, formatos suportados: WEBP, JPG, JPEG, GIF, PNG, BMP, TIFF")
    private byte[] arquivo;
}

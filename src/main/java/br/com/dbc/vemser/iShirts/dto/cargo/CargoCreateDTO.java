package br.com.dbc.vemser.iShirts.dto.cargo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoCreateDTO {
    @Schema(description = "Descrição do cargo", required = true)
    @NotNull
    private String descricao;
}

package br.com.dbc.vemser.iShirts.dto.cargo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoCreateDTO {
    @NotNull
    private String descricao;
}

package br.com.dbc.vemser.iShirts.dto.cargo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CargoDTO {
    @NotNull
    private String descricao;
}

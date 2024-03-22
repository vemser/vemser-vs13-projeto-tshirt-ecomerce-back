package br.com.dbc.vemser.iShirts.dto.cupom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CupomCreateDTO {
    @NotNull
    @Schema(description = "Código do cupom", example = "987654", required = true)
    private Integer codigo;

    @NotNull
    @Schema(description = "Valor do cupom", example = "250", required = true)
    private Double valor;

    @NotNull
    @Future
    @Schema(description = "Validade do cupom", example = "2024-05-30T23:59:59", required = true)
    private LocalDateTime validade;

    @NotNull
    @Schema(description = "Limite de uso do cupom", example = "1000", required = true)
    private Integer limiteUso;

    @NotNull
    @Schema(description = "Valor mínimo para uso do cupom", example = "500", required = true)
    private Double valorMinimo;
}

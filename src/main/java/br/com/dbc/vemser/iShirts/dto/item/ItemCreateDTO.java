package br.com.dbc.vemser.iShirts.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateDTO {
    @NotNull
    private Integer idVariacao;
    @NotNull
    @Min(value = 1)
    private Integer quantidade;
}

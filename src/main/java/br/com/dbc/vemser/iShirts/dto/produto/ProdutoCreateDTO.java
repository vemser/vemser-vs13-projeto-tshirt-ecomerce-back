package br.com.dbc.vemser.iShirts.dto.produto;

import br.com.dbc.vemser.iShirts.model.enums.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCreateDTO {

    @Schema(description = "Categoria do produto a ser anunciado", example = "Masculino")
    @NotNull(message = "A categoria não pode ser nula.")
    private Categoria categoria;

    @Schema(description = "Título do produto a ser anunciado", example = "Camisa preta.")
    @NotBlank(message = "O título não pode ser nulo nem em branco")
    @Size(max = 255, message = "O título não pode ter mais de 255 caracteres")
    private String titulo;

    @Schema(description = "Descrição do produto a ser anunciado", example = "Produto para moda masculina")
    @NotBlank(message = "A descrição não pode ser nula ou em branco")
    @Size(max = 255, message = "A descrição não pode possuir mais de 255 caracteres")
    private String descricao;

    @Schema(description = "Tecido do produto a ser anunciado", example = "Algodão")
    @NotBlank(message = "O tecido não pode ser nulo ou em branco")
    @Size(max = 60, message = "O tecido não pode possuir mais de 60 caracteres")
    private String tecido;

}

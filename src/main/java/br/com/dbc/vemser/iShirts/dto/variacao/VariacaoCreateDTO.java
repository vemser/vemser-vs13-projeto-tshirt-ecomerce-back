package br.com.dbc.vemser.iShirts.dto.variacao;

import br.com.dbc.vemser.iShirts.model.Foto;
import br.com.dbc.vemser.iShirts.model.Produto;
import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
public class VariacaoCreateDTO {

    @NotNull(message = "O produto não pode ser nulo")
    private Produto produto;

    @NotNull(message = "A foto não pode ser nula")
    private Foto foto;

    @NotBlank(message = "O SKU não pode estar em branco")
    private String sku;

    @NotBlank(message = "A cor não pode estar em branco")
    private String cor;

    @NotBlank(message = "O tamanho não pode estar em branco")
    private String tamanho;

    @NotNull(message = "O preço não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "A taxa de desconto não pode ser nula")
    @Min(value = 0, message = "A taxa de desconto não pode ser negativa")
    private Integer taxaDesconto;

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "O campo criado não pode ser nulo")
    private Timestamp criado;

    @NotNull(message = "O campo editado não pode ser nulo")
    private Timestamp editado;
}

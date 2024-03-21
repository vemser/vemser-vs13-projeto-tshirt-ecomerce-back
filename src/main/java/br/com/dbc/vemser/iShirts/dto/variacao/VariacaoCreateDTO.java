package br.com.dbc.vemser.iShirts.dto.variacao;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class VariacaoCreateDTO {

    @NotBlank(message = "O idProduto não pode estar em branco ou nulo")
    private Integer idProduto;

    @NotBlank(message = "O SKU não pode estar em branco")
    private String sku;

    @NotBlank(message = "A cor não pode estar em branco")
    private String cor;

    @NotBlank(message = "O tamanho não pode estar em branco")
    private String tamanho;

    @NotNull(message = "O preço não pode ser nulo")
    private BigDecimal preco;

    @NotNull(message = "A taxa de desconto não pode ser nula")
    private Integer taxaDesconto;

    @NotNull(message = "O campo ativo não pode ser nulo")
    private String ativo;
}

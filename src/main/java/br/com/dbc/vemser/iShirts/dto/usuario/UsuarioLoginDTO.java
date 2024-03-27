package br.com.dbc.vemser.iShirts.dto.usuario;

import br.com.dbc.vemser.iShirts.model.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
    private String email;
    private Set<Cargo> cargos;
}

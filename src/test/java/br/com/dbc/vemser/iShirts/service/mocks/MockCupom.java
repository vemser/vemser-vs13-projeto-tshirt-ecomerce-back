package br.com.dbc.vemser.iShirts.service.mocks;

import br.com.dbc.vemser.iShirts.dto.cupom.CupomCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomUpdateDTO;
import br.com.dbc.vemser.iShirts.model.Cupom;
import java.time.LocalDateTime;
import java.util.List;

public class MockCupom {
    public static Cupom retornarCupom(){
        Cupom cupom = new Cupom();
        cupom.setIdCupom(1);
        cupom.setCodigo(987654);
        cupom.setValor(250.00);
        cupom.setValidade(LocalDateTime.now());
        cupom.setValorMinimo(1000.00);
        return cupom;
    }

    public static CupomDTO retornarCupomDTO(Cupom cupom){
        CupomDTO cupomDTO = new CupomDTO();
        cupomDTO.setIdCupom(cupom.getIdCupom());
        cupomDTO.setCodigo(cupom.getCodigo());
        cupomDTO.setValor(cupom.getValor());
        cupomDTO.setValidade(cupom.getValidade());
        cupomDTO.setValorMinimo(cupom.getValorMinimo());
        return cupomDTO;
    }

    public static CupomCreateDTO retornarCupomCreateDTO(Cupom cupom){
        CupomCreateDTO cupomDTO = new CupomCreateDTO();
        cupomDTO.setCodigo(cupom.getCodigo());
        cupomDTO.setValor(cupom.getValor());
        cupomDTO.setValidade(cupom.getValidade());
        cupomDTO.setValorMinimo(cupom.getValorMinimo());
        return cupomDTO;
    }

    public static CupomUpdateDTO retornarCupomUpdateDTO(Cupom cupom){
        CupomUpdateDTO cupomDTO = new CupomUpdateDTO();
        cupomDTO.setCodigo(cupom.getCodigo());
        cupomDTO.setValor(cupom.getValor());
        cupomDTO.setValidade(cupom.getValidade());
        cupomDTO.setValorMinimo(cupom.getValorMinimo());
        return cupomDTO;
    }

    public static List<Cupom> retornarCupons(Cupom cupom){
        return List.of(cupom, cupom, cupom, cupom, cupom);
    }

    public static List<CupomDTO> retornarCuponsDTO(CupomDTO cupomDTO){
        return List.of(cupomDTO, cupomDTO, cupomDTO, cupomDTO, cupomDTO);
    }
}

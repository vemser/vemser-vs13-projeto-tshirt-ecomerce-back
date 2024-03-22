package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.cupom.CupomCreateDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomDTO;
import br.com.dbc.vemser.iShirts.dto.cupom.CupomUpdateDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Cupom;
import br.com.dbc.vemser.iShirts.repository.CupomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CupomService {
    private final CupomRepository cupomRepository;
    private final ObjectMapper objectMapper;

    private static final String ID_NAO_ENCONTRADO = "ID não encontrado";

    public Page<CupomDTO> listar(Pageable pageable) {
        Page<Cupom> cupons = cupomRepository.findAll(pageable);
        return cupons.map(this::converterDTO);
    }

    public CupomDTO buscarPorId(Integer idCupom) throws RegraDeNegocioException {
        Cupom cupom = cupomRepository.findById(idCupom).orElseThrow(() -> new RegraDeNegocioException(ID_NAO_ENCONTRADO));
        return converterDTO(cupom);
    }

    public CupomDTO criar(CupomCreateDTO cupomDto) {
        Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
        return converterDTO(cupomRepository.save(cupom));
    }

    public CupomDTO editar(Integer idCupom, CupomUpdateDTO cupomDto) throws RegraDeNegocioException {
        buscarPorId(idCupom);

        Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
        cupom.setIdCupom(idCupom);

        return converterDTO(cupomRepository.save(cupom));
    }

    public void deletar(Integer idCupom) throws RegraDeNegocioException {
        buscarPorId(idCupom);
        cupomRepository.deleteById(idCupom);
    }

    private CupomDTO converterDTO(Cupom cupom) {
        return objectMapper.convertValue(cupom, CupomDTO.class);
    }
}

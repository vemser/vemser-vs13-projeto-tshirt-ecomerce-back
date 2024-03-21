package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemDTO;
import br.com.dbc.vemser.iShirts.dto.variacao.VariacaoDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Item;
import br.com.dbc.vemser.iShirts.model.Variacao;
import br.com.dbc.vemser.iShirts.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;
    private final VariacaoService variacaoService;

    public Item getItemById(Integer id) throws RegraDeNegocioException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Item não encontrado com o ID: " + id));
    }
    public Item criarItem(ItemCreateDTO itemCreateDTO) throws RegraDeNegocioException {
        Variacao variacao = variacaoService.buscarPorId(itemCreateDTO.getIdVariacao());
        Item item = objectMapper.convertValue(itemCreateDTO, Item.class);
        item.setVariacao(variacao);
        calcularSubTotal(item);
        return item;
    }

    public  Item salvarItem(Item item){
        return itemRepository.save(item);
    }
    public List<Item> criarItens(List<ItemCreateDTO> itensDTO) throws RegraDeNegocioException {
        List<Item> itens = new ArrayList<>();
        for(ItemCreateDTO itemDTO : itensDTO){
            Item item =criarItem(itemDTO);
            itens.add(item);
        }
       return itemRepository.saveAll(itens);
    }
    public void atualizarItens(List<Item> itens) {
        for(Item item : itens){
            calcularSubTotal(item);
            itemRepository.save(item);
        }
    }

    public List<ItemDTO> converterDTO(List<Item> itens) {
        List<ItemDTO> itensDTO = new ArrayList<>();
        for(Item item : itens){
            ItemDTO itemDTO = objectMapper.convertValue(item, ItemDTO.class);
            itensDTO.add(itemDTO);
        }
        return itensDTO;
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public void calcularSubTotal(Item item) {
        BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
        BigDecimal preco = BigDecimal.valueOf(item.getVariacao().getPreco());
        BigDecimal taxaDesconto = BigDecimal.valueOf(item.getVariacao().getTaxaDesconto());
        BigDecimal taxaDescontoDecimal = taxaDesconto.divide(BigDecimal.valueOf(100));

        BigDecimal subTotal = quantidade
                .multiply(preco.subtract(preco.multiply(taxaDescontoDecimal)));
        item.setSubTotal(subTotal);

    }


    public void deletePorId(Integer idItem) {
        itemRepository.deleteById(idItem);
    }
}

package br.com.dbc.vemser.iShirts.service;

import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoCreateDTO;
import br.com.dbc.vemser.iShirts.dto.carrinho.CarrinhoDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemCreateDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemDTO;
import br.com.dbc.vemser.iShirts.dto.item.ItemUpdateQuantidadeDTO;
import br.com.dbc.vemser.iShirts.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.iShirts.model.Carrinho;
import br.com.dbc.vemser.iShirts.model.Item;
import br.com.dbc.vemser.iShirts.model.Usuario;
import br.com.dbc.vemser.iShirts.repository.CarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemService itemService;
    private final UsuarioService usuarioService;

    public Carrinho buscarCarrinhoPorId(Integer id) throws RegraDeNegocioException {
        return carrinhoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Carrinho não encontrado com o ID: " + id));
    }

    public Carrinho buscarCarrinhoUsuarioLogado() throws RegraDeNegocioException {
        Usuario usuario = usuarioService.buscarUsuarioLogadoEntity();
        Carrinho carrinho = carrinhoRepository.findByUsuario(usuario);
        if(carrinho == null){
            carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
        }
        return carrinho;
    }

    public CarrinhoDTO criarCarrinho(CarrinhoCreateDTO carrinhoCreateDTO) throws RegraDeNegocioException {
        List<Item> itens = itemService.criarItens(carrinhoCreateDTO.getItens());
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        carrinho.setItens(itens);
        return converterDTO(carrinhoRepository.save(carrinho));
    }

    public CarrinhoDTO atualizarCarrinho(Integer id, Carrinho carrinho) throws RegraDeNegocioException {
        Carrinho carrinhoExistente = buscarCarrinhoPorId(id);
        carrinhoExistente.setItens(carrinho.getItens());
        return converterDTO(carrinhoRepository.save(carrinho));
    }

    public void deleteCarrinho(Integer id) throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoPorId(id);
        carrinhoRepository.delete(carrinho);
    }

    public CarrinhoDTO adicionarItemCarrinho(ItemCreateDTO itemCreateDTO) throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        Item item = itemService.salvarItem(itemService.criarItem(itemCreateDTO));

        carrinho.getItens().add(item);

        return converterDTO(carrinhoRepository.save(carrinho));
    }

    public CarrinhoDTO removerItemCarrinho(Integer idItem) throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        Item item = itemService.getItemById(idItem);

        carrinho.getItens().remove(item);
        itemService.delete(item);

        return converterDTO(carrinhoRepository.save(carrinho));
    }

    public void limparCarrinho() throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        for (Item item : carrinho.getItens()) {
            itemService.delete(item);
        }
        carrinho.getItens().clear();

        carrinhoRepository.save(carrinho);
    }
    public void limparCarrinhoPedidoFeito() throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        carrinho.getItens().clear();

        carrinhoRepository.save(carrinho);
    }

    public CarrinhoDTO atualizaQuantidadeItem(Integer idItem, ItemUpdateQuantidadeDTO quantidadeDTO) throws RegraDeNegocioException {
        Carrinho carrinho = buscarCarrinhoUsuarioLogado();
        Item item = carrinho.getItens().stream()
                .filter(itemCarr -> itemCarr.getIdItem().equals(idItem))
                .findFirst().orElseThrow(() -> new RegraDeNegocioException("Item não encontrado no carrinho"));
        item.setQuantidade(quantidadeDTO.getQuantidade());
        itemService.calcularSubTotal(item);
        return converterDTO(carrinho);
    }
    public CarrinhoDTO buscarCarrinho() throws RegraDeNegocioException {
        return converterDTO(buscarCarrinhoUsuarioLogado());
    }

    public CarrinhoDTO converterDTO(Carrinho carrinho){
        CarrinhoDTO carrinhoDTO = new CarrinhoDTO();
        carrinhoDTO.setIdUsuario(carrinho.getUsuario().getIdUsuario());
        List<ItemDTO> itensDTO = itemService.converterDTO(carrinho.getItens());
        carrinhoDTO.setItens(itensDTO);
        carrinho.setIdCarrinho(carrinho.getIdCarrinho());
        return carrinhoDTO;
    }

}

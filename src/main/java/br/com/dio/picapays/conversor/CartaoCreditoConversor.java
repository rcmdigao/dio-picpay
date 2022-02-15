package br.com.dio.picapays.conversor;

import br.com.dio.picapays.entities.Transacao;
import br.com.dio.picapays.dto.CartaoCreditoDTO;
import br.com.dio.picapays.dto.TransacaoDTO;
import br.com.dio.picapays.entities.CartaoCredito;
import br.com.dio.picapays.services.IUsuarioService;
import br.com.dio.picapays.utils.CartaoCreditoUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartaoCreditoConversor  extends ConversorBase<CartaoCredito, CartaoCreditoDTO> {

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public CartaoCreditoDTO converterEntidadeParaDto(CartaoCredito entidade) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Transacao, TransacaoDTO>() {
            @Override
            protected void configure() {
            }
        });
        return modelMapper.map(entidade, CartaoCreditoDTO.class);
    }

    @Override
    public CartaoCredito converterDtoParaEntidade(CartaoCreditoDTO dto) {
        return CartaoCredito
                .builder()
                .bandeira(dto.getBandeira())
                .numero(CartaoCreditoUtil.mascarar(dto.getNumero()))
                .numeroToken(dto.getNumeroToken())
                .usuario(usuarioService.consultarEntidade(dto.getUsuario().getLogin()))
                .build();
    }

}
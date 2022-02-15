package br.com.dio.picapays.services.impl;

import br.com.dio.picapays.entities.Transacao;
import br.com.dio.picapays.conversor.TransacaoConversor;
import br.com.dio.picapays.dto.TransacaoDTO;
import br.com.dio.picapays.repositories.TransacaoRepository;
import br.com.dio.picapays.services.ICartaoCreditoService;
import br.com.dio.picapays.services.IUsuarioService;
import br.com.dio.picapays.services.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Service
public class TransacaoService implements ITransacaoService {

    @Autowired
    private TransacaoConversor transacaoConversor;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ICartaoCreditoService CartaoCreditoService;


    @Override
    public TransacaoDTO processar(TransacaoDTO dto) {
        Transacao transacao = salvar(dto);
        CartaoCreditoService.salvar(dto.getCartaoCredito());
        usuarioService.atualizarSaldo(transacao, dto.getIsCartaoCredito());
        return transacaoConversor.converterEntidadeParaDto(transacao);
    }

    private Transacao salvar(TransacaoDTO dto) {
        Transacao transacao = transacaoConversor.converterDtoParaEntidade(dto);
        usuarioService.validar(transacao.getDestino(), transacao.getOrigem());
        return transacaoRepository.save(transacao);
    }

    @Override
    public Page<TransacaoDTO> listar(Pageable paginacao, String login) {
        Page<Transacao> transacoes = transacaoRepository.findByOrigem_LoginOrDestino_Login(login, login, paginacao);
        return transacaoConversor.converterPageEntidadeParaDto(transacoes);
    }

}

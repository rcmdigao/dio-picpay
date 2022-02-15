package br.com.dio.picapays.services;

import br.com.dio.picapays.dto.TransacaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ITransacaoService {

    TransacaoDTO processar(TransacaoDTO dto);

    Page<TransacaoDTO> listar(Pageable paginacao, String login);
}

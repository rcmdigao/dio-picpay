package br.com.dio.picapays.services;

import br.com.dio.picapays.entities.Transacao;
import br.com.dio.picapays.entities.Usuario;
import br.com.dio.picapays.dto.UsuarioDTO;

import java.util.List;

public interface IUsuarioService {

    Usuario consultarEntidade(String login);

    void validar(Usuario... usuarios);

    void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito);

    UsuarioDTO consultar(String login);

    List<UsuarioDTO> listar(String login);
}

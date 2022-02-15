package br.com.dio.picapays.services.impl;

import br.com.dio.picapays.constantes.MensagemValidacao;
import br.com.dio.picapays.entities.Usuario;
import br.com.dio.picapays.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) {
        Usuario usuario = usuarioService.consultarEntidade(login);

        if (!validarUsuario(usuario)) {
            throw new UsernameNotFoundException(MensagemValidacao.ERRO_USUARIO_SEM_PERMISSAO);
        }
        return usuario;
    }

    private boolean validarUsuario(Usuario usuario) {
        boolean usuarioValidado = false;

        if (usuario != null && usuario.getPermissao() != null && usuario.getAtivo()) {
            usuarioValidado = true;
        }

        return usuarioValidado;
    }
}

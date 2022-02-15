package br.com.dio.picapays.services.impl;

import br.com.dio.picapays.conversor.UsuarioConversor;
import br.com.dio.picapays.entities.Transacao;
import br.com.dio.picapays.entities.Usuario;
import br.com.dio.picapays.exceptions.NegocioException;
import br.com.dio.picapays.dto.UsuarioDTO;
import br.com.dio.picapays.repositories.UsuarioRepository;
import br.com.dio.picapays.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioConversor usuarioConversor;


    @Override
    public Usuario consultarEntidade(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public void validar(Usuario... usuarios) {
        Arrays.asList(usuarios).stream().forEach(usuario -> {
            if (usuario == null) {
                throw new NegocioException("Usuario informado não existe!");
            }
        });
    }

    @Override
    @Async("asyncExecutor")
    public void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito) {
        decrementarSaldo(transacao, isCartaoCredito);
        incrementarSaldo(transacao);

    }


    private void incrementarSaldo(Transacao transacao) {
        usuarioRepository.updateIncrementarSaldo(transacao.getDestino().getLogin(), transacao.getValor());
    }

    private void decrementarSaldo(Transacao transacao, Boolean isCartaoCredito) {
        if (!isCartaoCredito) {
            usuarioRepository.updateDecrementarSaldo(transacao.getOrigem().getLogin(), transacao.getValor());
        }
    }

    @Override
    public UsuarioDTO consultar(String login) {
        Usuario usuario = consultarEntidade(login);
        return usuarioConversor.converterEntidadeParaDto(usuario);
    }

    @Override
    public List<UsuarioDTO> listar(String login) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Usuario> usuariosFiltrados = usuarios.stream().filter(v-> !v.getLogin().equals(login)).collect(Collectors.toList());
        return usuarioConversor.converterEntidadesParaDtos(usuariosFiltrados);
    }


}

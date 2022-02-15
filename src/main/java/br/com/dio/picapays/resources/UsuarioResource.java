package br.com.dio.picapays.resources;

import br.com.dio.picapays.dto.UsuarioDTO;
import br.com.dio.picapays.resources.swagger.IUsuarioResource;
import br.com.dio.picapays.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource extends ResourceBase<UsuarioDTO> implements IUsuarioResource {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/{login}")
    public ResponseEntity<UsuarioDTO> consultar(@PathVariable String login) {
        UsuarioDTO usuario = usuarioService.consultar(login);
        return responderSucessoComItem(usuario);
    }


    @GetMapping("/contatos")
    public ResponseEntity<List<UsuarioDTO>> listar(@RequestParam String login) {
        List<UsuarioDTO> usuarios = usuarioService.listar(login);
        return responderListaDeItens(usuarios);
    }


    @GetMapping("/{login}/saldo")
    public ResponseEntity<UsuarioDTO> consultarSaldo(@PathVariable String login) {
        UsuarioDTO usuario = usuarioService.consultar(login);
        return responderSucessoComItem(usuario);
    }


}

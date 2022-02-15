package br.com.dio.picapays.config;

import br.com.dio.picapays.entities.Usuario;
import br.com.dio.picapays.repositories.UsuarioRepository;
import br.com.dio.picapays.services.impl.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoTokenFilter  extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository repository;

    public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        Boolean tokenValido = tokenService.isTokenValido(token);

        if (tokenValido){
            autenticarUsuario(token);
        }

        // Vai continuar, o spring se vira
        filterChain.doFilter(request, response);



    }

    private void autenticarUsuario(String token) {

        Long idUsuario = tokenService.getIdUsuario(token);
        // Buscando o id do Usuario
        Usuario usuario = repository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null; // não token valido
        }
        // Retirando o Bearer
        return token.substring(7, token.length());
    }






}

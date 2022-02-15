package br.com.dio.picapays.services.impl;

import br.com.dio.picapays.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${picpayclone.jwt.expiration}")
    private String expiracao;

    @Value("${picpayclone.jwt.secretkey}")
    private String secret;

    // Geracao do Token
    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();

        // hoje + 24 horas
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiracao));

        return Jwts.builder()
                .setIssuer("API do PicPAy Clone")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();

    }

    // Validando o Token
    public Boolean isTokenValido(String token) {
        try {
            // Descriptografando o token
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // Recuperando o Id do Usuario
    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}

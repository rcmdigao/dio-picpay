package br.com.dio.picapays.resources;


import br.com.dio.picapays.dto.TransacaoDTO;
import br.com.dio.picapays.services.ITransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/transacoes")
public class TransacaoResource extends ResourceBase<TransacaoDTO> {

    @Autowired
    private ITransacaoService transacaoService;


    @PostMapping
    public ResponseEntity<TransacaoDTO> salvar(@RequestBody @Valid TransacaoDTO dto, UriComponentsBuilder uriBuilder) {
        TransacaoDTO transacaoDTORetorno = transacaoService.processar(dto);
        return responderItemCriadoComURI(transacaoDTORetorno, uriBuilder, "/transacoes/", transacaoDTORetorno.getCodigo());

    }


    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> listar(@PageableDefault(page = 0, size = 20) Pageable paginacao, @RequestParam String login) {
        Page<TransacaoDTO> transacoes = transacaoService.listar(paginacao, login);
        return responderListaDeItensPaginada(transacoes);
    }

}

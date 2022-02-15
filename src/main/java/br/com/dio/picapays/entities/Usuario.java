package br.com.dio.picapays.entities;

import br.com.dio.picapays.enums.TipoPermissao;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "USUARIOS")
public class Usuario extends EntidadeBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "USU_LOGIN", nullable = false)
    private String login;

    @Column(name = "USU_SENHA", nullable = false)
    private String senha;

    @Column(name = "USU_EMAIL", nullable = false)
    private String email;

    @Column(name = "USU_NOME_COMPLETO", nullable = false)
    private String nomeCompleto;

    @Column(name = "USU_CPF", nullable = false)
    private String cpf;

    @Column(name = "USU_DATA_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "USU_NUMERO_TELEFONE", nullable = false)
    private String numeroTelefone;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<CartaoCredito> cartoesCredito;

    @Column(name = "USU_SALDO", nullable = false)
    private Double saldo;

    @Column(name = "USU_ATIVO", nullable = false)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "USU_PERMISSAO", nullable = false)
    private TipoPermissao permissao;
}

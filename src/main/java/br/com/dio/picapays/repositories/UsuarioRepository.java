package br.com.dio.picapays.repositories;

import br.com.dio.picapays.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

    @Modifying
    @Query("update Usuario u set u.saldo = u.saldo - ?2 where u.login = ?1")

    void updateDecrementarSaldo(String Login, Double valor);

    @Modifying
    @Query("update Usuario u set u.saldo = u.saldo + ?2 where u.login = ?1")
    void updateIncrementarSaldo(String Login, Double valor);

}

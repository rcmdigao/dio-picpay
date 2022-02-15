package br.com.dio.picapays.repositories;

import br.com.dio.picapays.entities.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
}

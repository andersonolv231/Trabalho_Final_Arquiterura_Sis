package br.edu.unifor.missaomarte.servidor.jogador;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    boolean existsByUsuarioIgnoreCase(String usuario);

    Optional<Jogador> findByUsuarioIgnoreCase(String usuario);
}

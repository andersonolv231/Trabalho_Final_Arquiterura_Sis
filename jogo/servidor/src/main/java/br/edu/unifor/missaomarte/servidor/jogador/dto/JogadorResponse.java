package br.edu.unifor.missaomarte.servidor.jogador.dto;

import br.edu.unifor.missaomarte.servidor.jogador.Jogador;
import java.time.Instant;

public record JogadorResponse(Long id, String usuario, Instant criadoEm) {

    public static JogadorResponse de(Jogador jogador) {
        return new JogadorResponse(jogador.getId(), jogador.getUsuario(), jogador.getCriadoEm());
    }
}

package br.edu.unifor.missaomarte;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import br.edu.unifor.missaomarte.model.Astronauta;
import br.edu.unifor.missaomarte.model.Dificuldade;
import br.edu.unifor.missaomarte.model.Engenheiro;
import br.edu.unifor.missaomarte.model.Mapa;
import br.edu.unifor.missaomarte.model.Missao;
import br.edu.unifor.missaomarte.model.Nave;
import br.edu.unifor.missaomarte.model.Professor;
import br.edu.unifor.missaomarte.service.Comando;
import br.edu.unifor.missaomarte.service.FabricaPassageiro;
import br.edu.unifor.missaomarte.service.Partida;

/** Montagem de cenários controlados para os testes. */
final class Cenarios {
    static final Clock RELOGIO_FIXO = Clock.fixed(Instant.parse("2026-09-22T13:00:00Z"), ZoneId.of("America/Fortaleza"));

    static final List<FabricaPassageiro> TIPOS_PADRAO = List.of(
            (x, y) -> new Professor("Dr. Silva", x, y),
            (x, y) -> new Engenheiro("Eng. Rosa", x, y),
            (x, y) -> new Professor("Dr. Lima", x, y),
            (x, y) -> new Engenheiro("Eng. Carlos", x, y),
            (x, y) -> new Astronauta("Ast. Maria", x, y));

    private Cenarios() {
    }

    static Missao missaoVazia(int tamanho) {
        return new Missao(new Mapa(tamanho), new Nave("T-1", 0, 0));
    }

    static Partida partida(Dificuldade dificuldade, Missao missao) {
        return new Partida("Teste", dificuldade, missao, new Random(1), RELOGIO_FIXO);
    }

    /** Partida vencida: um astronauta em (1,0); direita, embarca, volta. */
    static Partida partidaVencida(Dificuldade dificuldade) {
        Missao missao = missaoVazia(2);
        missao.adicionarPassageiro(new Astronauta("Ast", 1, 0));
        Partida partida = partida(dificuldade, missao);
        partida.executar(Comando.DIREITA);
        partida.executar(Comando.EMBARCAR);
        partida.executar(Comando.ESQUERDA);
        return partida;
    }
}

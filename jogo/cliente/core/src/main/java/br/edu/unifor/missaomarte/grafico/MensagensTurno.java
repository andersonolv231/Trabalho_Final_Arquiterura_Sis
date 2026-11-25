package br.edu.unifor.missaomarte.grafico;

import br.edu.unifor.missaomarte.service.EventoTurno;
import br.edu.unifor.missaomarte.service.ResultadoTurno;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class MensagensTurno {
    private static final Map<EventoTurno, String> TEXTOS = new EnumMap<>(EventoTurno.class);

    static {
        TEXTOS.put(EventoTurno.PASSAGEIRO_EMBARCADO, "Passageiro embarcado!");
        TEXTOS.put(EventoTurno.SEM_PASSAGEIRO, "Nao ha passageiro aqui.");
        TEXTOS.put(EventoTurno.NAVE_CHEIA, "Nave cheia.");
        TEXTOS.put(EventoTurno.COLISAO, "Colisao! Voce perdeu uma vida.");
        TEXTOS.put(EventoTurno.NAVE_DESTRUIDA, "Nave destruida. Fim de jogo.");
        TEXTOS.put(EventoTurno.SEM_COMBUSTIVEL, "Sem combustivel. Fim de jogo.");
        TEXTOS.put(EventoTurno.TODOS_RESGATADOS, "Todos resgatados! Volte para a plataforma L.");
        TEXTOS.put(EventoTurno.MISSAO_CUMPRIDA, "Missao cumprida!");
        TEXTOS.put(EventoTurno.MISSAO_ABORTADA, "Missao abortada.");
    }

    private MensagensTurno() {
    }

    public static String descrever(ResultadoTurno resultado) {
        return resultado.getEventos().stream()
                .map(evento -> TEXTOS.getOrDefault(evento, evento.name()))
                .collect(Collectors.joining(" "));
    }
}

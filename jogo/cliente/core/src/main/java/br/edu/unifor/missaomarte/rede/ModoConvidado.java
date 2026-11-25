package br.edu.unifor.missaomarte.rede;

import br.edu.unifor.missaomarte.service.Partida;
import java.util.List;

public class ModoConvidado implements ClienteRanking {
    private static final String MOTIVO = "modo convidado (sem servidor)";

    @Override
    public boolean autenticado() {
        return false;
    }

    @Override
    public void cadastrar(String usuario, String senha, Resposta<Void> resposta) {
        resposta.falha(MOTIVO);
    }

    @Override
    public void entrar(String usuario, String senha, Resposta<Void> resposta) {
        resposta.falha(MOTIVO);
    }

    @Override
    public void enviarPartida(Partida partida, Resposta<Void> resposta) {
        resposta.falha(MOTIVO);
    }

    @Override
    public void buscarRankingGlobal(Resposta<List<ItemRankingGlobal>> resposta) {
        resposta.falha(MOTIVO);
    }
}

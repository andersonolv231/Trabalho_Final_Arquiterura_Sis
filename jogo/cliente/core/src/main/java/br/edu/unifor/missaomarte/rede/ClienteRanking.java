package br.edu.unifor.missaomarte.rede;

import br.edu.unifor.missaomarte.service.Partida;
import java.util.List;

public interface ClienteRanking {
    boolean autenticado();

    void cadastrar(String usuario, String senha, Resposta<Void> resposta);

    void entrar(String usuario, String senha, Resposta<Void> resposta);

    void enviarPartida(Partida partida, Resposta<Void> resposta);

    void buscarRankingGlobal(Resposta<List<ItemRankingGlobal>> resposta);
}

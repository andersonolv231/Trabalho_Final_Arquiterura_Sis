package br.edu.unifor.missaomarte;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import br.edu.unifor.missaomarte.model.Astronauta;
import br.edu.unifor.missaomarte.model.Engenheiro;
import br.edu.unifor.missaomarte.model.Professor;
import br.edu.unifor.missaomarte.presentation.JogoConsole;
import br.edu.unifor.missaomarte.presentation.MapaRenderer;
import br.edu.unifor.missaomarte.presentation.PainelInformacoes;
import br.edu.unifor.missaomarte.presentation.Terminal;
import br.edu.unifor.missaomarte.repository.JsonRankingRepository;
import br.edu.unifor.missaomarte.repository.RankingRepository;
import br.edu.unifor.missaomarte.service.FabricaPassageiro;
import br.edu.unifor.missaomarte.service.GeradorMissao;
import br.edu.unifor.missaomarte.service.JogoService;

/**
 * Ponto de entrada e raiz de composição.
 *
 * <p>SRP: único lugar que conhece as classes concretas e as conecta.
 * DIP: é aqui, e só aqui, que se escolhe a implementação de
 * {@link RankingRepository}.</p>
 *
 * <p>Uso: {@code java -cp out br.edu.unifor.missaomarte.Main [--seed N]}. A semente
 * torna o mapa e o movimento dos inimigos reproduzíveis (útil em testes e na
 * apresentação).</p>
 */
public class Main {
    private static final String ARQUIVO_RANKING = "ranking-solid-exercicio10.json";

    public static void main(String[] args) {
        Random random = criarRandom(args);

        RankingRepository ranking = new JsonRankingRepository(Path.of(ARQUIVO_RANKING));

        // OCP: um novo tipo de passageiro entra na rotação adicionando uma linha aqui.
        List<FabricaPassageiro> tiposPassageiro = List.of(
                (x, y) -> new Professor("Dr. Silva", x, y),
                (x, y) -> new Engenheiro("Eng. Rosa", x, y),
                (x, y) -> new Professor("Dr. Lima", x, y),
                (x, y) -> new Engenheiro("Eng. Carlos", x, y),
                (x, y) -> new Astronauta("Ast. Maria", x, y));

        JogoService jogoService = new JogoService(ranking, new GeradorMissao(tiposPassageiro, random), random);

        Scanner scanner = new Scanner(System.in);
        new JogoConsole(jogoService,
                new Terminal(scanner, System.out),
                new MapaRenderer(System.out),
                new PainelInformacoes(System.out)).executar();
        scanner.close();
    }

    private static Random criarRandom(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--seed")) {
                try {
                    return new Random(Long.parseLong(args[i + 1]));
                } catch (NumberFormatException e) {
                    System.out.println("Semente inválida, usando aleatória.");
                }
            }
        }
        return new Random();
    }
}

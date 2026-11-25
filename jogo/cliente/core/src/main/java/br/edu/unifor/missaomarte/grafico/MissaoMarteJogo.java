package br.edu.unifor.missaomarte.grafico;

import br.edu.unifor.missaomarte.model.Astronauta;
import br.edu.unifor.missaomarte.model.Engenheiro;
import br.edu.unifor.missaomarte.model.Professor;
import br.edu.unifor.missaomarte.rede.ClienteRanking;
import br.edu.unifor.missaomarte.rede.ModoConvidado;
import br.edu.unifor.missaomarte.repository.JsonRankingRepository;
import br.edu.unifor.missaomarte.repository.RankingRepository;
import br.edu.unifor.missaomarte.service.FabricaPassageiro;
import br.edu.unifor.missaomarte.service.GeradorMissao;
import br.edu.unifor.missaomarte.service.JogoService;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;
import java.util.Random;

public class MissaoMarteJogo extends Game {
    public static final String ARQUIVO_RANKING = "ranking-missao-marte.json";

    private final ClienteRanking clienteRanking;
    private SpriteBatch batch;
    private ShapeRenderer formas;
    private BitmapFont fonte;
    private JogoService jogoService;

    public MissaoMarteJogo() {
        this(new ModoConvidado());
    }

    public MissaoMarteJogo(ClienteRanking clienteRanking) {
        this.clienteRanking = clienteRanking;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        formas = new ShapeRenderer();
        fonte = new BitmapFont();
        fonte.getData().setScale(1.4f);

        Random random = criarRandom();
        RankingRepository ranking = new JsonRankingRepository(Gdx.files.local(ARQUIVO_RANKING).file().toPath());
        jogoService = new JogoService(ranking, new GeradorMissao(tiposPassageiro(), random), random);

        setScreen(new TelaMenu(this));
    }

    public void trocarTela(Screen novaTela) {
        Screen anterior = getScreen();
        setScreen(novaTela);
        if (anterior != null) {
            anterior.dispose();
        }
    }

    @Override
    public void dispose() {
        Screen atual = getScreen();
        super.dispose();
        if (atual != null) {
            atual.dispose();
        }
        batch.dispose();
        formas.dispose();
        fonte.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getFormas() {
        return formas;
    }

    public BitmapFont getFonte() {
        return fonte;
    }

    public JogoService getJogoService() {
        return jogoService;
    }

    public ClienteRanking getClienteRanking() {
        return clienteRanking;
    }

    private static List<FabricaPassageiro> tiposPassageiro() {
        return List.of(
                (x, y) -> new Professor("Dr. Silva", x, y),
                (x, y) -> new Engenheiro("Eng. Rosa", x, y),
                (x, y) -> new Professor("Dr. Lima", x, y),
                (x, y) -> new Engenheiro("Eng. Carlos", x, y),
                (x, y) -> new Astronauta("Ast. Maria", x, y));
    }

    private static Random criarRandom() {
        String semente = System.getProperty("missaomarte.semente");
        if (semente != null) {
            try {
                return new Random(Long.parseLong(semente));
            } catch (NumberFormatException ignorada) {
                Gdx.app.log("MissaoMarte", "Semente invalida: " + semente);
            }
        }
        return new Random();
    }
}

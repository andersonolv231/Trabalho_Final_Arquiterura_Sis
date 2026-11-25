package br.edu.unifor.missaomarte.grafico;

import br.edu.unifor.missaomarte.model.EntidadeMapa;
import br.edu.unifor.missaomarte.model.Mapa;
import br.edu.unifor.missaomarte.model.Missao;
import br.edu.unifor.missaomarte.service.Comando;
import br.edu.unifor.missaomarte.service.EstadoPartida;
import br.edu.unifor.missaomarte.service.Partida;
import br.edu.unifor.missaomarte.service.ResultadoTurno;
import br.edu.unifor.missaomarte.rede.Resposta;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class TelaPartida extends ScreenAdapter {
    private static final float ALTURA_HUD = 130f;
    private static final float MARGEM = 20f;
    private static final float LARGURA_LEGENDA = 240f;
    private static final String CONTROLES = "WASD/setas movem, C embarca, Q aborta";
    private static final Map<Character, Color> CORES = Map.of(
            '@', Color.CYAN,
            'P', Color.GREEN,
            'E', Color.LIME,
            'T', Color.GOLD,
            '#', Color.GRAY,
            'X', Color.RED,
            'L', Color.ROYAL);
    private static final Map<Character, String> LEGENDA = legenda();

    private final MissaoMarteJogo jogo;
    private final Partida partida;
    private final ScreenViewport viewport = new ScreenViewport();
    private String mensagem = CONTROLES;
    private boolean resultadoRegistrado;

    public TelaPartida(MissaoMarteJogo jogo, Partida partida) {
        this.jogo = jogo;
        this.partida = partida;
    }

    @Override
    public void render(float delta) {
        processarEntrada();
        ScreenUtils.clear(0.02f, 0.02f, 0.06f, 1f);
        viewport.apply();
        desenharMapa();
        desenharLegenda();
        desenharTextos();
    }

    private void processarEntrada() {
        if (!partida.emAndamento()) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER) || Gdx.input.justTouched()) {
                jogo.trocarTela(new TelaMenu(jogo));
            }
            return;
        }
        Comando comando = MapeamentoTeclas.comandoPressionado(Gdx.input);
        if (comando == null) {
            return;
        }
        ResultadoTurno resultado = partida.executar(comando);
        String descricao = MensagensTurno.descrever(resultado);
        mensagem = descricao.isEmpty() ? CONTROLES : descricao;
        if (!partida.emAndamento()) {
            finalizar();
        }
    }

    private void finalizar() {
        if (resultadoRegistrado) {
            return;
        }
        resultadoRegistrado = true;
        boolean entrouNoRanking = jogo.getJogoService().registrarResultado(partida);
        if (entrouNoRanking) {
            mensagem += " Voce entrou no Top 5!";
        }
        if (partida.getEstado() == EstadoPartida.VITORIA) {
            jogo.getClienteRanking().enviarPartida(partida, new AvisoEnvio());
        }
    }

    private float tamanhoCelula(Mapa mapa) {
        int lado = mapa.getMaxX() - mapa.getMinX() + 1;
        float largura = viewport.getWorldWidth() - LARGURA_LEGENDA - 2 * MARGEM;
        float altura = viewport.getWorldHeight() - ALTURA_HUD - 2 * MARGEM;
        return Math.max(4f, Math.min(largura, altura) / lado);
    }

    private float origemX(Mapa mapa, float celula) {
        int lado = mapa.getMaxX() - mapa.getMinX() + 1;
        return LARGURA_LEGENDA + (viewport.getWorldWidth() - LARGURA_LEGENDA - lado * celula) / 2f;
    }

    private float telaX(Mapa mapa, int x, float celula) {
        return origemX(mapa, celula) + (x - mapa.getMinX()) * celula;
    }

    private float telaY(Mapa mapa, int y, float celula) {
        return MARGEM + (mapa.getMaxY() - y) * celula;
    }

    private void desenharMapa() {
        Missao missao = partida.getMissao();
        Mapa mapa = missao.getMapa();
        float celula = tamanhoCelula(mapa);
        ShapeRenderer formas = jogo.getFormas();
        formas.setProjectionMatrix(viewport.getCamera().combined);

        formas.begin(ShapeType.Filled);
        for (int y = mapa.getMinY(); y <= mapa.getMaxY(); y++) {
            for (int x = mapa.getMinX(); x <= mapa.getMaxX(); x++) {
                float px = telaX(mapa, x, celula);
                float py = telaY(mapa, y, celula);
                formas.setColor(0.1f, 0.1f, 0.18f, 1f);
                formas.rect(px + 1, py + 1, celula - 2, celula - 2);
                Optional<EntidadeMapa> entidade = missao.entidadeEm(x, y);
                if (entidade.isPresent()) {
                    formas.setColor(CORES.getOrDefault(entidade.get().getSimbolo(), Color.WHITE));
                    formas.circle(px + celula / 2f, py + celula / 2f, celula * 0.35f);
                }
            }
        }
        formas.end();

        SpriteBatch batch = jogo.getBatch();
        BitmapFont fonte = jogo.getFonte();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        fonte.setColor(Color.BLACK);
        for (int y = mapa.getMinY(); y <= mapa.getMaxY(); y++) {
            for (int x = mapa.getMinX(); x <= mapa.getMaxX(); x++) {
                Optional<EntidadeMapa> entidade = missao.entidadeEm(x, y);
                if (entidade.isPresent()) {
                    fonte.draw(batch, String.valueOf(entidade.get().getSimbolo()),
                            telaX(mapa, x, celula) + celula / 2f - 6f,
                            telaY(mapa, y, celula) + celula / 2f + 8f);
                }
            }
        }
        fonte.setColor(Color.WHITE);
        batch.end();
    }

    private void desenharLegenda() {
        float raio = 12f;
        float espaco = 34f;
        float topo = viewport.getWorldHeight() - ALTURA_HUD - 40f;
        ShapeRenderer formas = jogo.getFormas();
        formas.begin(ShapeType.Filled);
        int linha = 0;
        for (Character simbolo : LEGENDA.keySet()) {
            formas.setColor(CORES.getOrDefault(simbolo, Color.WHITE));
            formas.circle(MARGEM + raio, topo - linha * espaco - raio, raio);
            linha++;
        }
        formas.end();

        SpriteBatch batch = jogo.getBatch();
        BitmapFont fonte = jogo.getFonte();
        batch.begin();
        fonte.draw(batch, "Legenda", MARGEM, topo + 28);
        linha = 0;
        for (Map.Entry<Character, String> item : LEGENDA.entrySet()) {
            fonte.draw(batch, item.getKey() + "  " + item.getValue(), MARGEM + 2 * raio + 10,
                    topo - linha * espaco - 2);
            linha++;
        }
        batch.end();
    }

    private static Map<Character, String> legenda() {
        Map<Character, String> itens = new LinkedHashMap<>();
        itens.put('@', "Nave (voce)");
        itens.put('P', "Professor +10");
        itens.put('E', "Engenheiro +15");
        itens.put('T', "Astronauta +20");
        itens.put('#', "Asteroide");
        itens.put('X', "Inimigo");
        itens.put('L', "Plataforma");
        return itens;
    }

    private void desenharTextos() {
        SpriteBatch batch = jogo.getBatch();
        BitmapFont fonte = jogo.getFonte();
        float topo = viewport.getWorldHeight() - MARGEM;
        batch.begin();
        fonte.draw(batch, String.format("Piloto: %s   Dificuldade: %s   Pontos: %d   Vidas: %d   A bordo: %d/%d",
                partida.getPiloto(), partida.getDificuldade().name(), partida.getPontuacao(),
                partida.getMissao().getNave().getVidas(), partida.getPassageirosResgatados(),
                partida.getMissao().getNave().getCapacidade()), MARGEM, topo);
        fonte.draw(batch, mensagem, MARGEM, topo - 32);
        if (!partida.emAndamento()) {
            fonte.draw(batch, "Resultado: " + partida.getEstado().name() + "  -  ENTER ou toque para voltar ao menu",
                    MARGEM, topo - 64);
        }
        batch.end();
    }

    @Override
    public void resize(int largura, int altura) {
        viewport.update(largura, altura, true);
    }

    private static final class AvisoEnvio implements Resposta<Void> {
        @Override
        public void sucesso(Void valor) {
            Gdx.app.log("MissaoMarte", "Partida enviada ao ranking global.");
        }

        @Override
        public void falha(String motivo) {
            Gdx.app.log("MissaoMarte", "Ranking global indisponivel: " + motivo);
        }
    }
}

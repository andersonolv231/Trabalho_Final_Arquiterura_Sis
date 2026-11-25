package br.edu.unifor.missaomarte.grafico;

import br.edu.unifor.missaomarte.model.Dificuldade;
import br.edu.unifor.missaomarte.repository.RankingEntry;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.List;

public class TelaMenu extends ScreenAdapter {
    private static final int TAMANHO_MAPA_PADRAO = 4;
    private static final String PILOTO_PADRAO = "Piloto";

    private final MissaoMarteJogo jogo;
    private final ScreenViewport viewport = new ScreenViewport();
    private List<RankingEntry> ranking;

    public TelaMenu(MissaoMarteJogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        ranking = jogo.getJogoService().listarRanking();
    }

    @Override
    public void render(float delta) {
        Dificuldade escolhida = dificuldadeEscolhida();
        if (escolhida != null) {
            iniciar(escolhida);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            Gdx.app.exit();
            return;
        }

        ScreenUtils.clear(0.05f, 0.05f, 0.12f, 1f);
        viewport.apply();
        SpriteBatch batch = jogo.getBatch();
        BitmapFont fonte = jogo.getFonte();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float x = 40;
        float y = viewport.getWorldHeight() - 40;
        fonte.draw(batch, "MISSAO MARTE UNIFOR", x, y);
        fonte.draw(batch, "1 - Facil    2 - Medio    3 - Dificil    ESC - Sair", x, y - 50);
        fonte.draw(batch, "Toque na tela para jogar no Medio", x, y - 80);
        fonte.draw(batch, "Ranking local (Top 5):", x, y - 140);
        float linha = y - 170;
        if (ranking.isEmpty()) {
            fonte.draw(batch, "Nenhuma vitoria registrada ainda.", x, linha);
        }
        for (RankingEntry entrada : ranking) {
            fonte.draw(batch, entrada.getNomePiloto() + "  " + entrada.getPontuacao() + " pts  "
                    + entrada.getDificuldade().name(), x, linha);
            linha -= 28;
        }
        batch.end();
    }

    private Dificuldade dificuldadeEscolhida() {
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_1)) {
            return Dificuldade.FACIL;
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_2)
                || Gdx.input.justTouched()) {
            return Dificuldade.MEDIO;
        }
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_3)) {
            return Dificuldade.DIFICIL;
        }
        return null;
    }

    private void iniciar(Dificuldade dificuldade) {
        jogo.trocarTela(new TelaPartida(jogo,
                jogo.getJogoService().iniciarPartida(PILOTO_PADRAO, dificuldade, TAMANHO_MAPA_PADRAO)));
    }

    @Override
    public void resize(int largura, int altura) {
        viewport.update(largura, altura, true);
    }
}

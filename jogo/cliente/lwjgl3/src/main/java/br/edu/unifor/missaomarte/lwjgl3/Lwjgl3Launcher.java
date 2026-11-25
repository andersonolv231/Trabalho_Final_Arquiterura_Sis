package br.edu.unifor.missaomarte.lwjgl3;

import br.edu.unifor.missaomarte.grafico.MissaoMarteJogo;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class Lwjgl3Launcher {

    private Lwjgl3Launcher() {
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration configuracao = new Lwjgl3ApplicationConfiguration();
        configuracao.setTitle("Missao Marte Unifor");
        configuracao.setWindowedMode(960, 720);
        configuracao.useVsync(true);
        configuracao.setForegroundFPS(60);
        new Lwjgl3Application(new MissaoMarteJogo(), configuracao);
    }
}

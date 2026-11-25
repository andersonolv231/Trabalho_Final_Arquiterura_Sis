package br.edu.unifor.missaomarte.grafico;

import br.edu.unifor.missaomarte.service.Comando;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public final class MapeamentoTeclas {

    private MapeamentoTeclas() {
    }

    public static Comando comandoPressionado(Input entrada) {
        if (entrada.isKeyJustPressed(Keys.W) || entrada.isKeyJustPressed(Keys.UP)) {
            return Comando.CIMA;
        }
        if (entrada.isKeyJustPressed(Keys.S) || entrada.isKeyJustPressed(Keys.DOWN)) {
            return Comando.BAIXO;
        }
        if (entrada.isKeyJustPressed(Keys.A) || entrada.isKeyJustPressed(Keys.LEFT)) {
            return Comando.ESQUERDA;
        }
        if (entrada.isKeyJustPressed(Keys.D) || entrada.isKeyJustPressed(Keys.RIGHT)) {
            return Comando.DIREITA;
        }
        if (entrada.isKeyJustPressed(Keys.C) || entrada.isKeyJustPressed(Keys.SPACE)) {
            return Comando.EMBARCAR;
        }
        if (entrada.isKeyJustPressed(Keys.Q)) {
            return Comando.ABORTAR;
        }
        return null;
    }
}

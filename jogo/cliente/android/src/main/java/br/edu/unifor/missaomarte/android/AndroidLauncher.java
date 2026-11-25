package br.edu.unifor.missaomarte.android;

import android.os.Bundle;
import br.edu.unifor.missaomarte.grafico.MissaoMarteJogo;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuracao = new AndroidApplicationConfiguration();
        configuracao.useImmersiveMode = true;
        initialize(new MissaoMarteJogo(), configuracao);
    }
}

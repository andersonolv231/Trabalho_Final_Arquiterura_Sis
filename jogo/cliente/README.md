# Cliente — jogo gráfico (libGDX)

| Módulo | Conteúdo |
|---|---|
| `dominio` | regras do jogo, as mesmas da Fase 2 (sem libGDX) |
| `core` | `grafico` (telas, HUD, entrada) e `rede` (cliente do servidor) |
| `lwjgl3` | launcher desktop |
| `android` | launcher Android (incluído só quando há Android SDK) |

```powershell
.\gradlew.bat test
.\gradlew.bat :lwjgl3:run
.\gradlew.bat :lwjgl3:run -Dmissaomarte.semente=5
.\gradlew.bat :lwjgl3:run -Dmissaomarte.servidor=http://localhost:8080
.\gradlew.bat :android:assembleDebug
```

Controles: as teclas 1, 2 e 3 escolhem a dificuldade no menu; WASD ou as setas movem; C ou espaço embarca; Q aborta; ENTER volta ao menu no fim da partida.

Para gerar o APK, informe o SDK em `local.properties` (`sdk.dir=C\:\\Users\\<voce>\\AppData\\Local\\Android\\Sdk`) ou na variável `ANDROID_HOME`. O `local.properties` não vai para o Git.

O ranking local fica em `assets/ranking-missao-marte.json` no desktop, porque é a pasta de trabalho do `run`. Esse arquivo é ignorado pelo Git.

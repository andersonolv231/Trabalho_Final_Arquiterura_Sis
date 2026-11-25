# Regras do projeto para assistentes de IA

Projeto integrador da UNIFOR: o jogo Missão Marte, que evolui do console para libGDX (desktop e Android), com servidor Spring Boot. Leia `docs/GUIA-DA-EQUIPE.md` e `docs/ARQUITETURA.md` antes de alterar qualquer coisa.

## Estrutura
- `fases/fase-0-original`, `fases/fase-01-refatoracao`, `fases/fase-02-gradle`: **congeladas. Não altere.**
- `jogo/cliente`: Gradle multi-módulo (`dominio`, `core`, `lwjgl3`, `android`).
- `jogo/servidor`: Spring Boot independente.

## Regras de arquitetura (obrigatórias)
1. Regras do jogo (pontuação, colisão, vitória, ranking) ficam **só** em `jogo/cliente/dominio`. Telas e controllers não decidem regra.
2. `dominio` não pode importar `com.badlogic.*`, Android nem Spring. O build e o teste `DominioSemLibgdxTest` quebram se isso acontecer.
3. Telas (`core/.../grafico`) só traduzem entrada em `Comando`, chamam o domínio e desenham o resultado.
4. Comunicação com o servidor só por `ClienteRanking` (`core/.../rede`), de forma assíncrona (`Gdx.net`) e com o retorno à tela via `Gdx.app.postRunnable`. Nunca bloqueie a thread de renderização.
5. Todo recurso libGDX (`Texture`, `Sound`, `Music`, `BitmapFont`, `SpriteBatch`) precisa ser liberado em `dispose()`.
6. O servidor não depende do código do cliente. Nunca exponha entidade JPA na API: use *records* DTO com Bean Validation.
7. Senhas: sempre com hash (`PasswordEncoder`), nunca em log, resposta ou arquivo versionado.
8. Erros do servidor saem sempre no formato `ErroResposta` (ver `TratadorDeErros`).
9. Nada de senha, token ou chave no código, nos arquivos de configuração versionados ou nos logs. Use variáveis de ambiente.
10. Não copie código do repositório do professor (`marcelobezerra-dotcom/T200-ProjArqSist`, pasta `projetoExtensaoFinal`). Use só como referência de estudo.

## Convenções
- Java 17 (`options.release = 17`), pacote base `br.edu.unifor.missaomarte` (servidor: `br.edu.unifor.missaomarte.servidor`).
- Nomes de classes, métodos e mensagens em português, seguindo o código existente.
- Toda mudança de regra vem com teste em `dominio/src/test/java`. Mudanças no servidor vêm com teste MockMvc.
- Commits pequenos, com mensagem descritiva e o número da issue (`#N`).
- Se mudar um comando, requisito ou endpoint, atualize o README do projeto afetado e o `docs/API.md`.

## Comandos
```bash
cd jogo/cliente && ./gradlew test && ./gradlew :lwjgl3:run
cd jogo/servidor && ./gradlew test && ./gradlew bootRun
cd fases/fase-02-gradle && ./gradlew test
```

## Ao terminar uma tarefa
Rode os testes do projeto que você alterou e descreva o que mudou, como testar e o que não deu para verificar.

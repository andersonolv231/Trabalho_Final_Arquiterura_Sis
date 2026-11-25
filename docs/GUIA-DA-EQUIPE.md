# Guia da equipe

Este guia explica, para quem não montou a base, **de onde partimos, o que já foi feito, como foi feito e o que falta**. Leia antes de começar a sua parte.

## 1. Situação do projeto

O enunciado previa cinco sprints a partir de 03/11. Por causa do calendário da disciplina, a equipe começou o projeto integrador em **25/11**, já na Sprint 4. Por isso, a base foi montada de uma vez, aproveitando o trabalho que a equipe já tinha feito antes:

- **Fase 0:** o código original do Exercício 10 (o jogo de console do professor), preservado sem nenhuma alteração.
- **Fase 1:** a **refatoração SOLID que o Igor fez** na atividade anterior (`missao-marte-solid-igor`). Ela já separa domínio, serviço, persistência e apresentação, e tem 39 testes automatizados. É código da própria equipe. **O código do repositório do professor (`projetoExtensaoFinal`) não foi copiado**, como o enunciado exige.

A partir daí, foram montadas a Fase 2 (Gradle), uma primeira versão jogável do cliente gráfico, o esqueleto do Android e um servidor com o primeiro endpoint completo. Esse primeiro endpoint serve de exemplo para os outros.

## 2. O que já está pronto (e como foi feito)

### Fase 0 — `fases/fase-0-original`
É uma cópia exata de `src/exercicio10` da versão do Igor, que por sua vez é o original do professor. Serve para comparar com as outras fases e para mostrar ao professor que a versão inicial foi preservada.

### Fase 1 — `fases/fase-01-refatoracao`
A refatoração do Igor, copiada sem alterações: `src/solidexercicio10`, `test/`, a análise inicial, a revisão SOLID, os diagramas UML e as evidências de execução. O README original dele está em `README-SOLID-IGOR.md`. Compila com `javac` puro e roda os testes com `ExecutarTestes`, sem JUnit.

### Fase 2 — `fases/fase-02-gradle`
O mesmo código da Fase 1, com três mudanças:

1. **Layout do Gradle:** o código foi para `src/main/java` e os testes para `src/test/java`.
2. **Pacote renomeado** de `solidexercicio10` para `br.edu.unifor.missaomarte`, que é o nome que vamos usar até o fim.
3. **JUnit 5:** a classe `TodosOsTestesTest` usa `@TestFactory` para transformar os 39 testes do Igor em testes JUnit, sem reescrever nenhum. A classe `DominioIndependenteTest` foi adicionada e garante que `model` e `service` não usam console nem a camada de apresentação.

Também foram adicionados o `build.gradle`, com os plugins `java` e `application`, e o **Gradle Wrapper 8.7** (`gradlew`, `gradlew.bat`, `gradle/wrapper`), para ninguém precisar instalar o Gradle.

### Cliente — `jogo/cliente` (Fases 3, 4 e 6)
É um projeto Gradle com **quatro módulos**:

| Módulo | O que tem | Depende de |
|---|---|---|
| `dominio` | `model`, `service` e `repository`, copiados da Fase 2 (as regras do jogo) | **nada**, nem libGDX |
| `core` | telas libGDX (`grafico`) e integração com o servidor (`rede`) | `dominio` + libGDX |
| `lwjgl3` | `Lwjgl3Launcher`, que abre a janela no desktop | `core` |
| `android` | `AndroidLauncher` e `AndroidManifest.xml` | `core` |

O domínio ficou num módulo separado de propósito. Como o `dominio` **não tem a libGDX no classpath**, é impossível colocar uma regra de jogo dependente de tela sem o build quebrar. Isso responde direto a duas perguntas da apresentação: "onde ficam as regras de negócio?" e "como os testes demonstram que elas não dependem da interface?". O teste `DominioSemLibgdxTest` confere isso.

O que já funciona no `core`:

- `MissaoMarteJogo` (estende `Game`): cria e libera (`dispose`) os recursos compartilhados (`SpriteBatch`, `ShapeRenderer`, `BitmapFont`) e monta o `JogoService` com o ranking local em JSON. É a raiz de composição, igual ao `Main` da Fase 1.
- `TelaMenu`: as teclas 1, 2 e 3 escolhem a dificuldade (ou toque na tela para jogar no Médio), a tela mostra o ranking local e ESC sai do jogo.
- `TelaPartida`: desenha o mapa com formas coloridas (a cor vem do símbolo da entidade), mostra o HUD (pontos, vidas, passageiros) e as mensagens de cada turno. WASD ou as setas movem a nave, C embarca o passageiro e Q aborta a missão. Quando a partida termina, registra o resultado no ranking.
- `MapeamentoTeclas` traduz tecla em `Comando`, e `MensagensTurno` traduz `EventoTurno` em texto. **As telas não têm regra de jogo**: elas só chamam `partida.executar(comando)`.
- `rede`: a interface `ClienteRanking` (cadastrar, entrar, enviar partida, buscar ranking global), a implementação `ModoConvidado` (o jogo funciona sem servidor) e `ConfiguracaoServidor` (lê `-Dmissaomarte.servidor=...`, com padrão `http://localhost:8080`).

O que ainda não tem: sprites, áudio, tela de nome do piloto, fonte com acentos (por enquanto os textos da tela estão sem acento), controles por toque de verdade e o cliente HTTP real.

### Servidor — `jogo/servidor` (Fase 5)
Spring Boot 3.3 independente, que não depende de nada do cliente. Tem um **exemplo completo de ponta a ponta**, para copiar o padrão:

`POST /api/jogadores` passa por `JogadorController`, `JogadorService` e `JogadorRepository` (JPA) até a entidade `Jogador`, com:

- DTOs de entrada e saída (`CadastroJogadorRequest`, `JogadorResponse`), sem expor a entidade;
- validação com `@Valid`, `@NotBlank` e `@Size`;
- senha guardada como hash BCrypt (`SegurancaConfig`). O `toString` do DTO esconde a senha, para ela não aparecer em log;
- erro padronizado (`TratadorDeErros` monta um `ErroResposta` com status, código, mensagem e campos inválidos);
- `/actuator/health` como verificação de saúde;
- porta, endereço e pasta do banco configuráveis por variável de ambiente;
- testes com MockMvc (`JogadorControllerTest`).

Os endpoints que faltam estão descritos em [API.md](API.md).

## 3. Divisão sugerida das partes

A divisão é uma sugestão, feita para cada um mexer em pastas diferentes e ninguém travar ninguém. Troquem entre vocês se fizer mais sentido.

| Quem | Parte | Pastas principais |
|---|---|---|
| **Igor** | Cliente gráfico desktop (Fases 3 e 4): sprites, áudio, telas de piloto e ranking, fonte com acentos e a melhoria livre (Fase 7) | `jogo/cliente/core/.../grafico`, `jogo/cliente/assets`, `jogo/cliente/dominio` (só para a melhoria) |
| **Asafe** | Servidor (Fase 5): login com token, partidas, histórico, ranking global, testes, `DEPLOY.md` | `jogo/servidor` |
| **Anderson** | Integração e Android (Fases 5 e 6): `ClienteRankingHttp` com `Gdx.net`, telas de login e cadastro, controles por toque, endereço do servidor, APK, Kanban, README e relatório | `jogo/cliente/core/.../rede`, `jogo/cliente/android`, `docs/` |

Pontos de contato entre as partes:

- **Asafe e Anderson** combinam o contrato JSON da API em [API.md](API.md) **antes** de implementar. Se mudarem um campo, atualizem o arquivo.
- **Igor e Anderson** dividem o `core`: o Igor fica com `grafico` e o Anderson com `rede`. Se precisarem mexer no `MissaoMarteJogo`, avisem um ao outro.
- **Mudanças no `dominio`** afetam todo mundo. Rodem `gradlew test` e abram um pull request para alguém revisar.

## 4. Como trabalhar no Git

```bash
git clone git@github.com:andersonolv231/Trabalho_Final_Arquiterura_Sis.git
cd Trabalho_Final_Arquiterura_Sis

git checkout -b igor/sprites-nave
git add jogo/cliente/core/src/main/java/br/edu/unifor/missaomarte/grafico/TelaPartida.java
git commit -m "Desenha a nave com textura em vez de círculo (#5)"
git push -u origin igor/sprites-nave
```

Depois, abra um pull request no GitHub, peça revisão de outro integrante e só então faça o merge. Regras rápidas:

- **Uma branch por issue.** Escrevam o número da issue na mensagem do commit (`#5`), para o GitHub ligar o commit à issue.
- **Commits pequenos, cada um feito por quem fez o trabalho.** O professor tira ponto quando não dá para ver a contribuição de cada um no histórico.
- **Rodem os testes antes de pedir revisão:** `gradlew test` no projeto que vocês mexeram.
- **Nunca façam commit de senha, token ou chave.** Configurações locais vão em variável de ambiente ou no `local.properties`, que já está no `.gitignore`.

## 5. Kanban (GitHub Project)

O backlog está pronto em [`docs/kanban/backlog-missao-marte.json`](kanban/backlog-missao-marte.json) (versão legível em [`BACKLOG.md`](kanban/BACKLOG.md)). Para criar as issues, os milestones e o quadro de uma vez, dá para usar o importador que o professor disponibilizou (`tools/github-issues-importer` no repositório dele):

1. Crie o GitHub Project (Board) com as colunas **Backlog**, **Em andamento**, **Em revisão** e **Concluído**.
2. Rode o importador (`python server.py`), carregue o JSON e informe um token *fine-grained* seu, com permissão de Issues e Projects. **O token é digitado só na tela do importador. Nunca coloque o token em arquivo.**
3. Atribua cada issue ao responsável e mova os cartões **conforme o trabalho anda de verdade**. Uma issue só vai para Concluído depois que outra pessoa revisar.

## 6. Usando IA na sua parte

Cada um pode usar a IA que preferir. O arquivo [`CLAUDE.md`](../CLAUDE.md), na raiz, tem as regras de arquitetura do projeto. O Claude Code lê esse arquivo sozinho. Em outras ferramentas, colem o conteúdo dele no começo da conversa. Um bom começo de pedido:

> Leia o CLAUDE.md e o docs/GUIA-DA-EQUIPE.md. Eu sou o responsável pela issue #N (descrição). Trabalhe só em `<pasta>`. Antes de mudar código, me explique o plano. Depois rode `gradlew test`.

Revisem o que a IA gerou antes de fazer commit. Na apresentação, o professor vai perguntar como o código funciona.

## 7. Limitações conhecidas da base

- A base foi montada num ambiente sem acesso ao repositório Maven. O código do domínio, dos testes e das telas libGDX foi compilado e testado lá (41 testes na Fase 2 e 36 no módulo `dominio`), mas o **primeiro `gradlew build` completo**, que baixa o JUnit, a libGDX e o Spring Boot, precisa ser rodado numa máquina da equipe. Se algo falhar nessa primeira execução, é o primeiro item a corrigir.
- O servidor e o módulo `android` não puderam ser compilados no ambiente de montagem. O servidor segue o padrão do Spring Boot 3.3. O Android precisa do Android SDK para ser validado.
- No Android, o `JsonRankingRepository` usa `java.nio.file.Files.readString`, que não existe nas versões mais antigas do Android. Quem cuidar do Android pode precisar de um `RankingRepository` feito com `Gdx.files` (a interface já existe para isso).
- O servidor não executa o jogo, então não consegue verificar se uma pontuação enviada pelo cliente é legítima. Essa limitação precisa aparecer na documentação e no relatório.

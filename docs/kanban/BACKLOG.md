# Backlog — Missão Marte (Grupo 01)

Versão legível de `backlog-missao-marte.json`. O JSON pode ser importado com o importador do professor (veja o Guia da equipe, seção 5).

| ID | Sprint | Fase | Responsável | Issue |
|---|---|---|---|---|
| MM-01 | 4 | 1 | Anderson | [Base] Montar repositorio, estrutura de fases e guia da equipe |
| MM-02 | 4 | 1 | Igor | [Fase 1] Registrar a refatoracao SOLID como ponto de partida |
| MM-03 | 4 | 2 | Anderson | [Fase 2] Validar Gradle Wrapper e JUnit 5 em maquina limpa |
| MM-04 | 4 | 3 | Igor | [Fase 3] Validar cliente libGDX desktop e explicar o ciclo de vida |
| MM-05 | 4 | 4 | Igor | [Fase 4] Substituir formas por sprites e liberar recursos corretamente |
| MM-06 | 4 | 4 | Igor | [Fase 4] Adicionar audio de embarque, colisao, vitoria e derrota |
| MM-07 | 4 | 4 | Igor | [Fase 4] Telas de nome do piloto e ranking local e fonte com acentos |
| MM-08 | 4 | 5 | Asafe | [Fase 5] Login com token e protecao das rotas autenticadas |
| MM-09 | 4 | 5 | Asafe | [Fase 5] Registrar partidas e historico do jogador |
| MM-10 | 4 | 5 | Asafe | [Fase 5] Ranking global e documentacao da API |
| MM-11 | 4 | 5 | Anderson | [Fase 5] ClienteRankingHttp com Gdx.net e telas de cadastro e login |
| MM-12 | 4 | 6 | Anderson | [Fase 6] Validar modulo Android e gerar APK de debug |
| MM-13 | 4 | 6 | Anderson | [Fase 6] Controles por toque e endereco do servidor configuravel |
| MM-14 | 4 | 6 | Asafe | [Fase 6] Preparar o servidor para publicacao |
| MM-15 | 4 | 7 | Igor (com a equipe) | [Fase 7] Melhoria livre do jogo |
| MM-16 | 5 | 7 | Todos | [Sprint 5] Relatorio tecnico, README final, retrospectiva e ensaio da apresentacao |

Colunas do quadro: **Backlog → Em andamento → Em revisão → Concluído**. Só mova para Concluído depois da revisão de outro integrante.

## MM-01 — [Base] Montar repositorio, estrutura de fases e guia da equipe

### Objetivo
Criar o repositorio com as fases 0 a 2, os esqueletos do cliente e do servidor e a documentacao que orienta a equipe.

### Responsavel sugerido
Anderson

### Criterios de aceite
- [ ] Fases 0, 1 e 2 em pastas separadas e preservadas
- [ ] README, GUIA-DA-EQUIPE, ARQUITETURA, API e CLAUDE.md publicados
- [ ] Integrantes adicionados ao repositorio com permissao de escrita

## MM-02 — [Fase 1] Registrar a refatoracao SOLID como ponto de partida

### Objetivo
Documentar a analise inicial, a refatoracao e as evidencias da versao SOLID usada como Fase 1.

### Responsavel sugerido
Igor

### Criterios de aceite
- [ ] fase-01-refatoracao compila com javac e os 39 testes passam
- [ ] Analise inicial, revisao SOLID e UML presentes em docs/
- [ ] Comportamento preservado e correcoes intencionais listados

## MM-03 — [Fase 2] Validar Gradle Wrapper e JUnit 5 em maquina limpa

### Objetivo
Confirmar que a versao console roda e testa pelo Gradle Wrapper no Windows.

### Responsavel sugerido
Anderson

### Criterios de aceite
- [ ] gradlew test passa (41 testes) no Windows
- [ ] gradlew run joga uma partida completa no console
- [ ] Saida dos testes salva como evidencia em docs/

### Depende de
MM-01

## MM-04 — [Fase 3] Validar cliente libGDX desktop e explicar o ciclo de vida

### Objetivo
Rodar o cliente grafico ja montado e documentar o ciclo de vida (create, render, resize, pause, resume, dispose) e o papel dos modulos.

### Responsavel sugerido
Igor

### Criterios de aceite
- [ ] gradlew :lwjgl3:run abre a janela e permite jogar uma partida
- [ ] gradlew test do cliente passa sem abrir janela
- [ ] Tutorial curto da fase no README do cliente

### Depende de
MM-03

## MM-05 — [Fase 4] Substituir formas por sprites e liberar recursos corretamente

### Objetivo
Desenhar nave, passageiros, asteroides, inimigos e plataforma com texturas, carregadas com AssetManager.

### Responsavel sugerido
Igor

### Criterios de aceite
- [ ] Texturas em jogo/cliente/assets com origem/licenca registrada
- [ ] Todos os recursos liberados em dispose()
- [ ] Nenhuma regra de jogo dentro das telas

### Depende de
MM-04

## MM-06 — [Fase 4] Adicionar audio de embarque, colisao, vitoria e derrota

### Objetivo
Tocar efeitos sonoros a partir dos EventoTurno, com opcao para desativar o audio.

### Responsavel sugerido
Igor

### Criterios de aceite
- [ ] Sons tocam nos eventos corretos
- [ ] -Dmissaomarte.semaudio=true desativa o audio
- [ ] Sons liberados em dispose()

### Depende de
MM-04

## MM-07 — [Fase 4] Telas de nome do piloto e ranking local e fonte com acentos

### Objetivo
Completar a interface: digitar o nome do piloto, escolher o tamanho do mapa, ver o ranking local e exibir acentos (gdx-freetype ou fonte .fnt).

### Responsavel sugerido
Igor

### Criterios de aceite
- [ ] Nome do piloto aparece no HUD e no ranking
- [ ] Tela de ranking acessivel pelo menu
- [ ] Textos com acentos exibidos corretamente

### Depende de
MM-04

## MM-08 — [Fase 5] Login com token e protecao das rotas autenticadas

### Objetivo
Implementar POST /api/autenticacao/login e validar o token Bearer nas rotas de partidas, conforme docs/API.md.

### Responsavel sugerido
Asafe

### Criterios de aceite
- [ ] Login correto devolve token e expiracao; login errado devolve 401
- [ ] Rotas /api/partidas/** exigem token valido
- [ ] Testes MockMvc cobrindo sucesso, senha errada e token ausente ou expirado
- [ ] Token e senha nunca aparecem em log

## MM-09 — [Fase 5] Registrar partidas e historico do jogador

### Objetivo
Implementar POST /api/partidas e GET /api/partidas/minhas com DTOs e validacao.

### Responsavel sugerido
Asafe

### Criterios de aceite
- [ ] Entidade Partida ligada ao Jogador via JPA
- [ ] Dados invalidos devolvem 400 no formato ErroResposta
- [ ] Historico ordenado da mais recente para a mais antiga
- [ ] Testes do servidor passando

### Depende de
MM-08

## MM-10 — [Fase 5] Ranking global e documentacao da API

### Objetivo
Implementar GET /api/ranking e completar docs/API.md, incluindo a limitacao de validacao da pontuacao.

### Responsavel sugerido
Asafe

### Criterios de aceite
- [ ] Ranking considera so vitorias, com limite configuravel
- [ ] docs/API.md atualizado com exemplos reais
- [ ] Limitacao da pontuacao enviada pelo cliente documentada

### Depende de
MM-09

## MM-11 — [Fase 5] ClienteRankingHttp com Gdx.net e telas de cadastro e login

### Objetivo
Implementar ClienteRanking com chamadas assincronas, sem bloquear a interface, e preservar o modo convidado.

### Responsavel sugerido
Anderson

### Criterios de aceite
- [ ] Cadastro, login, envio de partida e ranking global funcionando contra o servidor
- [ ] Servidor desligado: mensagem amigavel e o jogo continua como convidado
- [ ] Respostas aplicadas na tela via Gdx.app.postRunnable
- [ ] Teste do cliente com servidor falso ou parser de JSON testado

### Depende de
MM-08

## MM-12 — [Fase 6] Validar modulo Android e gerar APK de debug

### Objetivo
Gerar o APK com o Android SDK, reutilizando core e dominio, e preservar o build desktop.

### Responsavel sugerido
Anderson

### Criterios de aceite
- [ ] gradlew :android:assembleDebug gera o APK (ou a limitacao de ambiente fica documentada com evidencias)
- [ ] gradlew :lwjgl3:run continua funcionando
- [ ] Ranking local funcionando no Android (RankingRepository compativel)

### Depende de
MM-04

## MM-13 — [Fase 6] Controles por toque e endereco do servidor configuravel

### Objetivo
Adicionar botoes na tela (setas, embarcar, abortar) e uma tela para informar o endereco do servidor, sem segredos no app.

### Responsavel sugerido
Anderson

### Criterios de aceite
- [ ] Partida jogavel so com toque
- [ ] Layout adaptado a telas de celular
- [ ] Endereco do servidor configuravel no jogo

### Depende de
MM-12

## MM-14 — [Fase 6] Preparar o servidor para publicacao

### Objetivo
Documentar em jogo/servidor/DEPLOY.md a configuracao necessaria para publicar o servidor.

### Responsavel sugerido
Asafe

### Criterios de aceite
- [ ] Variaveis de ambiente, porta, health check e persistencia dos dados descritas
- [ ] Instrucoes para aceitar conexoes do celular (SERVER_ADDRESS=0.0.0.0)
- [ ] Nenhum segredo versionado

### Depende de
MM-10

## MM-15 — [Fase 7] Melhoria livre do jogo

### Objetivo
Escolher e implementar uma melhoria compativel com o prazo (ex.: novo passageiro, inimigo perseguidor, nova arma), usando os pontos de extensao existentes.

### Responsavel sugerido
Igor (com a equipe)

### Criterios de aceite
- [ ] Melhoria escolhida e registrada nesta issue
- [ ] Implementada no dominio com testes
- [ ] Comportamento anterior preservado ou mudancas intencionais documentadas

### Depende de
MM-05

## MM-16 — [Sprint 5] Relatorio tecnico, README final, retrospectiva e ensaio da apresentacao

### Objetivo
Congelar funcionalidades, revisar testes e comandos e preparar a entrega no AVA.

### Responsavel sugerido
Todos

### Criterios de aceite
- [ ] Comandos do README conferidos em outra maquina
- [ ] Relatorio tecnico (2 a 4 paginas) entregue
- [ ] Retrospectiva registrada em docs/RETROSPECTIVA.md
- [ ] Kanban fechado e apresentacao ensaiada (10 a 15 min)

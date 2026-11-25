# Missão Marte Unifor — Projeto Integrador (Grupo 01)

Projeto final da disciplina **Projeto e Arquitetura de Sistemas (UNIFOR, T200)**: evoluir o jogo de console *Missão Marte Unifor* até um jogo gráfico multiplataforma (desktop e Android) com servidor de ranking.

**Equipe:** Anderson Silverio de Oliveira · Igor Damasceno Andrade · Asafe

> Novo no repositório? Comece pelo [Guia da equipe](docs/GUIA-DA-EQUIPE.md). Ele explica de onde partimos, o que já está pronto, quem faz o quê e como usar a IA sem quebrar a parte dos outros.

## Onde está cada fase

| Fase | Pasta | Situação |
|---|---|---|
| 0 — Jogo original (console) | [`fases/fase-0-original`](fases/fase-0-original) | Preservada, sem alterações |
| 1 — Refatoração SOLID | [`fases/fase-01-refatoracao`](fases/fase-01-refatoracao) | Concluída (39 testes) |
| 2 — Gradle + JUnit 5 | [`fases/fase-02-gradle`](fases/fase-02-gradle) | Concluída (41 testes) |
| 3 e 4 — libGDX e interface gráfica | [`jogo/cliente`](jogo/cliente) (`dominio`, `core`, `lwjgl3`) | Base jogável pronta; falta sprites, áudio e telas extras |
| 5 — Servidor e ranking global | [`jogo/servidor`](jogo/servidor) + `jogo/cliente/core/.../rede` | Cadastro de jogador pronto; falta login, partidas e ranking |
| 6 — Android | [`jogo/cliente/android`](jogo/cliente/android) | Esqueleto pronto; falta toque e APK |
| 7 — Evolução livre | a definir | Não iniciada |

As fases 0, 1 e 2 ficam congeladas. Daqui em diante, o desenvolvimento acontece em `jogo/`, e o fim de cada fase é marcado com uma tag do git (`fase-03`, `fase-04` etc.). O motivo dessa decisão está em [docs/ARQUITETURA.md](docs/ARQUITETURA.md).

## Requisitos

- JDK 17 ou superior (`java -version`). Não precisa instalar o Gradle: cada projeto tem o seu Gradle Wrapper (`gradlew`).
- Placa de vídeo com OpenGL 2.0 ou superior, para rodar o cliente desktop.
- Android SDK (plataforma 34), só para gerar o APK. Sem ele, o módulo `android` é ignorado e o desktop continua funcionando.
- Nenhum banco para instalar: o servidor usa H2 em arquivo.

## Como executar (Windows, PowerShell ou Git Bash)

```powershell
# Fase 0 — original
cd fases\fase-0-original
javac -encoding UTF-8 -d out src\exercicio10\*.java
java -cp out exercicio10.Main

# Fase 2 — console com Gradle
cd fases\fase-02-gradle
.\gradlew.bat test
.\gradlew.bat run --console=plain

# Cliente gráfico (desktop)
cd jogo\cliente
.\gradlew.bat test
.\gradlew.bat :lwjgl3:run

# Servidor (outro terminal) — http://localhost:8080/actuator/health
cd jogo\servidor
.\gradlew.bat test
.\gradlew.bat bootRun
```

No Git Bash, use `./gradlew` em vez de `.\gradlew.bat`. Na primeira execução, o wrapper baixa o Gradle 8.7 e as bibliotecas, então leva alguns minutos.

## Documentação

| Documento | Conteúdo |
|---|---|
| [docs/GUIA-DA-EQUIPE.md](docs/GUIA-DA-EQUIPE.md) | Ponto de partida, divisão das partes, fluxo de trabalho no Git e Kanban |
| [docs/ARQUITETURA.md](docs/ARQUITETURA.md) | Camadas, módulos, decisões técnicas e comparação antes/depois |
| [docs/API.md](docs/API.md) | Endpoints do servidor (prontos e planejados), formato de erro e configuração |
| [docs/kanban/](docs/kanban/) | Backlog de issues pronto para importar no GitHub Project |
| [CLAUDE.md](CLAUDE.md) | Regras do projeto para quem usa IA (Claude, Copilot etc.) |
| [docs/RETROSPECTIVA.md](docs/RETROSPECTIVA.md) | Modelo da retrospectiva da Sprint 5 |

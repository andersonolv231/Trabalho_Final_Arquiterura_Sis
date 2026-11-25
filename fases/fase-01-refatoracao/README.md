# Fase 1 — Refatoração SOLID

A refatoração feita por Igor Damasceno Andrade na atividade de SOLID da disciplina, usada como ponto de partida do projeto integrador. O README original dessa atividade está em [README-SOLID-IGOR.md](README-SOLID-IGOR.md). Nele, os caminhos `src/exercicio10` se referem a [`../fase-0-original`](../fase-0-original).

| Pacote | Papel |
|---|---|
| `model` | estado do mundo (sem dependências) |
| `service` | regras do turno e casos de uso (sem console) |
| `repository` | ranking em JSON, atrás de uma interface |
| `presentation` | console |

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out solidexercicio10.Main --seed 5

javac -encoding UTF-8 -d out $(find src test -name "*.java")
java -cp out solidexercicio10.ExecutarTestes
```

Resultado: 39 testes, 39 sucessos. Documentos: [análise inicial](docs/ANALISE-INICIAL.md), [revisão SOLID](REVISAO-SOLID.md), [UML](docs/uml) e [evidências](docs/evidencias).

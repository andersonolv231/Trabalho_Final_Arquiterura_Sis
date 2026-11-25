# API do servidor

Base: `http://localhost:8080`. Corpo sempre em JSON (`Content-Type: application/json`).

## Situação dos endpoints

| Método | Caminho | Autenticação | Situação |
|---|---|---|---|
| GET | `/actuator/health` | não | pronto |
| POST | `/api/jogadores` | não | **pronto** |
| POST | `/api/autenticacao/login` | não | a fazer (issue de login) |
| POST | `/api/partidas` | Bearer token | a fazer |
| GET | `/api/partidas/minhas` | Bearer token | a fazer |
| GET | `/api/ranking?limite=10` | não | a fazer |

## Endpoints

### POST /api/jogadores (pronto)
Requisição:
```json
{ "usuario": "piloto1", "senha": "segredo123" }
```
Regras: o usuário tem de 3 a 30 caracteres (letras, números, `.`, `-` e `_`); a senha tem de 6 a 72 caracteres.

Respostas:
- `201`: `{ "id": 1, "usuario": "piloto1", "criadoEm": "2026-11-25T16:00:00Z" }` (nunca devolve a senha nem o hash)
- `400`: erro de validação, com `campos` preenchido
- `409`: usuário já cadastrado

### POST /api/autenticacao/login (planejado)
Requisição: `{ "usuario": "piloto1", "senha": "segredo123" }`

Resposta `200`: `{ "token": "c1f3...", "expiraEm": "2026-11-25T18:00:00Z" }`. Com usuário ou senha errados, responde `401` com a mesma mensagem nos dois casos.

Sugestão de implementação: um token opaco, gerado com `SecureRandom` e salvo numa tabela `tokens` (jogador, token, expiraEm). A validade vem de `missaomarte.token.validade-minutos`. Esse modelo é mais simples que JWT e não precisa de segredo. Um `HandlerInterceptor` valida o cabeçalho `Authorization: Bearer <token>` nas rotas `/api/partidas/**` e responde `401` se o token estiver ausente, for inválido ou estiver expirado.

### POST /api/partidas (planejado)
Cabeçalho: `Authorization: Bearer <token>`
```json
{ "pontuacao": 48, "dificuldade": "FACIL", "passageirosResgatados": 4, "duracaoSegundos": 95, "resultado": "VITORIA" }
```
Validações: `pontuacao >= 0`, `dificuldade` em `FACIL|MEDIO|DIFICIL`, `passageirosResgatados` entre 0 e 5, `duracaoSegundos >= 0` e `resultado` em `VITORIA|DERROTA|ABORTADA`. Resposta: `201` com a partida salva (DTO).

### GET /api/partidas/minhas (planejado)
Devolve o histórico do jogador autenticado, da partida mais recente para a mais antiga.

### GET /api/ranking (planejado)
Parâmetro `limite` (de 1 a 50, padrão 10). Considera só vitórias. Resposta:
```json
[ { "posicao": 1, "usuario": "piloto1", "pontuacao": 48, "dificuldade": "FACIL", "data": "2026-11-25T16:10:00Z" } ]
```

## Formato padrão de erro

```json
{
  "momento": "2026-11-25T16:00:00Z",
  "status": 400,
  "erro": "VALIDACAO",
  "mensagem": "Dados inválidos",
  "campos": { "usuario": "o usuário deve ter entre 3 e 30 caracteres" }
}
```
Códigos de `erro`: `VALIDACAO`, `CORPO_INVALIDO`, `CONFLITO`, `NAO_AUTORIZADO`, `ERRO_INTERNO` e `HTTP_<status>` para os erros do próprio Spring (404, 405 etc.).

## Configuração (variáveis de ambiente)

| Variável | Padrão | Uso |
|---|---|---|
| `PORT` | `8080` | porta HTTP |
| `SERVER_ADDRESS` | `localhost` | use `0.0.0.0` para aceitar conexões do celular na mesma rede |
| `MISSAOMARTE_DADOS` | `./dados` | pasta do banco H2 (em arquivo) |
| `MISSAOMARTE_TOKEN_VALIDADE_MINUTOS` | `120` | validade do token de login |

No cliente, o endereço do servidor vem de `-Dmissaomarte.servidor=http://IP:8080` (desktop). No Android, deve ser configurável numa tela do jogo. Nenhum segredo fica no aplicativo.

## Limitação importante

O servidor **não executa a lógica do jogo**, então não tem como provar que a pontuação enviada pelo cliente é verdadeira. Um cliente modificado poderia enviar qualquer valor. O servidor pode, no máximo, rejeitar valores impossíveis (por exemplo, acima da pontuação máxima da dificuldade). Essa limitação deve constar no relatório.

## Testando sem o jogo

```bash
curl -i -X POST http://localhost:8080/api/jogadores -H "Content-Type: application/json" -d "{\"usuario\":\"piloto1\",\"senha\":\"segredo123\"}"
curl http://localhost:8080/actuator/health
```

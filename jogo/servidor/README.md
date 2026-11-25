# Servidor — Spring Boot

API REST de cadastro, autenticação, partidas e ranking global. É um projeto independente: não depende do código do cliente. Os endpoints, o formato de erro e as variáveis de ambiente estão em [../../docs/API.md](../../docs/API.md).

```powershell
.\gradlew.bat test
.\gradlew.bat bootRun
```

Teste rápido: abra `http://localhost:8080/actuator/health` (deve responder `{"status":"UP"}`).

Para aceitar conexões do celular na mesma rede Wi-Fi:

```powershell
$env:SERVER_ADDRESS="0.0.0.0"; .\gradlew.bat bootRun
```

O banco H2 fica em `./dados` (ou na pasta definida em `MISSAOMARTE_DADOS`). Essa pasta é ignorada pelo Git.

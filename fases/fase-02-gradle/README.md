# Fase 2 — Gradle e JUnit 5

O mesmo jogo de console da Fase 1, agora com build reproduzível pelo **Gradle Wrapper 8.7** e testes no **JUnit 5**.

O que mudou em relação à Fase 1:

- Estrutura `src/main/java` e `src/test/java`, e o pacote renomeado de `solidexercicio10` para `br.edu.unifor.missaomarte`.
- `build.gradle` com os plugins `java` e `application`, compilação para Java 17 (`options.release = 17`), JUnit 5 pelo BOM e o `run` ligado ao teclado (`standardInput = System.in`).
- `TodosOsTestesTest` usa `@TestFactory` para rodar os 39 testes da Fase 1 como testes JUnit. `DominioIndependenteTest` verifica que `model` e `service` não usam console nem a camada de apresentação.

```powershell
.\gradlew.bat test
.\gradlew.bat run --console=plain
.\gradlew.bat run --console=plain --args="--seed 5"
```

Resultado esperado de `test`: 41 testes passando. O relatório HTML fica em `build/reports/tests/test/index.html`.

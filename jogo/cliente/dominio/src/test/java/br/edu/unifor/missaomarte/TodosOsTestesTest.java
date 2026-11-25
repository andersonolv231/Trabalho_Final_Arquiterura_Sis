package br.edu.unifor.missaomarte;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

@DisplayName("Missão Marte - regras do jogo (sem libGDX)")
class TodosOsTestesTest {

    @TestFactory
    Stream<DynamicTest> testesDoDominio() {
        Map<String, Verifica.Acao> testes = new LinkedHashMap<>();
        ModelTest.registrar(testes);
        PartidaTest.registrar(testes);
        JogoServiceTest.registrar(testes);
        RepositoryTest.registrar(testes);
        return testes.entrySet().stream()
                .map(teste -> DynamicTest.dynamicTest(teste.getKey(), () -> teste.getValue().executar()));
    }
}

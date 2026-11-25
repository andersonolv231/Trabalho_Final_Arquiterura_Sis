package br.edu.unifor.missaomarte;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DominioIndependenteTest {

    private static final Path RAIZ = Path.of("src/main/java/br/edu/unifor/missaomarte");

    @Test
    void modelEServiceNaoDependemDaApresentacao() throws IOException {
        List<String> violacoes = arquivosDe("model", "service").stream()
                .filter(arquivo -> conteudo(arquivo).contains("import br.edu.unifor.missaomarte.presentation")
                        || conteudo(arquivo).contains("System.out.print")
                        || conteudo(arquivo).contains("import java.util.Scanner"))
                .map(Path::toString)
                .toList();
        assertTrue(violacoes.isEmpty(), "Regras dependendo de console: " + violacoes);
    }

    @Test
    void modelNaoDependeDeOutrasCamadas() throws IOException {
        List<String> violacoes = arquivosDe("model").stream()
                .filter(arquivo -> conteudo(arquivo).contains("import br.edu.unifor.missaomarte.service")
                        || conteudo(arquivo).contains("import br.edu.unifor.missaomarte.repository")
                        || conteudo(arquivo).contains("import br.edu.unifor.missaomarte.presentation"))
                .map(Path::toString)
                .toList();
        assertTrue(violacoes.isEmpty(), "Model dependendo de outras camadas: " + violacoes);
    }

    private static List<Path> arquivosDe(String... pacotes) throws IOException {
        List<Path> arquivos = new ArrayList<>();
        for (String pacote : pacotes) {
            try (Stream<Path> stream = Files.walk(RAIZ.resolve(pacote))) {
                stream.filter(p -> p.toString().endsWith(".java")).forEach(arquivos::add);
            }
        }
        return arquivos;
    }

    private static String conteudo(Path arquivo) {
        try {
            return Files.readString(arquivo);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

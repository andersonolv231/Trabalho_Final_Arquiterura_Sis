package br.edu.unifor.missaomarte;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DominioSemLibgdxTest {

    @Test
    void nenhumaClasseDoDominioImportaLibgdx() throws IOException {
        try (Stream<Path> arquivos = Files.walk(Path.of("src/main/java"))) {
            List<String> violacoes = arquivos
                    .filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> ler(p).contains("import com.badlogic"))
                    .map(Path::toString)
                    .toList();
            assertTrue(violacoes.isEmpty(), "Domínio dependendo de libGDX: " + violacoes);
        }
    }

    @Test
    void libgdxNaoEstaNoClasspathDoDominio() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("com.badlogic.gdx.Gdx"));
    }

    private static String ler(Path arquivo) {
        try {
            return Files.readString(arquivo);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

package br.edu.unifor.missaomarte.servidor.jogador;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:missaomarte-teste;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class JogadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JogadorRepository repositorio;

    @BeforeEach
    void limpar() {
        repositorio.deleteAll();
    }

    @Test
    void cadastraJogadorSemDevolverSenha() throws Exception {
        mockMvc.perform(post("/api/jogadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usuario\":\"piloto1\",\"senha\":\"segredo123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario").value("piloto1"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.senha").doesNotExist())
                .andExpect(jsonPath("$.senhaHash").doesNotExist());
    }

    @Test
    void guardaSenhaComoHash() throws Exception {
        mockMvc.perform(post("/api/jogadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuario\":\"piloto2\",\"senha\":\"segredo123\"}"));

        Jogador salvo = repositorio.findByUsuarioIgnoreCase("piloto2").orElseThrow();
        assertThat(salvo.getSenhaHash()).isNotEqualTo("segredo123").startsWith("$2");
    }

    @Test
    void recusaDadosInvalidosComErroPadronizado() throws Exception {
        mockMvc.perform(post("/api/jogadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usuario\":\"a\",\"senha\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("VALIDACAO"))
                .andExpect(jsonPath("$.campos.usuario").exists())
                .andExpect(jsonPath("$.campos.senha").exists());
    }

    @Test
    void recusaUsuarioRepetido() throws Exception {
        String corpo = "{\"usuario\":\"piloto3\",\"senha\":\"segredo123\"}";
        mockMvc.perform(post("/api/jogadores").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/jogadores").contentType(MediaType.APPLICATION_JSON).content(corpo))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.erro").value("CONFLITO"));
    }
}

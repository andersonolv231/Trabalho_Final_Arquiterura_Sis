package br.edu.unifor.missaomarte.servidor.jogador;

import br.edu.unifor.missaomarte.servidor.jogador.dto.CadastroJogadorRequest;
import br.edu.unifor.missaomarte.servidor.jogador.dto.JogadorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService servico;

    public JogadorController(JogadorService servico) {
        this.servico = servico;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JogadorResponse cadastrar(@Valid @RequestBody CadastroJogadorRequest requisicao) {
        return servico.cadastrar(requisicao);
    }
}

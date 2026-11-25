package br.edu.unifor.missaomarte.servidor.jogador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroJogadorRequest(
        @NotBlank(message = "informe o usuário")
        @Size(min = 3, max = 30, message = "o usuário deve ter entre 3 e 30 caracteres")
        @Pattern(regexp = "[A-Za-z0-9_.-]+", message = "use apenas letras, números, ponto, hífen ou sublinhado")
        String usuario,

        @NotBlank(message = "informe a senha")
        @Size(min = 6, max = 72, message = "a senha deve ter entre 6 e 72 caracteres")
        String senha) {

    @Override
    public String toString() {
        return "CadastroJogadorRequest[usuario=" + usuario + ", senha=***]";
    }
}

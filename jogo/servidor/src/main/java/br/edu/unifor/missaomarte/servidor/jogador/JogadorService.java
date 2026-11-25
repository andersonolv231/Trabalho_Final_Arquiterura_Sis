package br.edu.unifor.missaomarte.servidor.jogador;

import br.edu.unifor.missaomarte.servidor.erro.ConflitoException;
import br.edu.unifor.missaomarte.servidor.jogador.dto.CadastroJogadorRequest;
import br.edu.unifor.missaomarte.servidor.jogador.dto.JogadorResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JogadorService {

    private final JogadorRepository repositorio;
    private final PasswordEncoder passwordEncoder;

    public JogadorService(JogadorRepository repositorio, PasswordEncoder passwordEncoder) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public JogadorResponse cadastrar(CadastroJogadorRequest requisicao) {
        String usuario = requisicao.usuario().trim();
        if (repositorio.existsByUsuarioIgnoreCase(usuario)) {
            throw new ConflitoException("Usuário já cadastrado: " + usuario);
        }
        Jogador salvo = repositorio.save(new Jogador(usuario, passwordEncoder.encode(requisicao.senha())));
        return JogadorResponse.de(salvo);
    }
}

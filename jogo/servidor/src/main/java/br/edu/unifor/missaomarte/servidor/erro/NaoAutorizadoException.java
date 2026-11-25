package br.edu.unifor.missaomarte.servidor.erro;

public class NaoAutorizadoException extends RuntimeException {

    public NaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}

package br.edu.unifor.missaomarte.servidor.erro;

public class ConflitoException extends RuntimeException {

    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}

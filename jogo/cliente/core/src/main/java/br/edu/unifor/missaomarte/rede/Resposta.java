package br.edu.unifor.missaomarte.rede;

public interface Resposta<T> {
    void sucesso(T valor);

    void falha(String motivo);
}

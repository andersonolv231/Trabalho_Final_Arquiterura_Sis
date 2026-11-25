package br.edu.unifor.missaomarte.repository;

/** Falha ao gravar ou apagar o ranking (disco cheio, sem permissão, etc.). */
public class RankingPersistenciaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RankingPersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

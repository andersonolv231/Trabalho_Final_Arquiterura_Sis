package br.edu.unifor.missaomarte.rede;

public final class ItemRankingGlobal {
    private final String usuario;
    private final int pontuacao;
    private final String dificuldade;

    public ItemRankingGlobal(String usuario, int pontuacao, String dificuldade) {
        this.usuario = usuario;
        this.pontuacao = pontuacao;
        this.dificuldade = dificuldade;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public String getDificuldade() {
        return dificuldade;
    }
}

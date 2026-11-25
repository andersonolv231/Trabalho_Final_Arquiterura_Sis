package br.edu.unifor.missaomarte.rede;

public final class ConfiguracaoServidor {
    public static final String PROPRIEDADE = "missaomarte.servidor";
    public static final String PADRAO = "http://localhost:8080";

    private final String urlBase;

    public ConfiguracaoServidor(String urlBase) {
        if (urlBase == null || urlBase.isBlank()) {
            throw new IllegalArgumentException("Informe o endereço do servidor");
        }
        this.urlBase = urlBase.endsWith("/") ? urlBase.substring(0, urlBase.length() - 1) : urlBase;
    }

    public static ConfiguracaoServidor doSistema() {
        return new ConfiguracaoServidor(System.getProperty(PROPRIEDADE, PADRAO));
    }

    public String getUrlBase() {
        return urlBase;
    }

    public String endpoint(String caminho) {
        return urlBase + (caminho.startsWith("/") ? caminho : "/" + caminho);
    }
}

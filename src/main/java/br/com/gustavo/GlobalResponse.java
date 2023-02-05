package br.com.gustavo;

public class GlobalResponse {
    public boolean sucesso;
    public String mensagem;

    public GlobalResponse() {
    }

    public GlobalResponse(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

}

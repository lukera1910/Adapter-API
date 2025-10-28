public class RespostaUnificada<T> {
    private boolean sucesso;
    private String mensagem;
    private T dados;
    private String codigoErro;
    private long timestamp;
    
    // Construtor para sucesso
    public RespostaUnificada(T dados) {
        this.sucesso = true;
        this.dados = dados;
        this.mensagem = "Operação realizada com sucesso";
        this.timestamp = System.currentTimeMillis();
    }
    
    // Construtor para erro
    public RespostaUnificada(String mensagem, String codigoErro) {
        this.sucesso = false;
        this.mensagem = mensagem;
        this.codigoErro = codigoErro;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Construtor completo
    public RespostaUnificada(boolean sucesso, String mensagem, T dados, String codigoErro) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
        this.codigoErro = codigoErro;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Métodos estáticos para facilitar criação
    public static <T> RespostaUnificada<T> sucesso(T dados) {
        return new RespostaUnificada<>(dados);
    }
    
    public static <T> RespostaUnificada<T> erro(String mensagem, String codigoErro) {
        return new RespostaUnificada<>(mensagem, codigoErro);
    }
    
    // Getters
    public boolean isSucesso() { return sucesso; }
    public String getMensagem() { return mensagem; }
    public T getDados() { return dados; }
    public String getCodigoErro() { return codigoErro; }
    public long getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("RespostaUnificada{sucesso=%s, mensagem='%s', dados=%s, codigoErro='%s', timestamp=%d}", 
                           sucesso, mensagem, dados, codigoErro, timestamp);
    }
}

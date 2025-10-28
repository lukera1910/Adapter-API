public interface RedeSocialInterface {
    // Publicar conteúdo de forma padronizada
    RespostaUnificada<PublicacaoResponse> publicar(Conteudo conteudo);

    // Obter estatísticas de forma padronizada
    RespostaUnificada<Estatisticas> obterEstatisticas(String postId);

    // Agendar publicação
    RespostaUnificada<AgendamentoResponse> agendarPublicacao(Conteudo conteudo, long dataHora);

    // Deletar publicação
    RespostaUnificada<Boolean> deletarPublicacao(String postId);
}
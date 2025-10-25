public interface RedeSocialInterface {
    // Publicar conteúdo de forma padronizada
    PublicacaoResponse publicar(Conteudo conteudo);

    // Obter estatísticas de forma padronizada
    Estatisticas obterEstatisticas(String postId);

    // Agendar publicação
    AgendamentoResponse agendarPublicacao(Conteudo conteudo, long dataHora);

    // Deletar publicação
    boolean deletarPublicacao(String postId);
}
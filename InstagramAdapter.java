public class InstagramAdapter implements RedeSocialInterface {
    private InstagramAPI api;

    public InstagramAdapter(String appId, String appSecret) {
        this.api = new InstagramAPI(appId, appSecret);
    }

    @Override
    public PublicacaoResponse publicar(Conteudo conteudo) {
        // Traduz do formato unificado para o formato do Instagram
        InstagramAPI.InstagramResponse igResponse;

        if (conteudo.tipo == TipoConteudo.STORY) {
            igResponse = api.createStory(conteudo.imagemUrl, 24);
        } else {
            igResponse = api.createPost(
                conteudo.texto,
                conteudo.imagemUrl,
                conteudo.hashtags
            );
        }

        // Traduz a resposta do Instagram para o formato unificado
        PublicacaoResponse response = new PublicacaoResponse();
        response.id = igResponse.mediaId;
        response.url = igResponse.permaLink;
        response.status = igResponse.status;
        response.dataPublicacao = igResponse.timestamp;

        return response;
    }

    @Override
    public Estatisticas obterEstatisticas(String postId) {
        // Chama a API do Instagram
        InstagramAPI.InstagramInsights insights = api.getInsights(postId);

        // Converte para o formato unificado
        Estatisticas stats = new Estatisticas();
        stats.curtidas = insights.likes;
        stats.comentarios = insights.comments;
        stats.compartilhamentos = insights.shares;
        stats.visualizacoes = insights.impressions;

        return stats;
    }

    @Override
    public AgendamentoResponse agendarPublicacao(Conteudo conteudo, long dataHora) {
        // Instagram pode não ter agendamento na API simulada
        // Simula o agendamento
        System.out.println("[Instagram] Agendando post para: " + dataHora);

        AgendamentoResponse response = new AgendamentoResponse();
        response.agendamentoId = "IG_AGENDA_" + System.currentTimeMillis();
        response.dataAgenda = dataHora;
        response.sucesso = true;

        return response;
    }

    @Override
    public boolean deletarPublicacao(String postId) {
        System.out.println("[Instagram] Deletando post: " + postId);
        // Simula processo
        return true;
    }
}

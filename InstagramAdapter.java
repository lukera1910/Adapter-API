public class InstagramAdapter implements RedeSocialInterface {
    private InstagramAPI api;

    public InstagramAdapter(String appId, String appSecret) {
        this.api = new InstagramAPI(appId, appSecret);
    }

    @Override
    public RespostaUnificada<PublicacaoResponse> publicar(Conteudo conteudo) {
        try {
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

            return RespostaUnificada.sucesso(response);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao publicar no Instagram: " + e.getMessage(), "IG_PUB_ERROR");
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            // Chama a API do Instagram
            InstagramAPI.InstagramInsights insights = api.getInsights(postId);

            // Converte para o formato unificado
            Estatisticas stats = new Estatisticas();
            stats.curtidas = insights.likes;
            stats.comentarios = insights.comments;
            stats.compartilhamentos = insights.shares;
            stats.visualizacoes = insights.impressions;

            return RespostaUnificada.sucesso(stats);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao obter estatísticas do Instagram: " + e.getMessage(), "IG_STATS_ERROR");
        }
    }

    @Override
    public RespostaUnificada<AgendamentoResponse> agendarPublicacao(Conteudo conteudo, long dataHora) {
        try {
            // Instagram pode não ter agendamento na API simulada
            // Simula o agendamento
            System.out.println("[Instagram] Agendando post para: " + dataHora);

            AgendamentoResponse response = new AgendamentoResponse();
            response.agendamentoId = "IG_AGENDA_" + System.currentTimeMillis();
            response.dataAgenda = dataHora;
            response.sucesso = true;

            return RespostaUnificada.sucesso(response);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao agendar no Instagram: " + e.getMessage(), "IG_SCHEDULE_ERROR");
        }
    }

    @Override
    public RespostaUnificada<Boolean> deletarPublicacao(String postId) {
        try {
            System.out.println("[Instagram] Deletando post: " + postId);
            // Simula processo
            return RespostaUnificada.sucesso(true);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao deletar no Instagram: " + e.getMessage(), "IG_DELETE_ERROR");
        }
    }
}

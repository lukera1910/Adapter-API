public class TwitterAdapter implements RedeSocialInterface {
    private TwitterAPI api;

    public TwitterAdapter(String appId, String appSecret) {
        this.api = new TwitterAPI(appId, appSecret);
    }

    @Override
    public RespostaUnificada<PublicacaoResponse> publicar(Conteudo conteudo) {
        try {
            // Traduz do formato unificado para o formato do Twitter
            TwitterAPI.TwitterResponse twResponse;

            if (conteudo.tipo == TipoConteudo.THREAD) {
                twResponse = api.createThread(conteudo.mensagens);
            } else {
                twResponse = api.tweet(
                    conteudo.texto,
                    conteudo.imagemUrl,
                    conteudo.hashtags
                );
            }

            // Traduz a resposta do Twitter para o formato unificado
            PublicacaoResponse response = new PublicacaoResponse();
            response.id = twResponse.tweetId;
            response.url = twResponse.permaLink;
            response.status = twResponse.status;
            response.dataPublicacao = twResponse.timestamp;

            return RespostaUnificada.sucesso(response);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao publicar no Twitter: " + e.getMessage(), "TW_PUB_ERROR");
        }
    }

    @Override
    public RespostaUnificada<Estatisticas> obterEstatisticas(String postId) {
        try {
            // Chama a API do Twitter
            TwitterAPI.TwitterAnalytics metrics = api.getAnalytics(postId);

            // Converte para o formato unificado
            Estatisticas stats = new Estatisticas();
            stats.curtidas = metrics.likes;
            stats.comentarios = metrics.replies;
            stats.compartilhamentos = metrics.retweets;
            stats.visualizacoes = metrics.impressions;

            return RespostaUnificada.sucesso(stats);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao obter estatísticas do Twitter: " + e.getMessage(), "TW_STATS_ERROR");
        }
    }

    @Override
    public RespostaUnificada<AgendamentoResponse> agendarPublicacao(Conteudo conteudo, long dataHora) {
        try {
            // Twitter pode não ter agendamento na API simulada
            // Simula o agendamento
            System.out.println("[Twitter] Agendando post para: " + dataHora);

            AgendamentoResponse response = new AgendamentoResponse();
            response.agendamentoId = "TW_AGENDA_" + System.currentTimeMillis();
            response.dataAgenda = dataHora;
            response.sucesso = true;

            return RespostaUnificada.sucesso(response);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao agendar no Twitter: " + e.getMessage(), "TW_SCHEDULE_ERROR");
        }
    }

    @Override
    public RespostaUnificada<Boolean> deletarPublicacao(String postId) {
        try {
            // Simula deleção
            System.out.println("[Twitter] Deletando post: " + postId);
            return RespostaUnificada.sucesso(true);
        } catch (Exception e) {
            return RespostaUnificada.erro("Erro ao deletar no Twitter: " + e.getMessage(), "TW_DELETE_ERROR");
        }
    }
}

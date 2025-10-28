import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO SISTEMA DE REDES SOCIAIS COM FACTORY ===\n");

        // Carrega configurações do arquivo
        SocialMediaFactory.carregarConfiguracao("config.properties");
        
        // Cria todos os adapters usando o factory
        Map<SocialMediaFactory.TipoRedeSocial, RedeSocialInterface> adapters = 
            SocialMediaFactory.criarTodosAdapters();
        
        // Lista adapters ativos
        SocialMediaFactory.listarAdaptersAtivos();

        Conteudo conteudo = new Conteudo();
        conteudo.texto = "Testando integração via Adapter Pattern com Factory!";
        conteudo.imagemUrl = "https://imagem.exemplo.com/foto.jpg";
        conteudo.hashtags = new String[] { "adapter", "designpattern", "java", "factory" };
        conteudo.tipo = TipoConteudo.POST;

        System.out.println("Conteúdo a ser publicado:");
        System.out.println("Texto: " + conteudo.texto);
        System.out.println("Imagem: " + conteudo.imagemUrl);
        System.out.println("\n");

        // Testa cada rede social usando o factory
        for (Map.Entry<SocialMediaFactory.TipoRedeSocial, RedeSocialInterface> entry : adapters.entrySet()) {
            SocialMediaFactory.TipoRedeSocial tipo = entry.getKey();
            RedeSocialInterface adapter = entry.getValue();
            
            System.out.println("--- TESTANDO " + tipo.getNome().toUpperCase() + " ---");
            testarRedeSocialComRespostaUnificada(adapter, conteudo, tipo.getNome());
        }
        
        // Demonstra criação dinâmica de adapter
        System.out.println("\n=== TESTE DE CRIAÇÃO DINÂMICA ===");
        testarCriacaoDinamica();
    }
    
    private static void testarRedeSocialComRespostaUnificada(RedeSocialInterface redeSocial, Conteudo conteudo, String nomeRede) {
        try {
            System.out.println("Publicando conteúdo...");
            RespostaUnificada<PublicacaoResponse> respostaPublicacao = redeSocial.publicar(conteudo);
            
            if (respostaPublicacao.isSucesso()) {
                PublicacaoResponse publicacao = respostaPublicacao.getDados();
                System.out.println("Publicação realizada com sucesso!");
                System.out.println("ID: " + publicacao.id);
                System.out.println("URL: " + publicacao.url);
                System.out.println("Status: " + publicacao.status);
        
                System.out.println("\nObtendo estatísticas...");
                RespostaUnificada<Estatisticas> respostaStats = redeSocial.obterEstatisticas(publicacao.id);
                
                if (respostaStats.isSucesso()) {
                    Estatisticas stats = respostaStats.getDados();
                    System.out.println("Estatísticas obtidas:");
                    System.out.println("Curtidas: " + stats.curtidas);
                    System.out.println("Comentários: " + stats.comentarios);
                    System.out.println("Compartilhamentos: " + stats.compartilhamentos);
                    System.out.println("Visualizações: " + stats.visualizacoes);
                } else {
                    System.out.println("Erro ao obter estatísticas: " + respostaStats.getMensagem());
                }
        
                System.out.println("\nAgendando publicação futura...");
                long dataHoraFutura = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 24 horas
                RespostaUnificada<AgendamentoResponse> respostaAgendamento = redeSocial.agendarPublicacao(conteudo, dataHoraFutura);
                
                if (respostaAgendamento.isSucesso()) {
                    AgendamentoResponse agendamento = respostaAgendamento.getDados();
                    System.out.println("Agendamento realizado:");
                    System.out.println("ID do Agendamento: " + agendamento.agendamentoId);
                    System.out.println("Sucesso: " + agendamento.sucesso);
                } else {
                    System.out.println("Erro ao agendar: " + respostaAgendamento.getMensagem());
                }
        
                System.out.println("\nDeletando publicação...");
                RespostaUnificada<Boolean> respostaDelete = redeSocial.deletarPublicacao(publicacao.id);
                
                if (respostaDelete.isSucesso()) {
                    System.out.println("Deletado: " + (respostaDelete.getDados() ? "Sim" : "Não"));
                } else {
                    System.out.println("Erro ao deletar: " + respostaDelete.getMensagem());
                }
            } else {
                System.out.println("Erro na publicação: " + respostaPublicacao.getMensagem());
                System.out.println("Código do erro: " + respostaPublicacao.getCodigoErro());
            }
        } catch (Exception e) {
            System.out.println("Erro geral ao testar rede social: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testarCriacaoDinamica() {
        // Testa criação de adapter específico
        RedeSocialInterface instagram = SocialMediaFactory.criarAdapter(SocialMediaFactory.TipoRedeSocial.INSTAGRAM);
        System.out.println("Instagram criado: " + instagram.getClass().getSimpleName());
        
        // Testa criação com configuração personalizada
        SocialMediaFactory.ConfiguracaoRedeSocial configPersonalizada = 
            new SocialMediaFactory.ConfiguracaoRedeSocial("custom_id", "custom_secret", true);
        
        RedeSocialInterface twitterCustom = SocialMediaFactory.criarAdapter(
            SocialMediaFactory.TipoRedeSocial.TWITTER, configPersonalizada);
        System.out.println("Twitter customizado criado: " + twitterCustom.getClass().getSimpleName());
        
        // Testa verificação de status
        boolean instagramAtivo = SocialMediaFactory.isRedeSocialAtiva(SocialMediaFactory.TipoRedeSocial.INSTAGRAM);
        System.out.println("Instagram está ativo: " + instagramAtivo);
        
        // Lista configuração
        SocialMediaFactory.ConfiguracaoRedeSocial configLinkedin = 
            SocialMediaFactory.obterConfiguracao(SocialMediaFactory.TipoRedeSocial.LINKEDIN);
        System.out.println("Config LinkedIn - AppId: " + configLinkedin.getAppId() + 
                          ", Ativo: " + configLinkedin.isAtivo());
    }
}

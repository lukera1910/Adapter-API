public class Main {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO SISTEMA DE REDES SOCIAIS ===\n");

        RedeSocialInterface instagram = new InstagramAdapter("app123", "secret456");
        RedeSocialInterface twitter = new TwitterAdapter("key789", "secret012");
        RedeSocialInterface linkedin = new LinkedinAdapter("client345", "secret678");

        Conteudo conteudo = new Conteudo();
        conteudo.texto = "Testando integração via Adapter Pattern!";
        conteudo.imagemUrl = "https://imagem.exemplo.com/foto.jpg";
        conteudo.hashtags = new String[] { "adapter", "designpattern", "java" };
        conteudo.tipo = TipoConteudo.POST;

        System.out.println("Conteúdo a ser publicado:");
        System.out.println("Texto: " + conteudo.texto);
        System.out.println("Imagem: " + conteudo.imagemUrl);
        System.out.println("\n");

        System.out.println("--- TESTANDO INSTAGRAM ---");
        testarRedeSocial(instagram, conteudo, "Instagram");
        
        System.out.println("\n--- TESTANDO TWITTER ---");
        testarRedeSocial(twitter, conteudo, "Twitter");

        System.out.println("\n--- TESTANDO LINKEDIN ---");
        testarRedeSocial(linkedin, conteudo, "LinkedIn");
    }
    
    private static void testarRedeSocial(RedeSocialInterface redeSocial, Conteudo conteudo, String nomeRede) {
        try {
            System.out.println("Publicando conteúdo...");
            PublicacaoResponse publicacao = redeSocial.publicar(conteudo);
            System.out.println("Publicação realizada com sucesso!");
            System.out.println("ID: " + publicacao.id);
            System.out.println("URL: " + publicacao.url);
            System.out.println("Status: " + publicacao.status);
    
            System.out.println("\nObtendo estatísticas...");
            Estatisticas stats = redeSocial.obterEstatisticas(publicacao.id);
            System.out.println("Curtidas: " + stats.curtidas);
            System.out.println("Comentários: " + stats.comentarios);
            System.out.println("Compartilhamentos: " + stats.compartilhamentos);
            System.out.println("Visualizações: " + stats.visualizacoes);
    
            System.out.println("\nAgendando publicação futura...");
            long dataHoraFutura = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 24 horas a partir de agora
            AgendamentoResponse agendamento = redeSocial.agendarPublicacao(conteudo, dataHoraFutura);
            System.out.println("Agendamento realizado:");
            System.out.println("ID do Agendamento: " + agendamento.agendamentoId);
            System.out.println("Sucesso: " + agendamento.sucesso);
    
            System.out.println("\nDeletando publicação...");
            boolean deletado = redeSocial.deletarPublicacao(publicacao.id);
            System.out.println("Deletado: " + (deletado ? "Sim" : "Não"));
        } catch (Exception e) {
            System.out.println("Erro ao testar rede social: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

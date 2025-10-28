import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class SocialMediaFactory {
    private static Map<String, RedeSocialInterface> adapters = new HashMap<>();
    private static Properties config = new Properties();
    
    // Enum para tipos de redes sociais
    public enum TipoRedeSocial {
        INSTAGRAM("instagram"),
        TWITTER("twitter"), 
        LINKEDIN("linkedin");
        
        private final String nome;
        
        TipoRedeSocial(String nome) {
            this.nome = nome;
        }
        
        public String getNome() {
            return nome;
        }
    }
    
    // Classe para configuração de credenciais
    public static class ConfiguracaoRedeSocial {
        private String appId;
        private String appSecret;
        private boolean ativo;
        
        public ConfiguracaoRedeSocial(String appId, String appSecret, boolean ativo) {
            this.appId = appId;
            this.appSecret = appSecret;
            this.ativo = ativo;
        }
        
        // Getters
        public String getAppId() { return appId; }
        public String getAppSecret() { return appSecret; }
        public boolean isAtivo() { return ativo; }
    }
    
    // Carrega configurações do arquivo
    public static void carregarConfiguracao(String arquivoConfig) {
        try {
            config.load(new FileInputStream(arquivoConfig));
            System.out.println("Configurações carregadas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações: " + e.getMessage());
            // Usa configurações padrão
            usarConfiguracoesPadrao();
        }
    }
    
    // Configurações padrão caso não consiga carregar arquivo
    private static void usarConfiguracoesPadrao() {
        config.setProperty("instagram.appId", "app123");
        config.setProperty("instagram.appSecret", "secret456");
        config.setProperty("instagram.ativo", "true");
        
        config.setProperty("twitter.appId", "key789");
        config.setProperty("twitter.appSecret", "secret012");
        config.setProperty("twitter.ativo", "true");
        
        config.setProperty("linkedin.appId", "client345");
        config.setProperty("linkedin.appSecret", "secret678");
        config.setProperty("linkedin.ativo", "true");
    }
    
    // Cria adapter baseado no tipo
    public static RedeSocialInterface criarAdapter(TipoRedeSocial tipo) {
        return criarAdapter(tipo, null);
    }
    
    // Cria adapter com configuração específica
    public static RedeSocialInterface criarAdapter(TipoRedeSocial tipo, ConfiguracaoRedeSocial configuracao) {
        String chave = tipo.getNome();
        
        // Verifica se já existe na cache
        if (adapters.containsKey(chave)) {
            System.out.println("Retornando adapter em cache: " + chave);
            return adapters.get(chave);
        }
        
        // Cria novo adapter
        RedeSocialInterface adapter = null;
        
        switch (tipo) {
            case INSTAGRAM:
                if (configuracao != null) {
                    adapter = new InstagramAdapter(configuracao.getAppId(), configuracao.getAppSecret());
                } else {
                    String appId = config.getProperty("instagram.appId", "app123");
                    String appSecret = config.getProperty("instagram.appSecret", "secret456");
                    adapter = new InstagramAdapter(appId, appSecret);
                }
                break;
                
            case TWITTER:
                if (configuracao != null) {
                    adapter = new TwitterAdapter(configuracao.getAppId(), configuracao.getAppSecret());
                } else {
                    String appId = config.getProperty("twitter.appId", "key789");
                    String appSecret = config.getProperty("twitter.appSecret", "secret012");
                    adapter = new TwitterAdapter(appId, appSecret);
                }
                break;
                
            case LINKEDIN:
                if (configuracao != null) {
                    adapter = new LinkedinAdapter(configuracao.getAppId(), configuracao.getAppSecret());
                } else {
                    String appId = config.getProperty("linkedin.appId", "client345");
                    String appSecret = config.getProperty("linkedin.appSecret", "secret678");
                    adapter = new LinkedinAdapter(appId, appSecret);
                }
                break;
        }
        
        // Armazena na cache
        if (adapter != null) {
            adapters.put(chave, adapter);
            System.out.println("Novo adapter criado e armazenado: " + chave);
        }
        
        return adapter;
    }
    
    // Cria múltiplos adapters de uma vez
    public static Map<TipoRedeSocial, RedeSocialInterface> criarTodosAdapters() {
        Map<TipoRedeSocial, RedeSocialInterface> todosAdapters = new HashMap<>();
        
        for (TipoRedeSocial tipo : TipoRedeSocial.values()) {
            String ativo = config.getProperty(tipo.getNome() + ".ativo", "true");
            if ("true".equals(ativo)) {
                RedeSocialInterface adapter = criarAdapter(tipo);
                todosAdapters.put(tipo, adapter);
            } else {
                System.out.println("Rede social " + tipo.getNome() + " está desabilitada");
            }
        }
        
        return todosAdapters;
    }
    
    // Remove adapter da cache
    public static void removerAdapter(TipoRedeSocial tipo) {
        String chave = tipo.getNome();
        if (adapters.containsKey(chave)) {
            adapters.remove(chave);
            System.out.println("Adapter removido da cache: " + chave);
        }
    }
    
    // Limpa toda a cache
    public static void limparCache() {
        adapters.clear();
        System.out.println("Cache de adapters limpa");
    }
    
    // Lista adapters ativos
    public static void listarAdaptersAtivos() {
        System.out.println("\n=== ADAPTERS ATIVOS ===");
        for (Map.Entry<String, RedeSocialInterface> entry : adapters.entrySet()) {
            System.out.println("- " + entry.getKey().toUpperCase() + ": " + entry.getValue().getClass().getSimpleName());
        }
        System.out.println("Total: " + adapters.size() + " adapters\n");
    }
    
    // Verifica se um tipo está configurado
    public static boolean isRedeSocialAtiva(TipoRedeSocial tipo) {
        String ativo = config.getProperty(tipo.getNome() + ".ativo", "true");
        return "true".equals(ativo);
    }
    
    // Obtém configuração de uma rede social
    public static ConfiguracaoRedeSocial obterConfiguracao(TipoRedeSocial tipo) {
        String appId = config.getProperty(tipo.getNome() + ".appId");
        String appSecret = config.getProperty(tipo.getNome() + ".appSecret");
        boolean ativo = isRedeSocialAtiva(tipo);
        
        return new ConfiguracaoRedeSocial(appId, appSecret, ativo);
    }
}

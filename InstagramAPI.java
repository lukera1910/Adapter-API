public class InstagramAPI {
    private String appId;
    private String appSecret;

    public InstagramAPI(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    // Instagram usa "createPost" ao invés de "publicar"
    public InstagramResponse createPost(String caption, String imageUrl, String[] hashtags) {
        // Simula delay da API
        System.out.println("[Instagram API] Processando post...");

        // Retorna resposta no formato específico do Instagram
        InstagramResponse response = new InstagramResponse();
        response.mediaId = "IG_" + System.currentTimeMillis();
        response.permaLink = "https://instagram.com/p/" + response.mediaId;
        response.timestamp = System.currentTimeMillis();
        response.status = "published";

        return response;
    }

    // Instagram tem método específico para stories
    public InstagramResponse createStory(String imageUrl, int durationSeconds) {
        System.out.println("[Instagram API] Publicando story...");

        InstagramResponse response = new InstagramResponse();
        response.mediaId = "STORY_" + System.currentTimeMillis();
        response.expiresIn = durationSeconds;
        response.status = "live";

        return response;
    }

    // Método para obter insights (estatísticas)
    public InstagramInsights getInsights(String mediaId) {
        System.out.println("[Instagram API] Buscando insights...");

        InstagramInsights insights = new InstagramInsights();
        insights.likes = (int)(Math.random() * 1000);
        insights.comments = (int)(Math.random() * 100);
        insights.shares = (int)(Math.random() * 50);
        insights.reach = (int)(Math.random() * 5000);
        insights.impressions = insights.reach + (int)(Math.random() * 2000);

        return insights;
    }

    // Classes internas para resposta (formato específico do Instagram)
    public static class InstagramResponse {
        public String mediaId;
        public String permaLink;
        public long timestamp;
        public String status;
        public int expiresIn;
    }

    public static class InstagramInsights {
        public int likes;
        public int comments;
        public int shares;
        public int reach;
        public int impressions;
    }
}
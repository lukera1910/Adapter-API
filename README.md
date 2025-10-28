# Adapter-API

Sistema de integração unificada para redes sociais implementando o **Padrão Adapter** com **Factory Pattern** e **Sistema de Resposta Unificado**.

## 🚀 Funcionalidades Implementadas

### ✅ Tarefa 1: Padrão Adapter Básico
- Interface unificada `RedeSocialInterface` para todas as redes sociais
- Adapters específicos para Instagram, Twitter e LinkedIn
- Conversão automática entre formatos unificados e específicos de cada API

### ✅ Tarefa 2: Sistema de Resposta Unificado
- Classe `RespostaUnificada<T>` para padronizar todas as respostas
- Tratamento de erros consistente com códigos específicos
- Métodos estáticos para facilitar criação de respostas de sucesso/erro
- Timestamp automático em todas as respostas

### ✅ Tarefa 3: Social Media Factory com Configuração Dinâmica
- Factory `SocialMediaFactory` para criação dinâmica de adapters
- Carregamento de configurações via arquivo `config.properties`
- Cache de adapters para melhor performance
- Configuração personalizada por rede social
- Enum `TipoRedeSocial` para type safety

## 📁 Estrutura do Projeto

```
├── Main.java                    # Classe principal com testes
├── RedeSocialInterface.java     # Interface unificada
├── RespostaUnificada.java       # Sistema de resposta padronizado
├── SocialMediaFactory.java      # Factory para criação dinâmica
├── config.properties            # Arquivo de configuração
├── InstagramAdapter.java        # Adapter para Instagram
├── TwitterAdapter.java          # Adapter para Twitter
├── LinkedinAdapter.java         # Adapter para LinkedIn
├── InstagramAPI.java            # API simulada do Instagram
├── TwitterAPI.java              # API simulada do Twitter
├── LinkedinAPI.java             # API simulada do LinkedIn
├── Conteudo.java                # Modelo de conteúdo
├── PublicacaoResponse.java      # Resposta de publicação
├── AgendamentoResponse.java     # Resposta de agendamento
├── Estatisticas.java            # Modelo de estatísticas
└── TipoConteudo.java            # Enum de tipos de conteúdo
```

## 🔧 Como Usar

### 1. Configuração
Edite o arquivo `config.properties`:
```properties
instagram.appId=seu_app_id
instagram.appSecret=seu_app_secret
instagram.ativo=true

twitter.appId=sua_api_key
twitter.appSecret=seu_api_secret
twitter.ativo=true

linkedin.appId=seu_client_id
linkedin.appSecret=seu_client_secret
linkedin.ativo=true
```

### 2. Uso Básico
```java
// Carrega configurações
SocialMediaFactory.carregarConfiguracao("config.properties");

// Cria todos os adapters ativos
Map<TipoRedeSocial, RedeSocialInterface> adapters = 
    SocialMediaFactory.criarTodosAdapters();

// Usa um adapter específico
RedeSocialInterface instagram = adapters.get(TipoRedeSocial.INSTAGRAM);
```

### 3. Uso Avançado
```java
// Criação com configuração personalizada
ConfiguracaoRedeSocial config = new ConfiguracaoRedeSocial(
    "custom_id", "custom_secret", true);
RedeSocialInterface twitter = SocialMediaFactory.criarAdapter(
    TipoRedeSocial.TWITTER, config);

// Verificação de status
boolean ativo = SocialMediaFactory.isRedeSocialAtiva(TipoRedeSocial.INSTAGRAM);

// Obtenção de configuração
ConfiguracaoRedeSocial configLinkedin = 
    SocialMediaFactory.obterConfiguracao(TipoRedeSocial.LINKEDIN);
```
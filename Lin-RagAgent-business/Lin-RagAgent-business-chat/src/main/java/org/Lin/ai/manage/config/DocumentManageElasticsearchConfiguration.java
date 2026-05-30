package org.Lin.ai.manage.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;
import org.opensearch.client.RestClient;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 配置类
 * @author: Lin-RAGAgent
 **/

@Configuration
@EnableConfigurationProperties(DocumentManageProperties.class)
@ConditionalOnProperty(prefix = "app.manage.elasticsearch", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentManageElasticsearchConfiguration {

    @Bean(name = "documentManageElasticsearchRestClient", destroyMethod = "close")
    public RestClient documentManageElasticsearchRestClient(DocumentManageProperties properties) {
        DocumentManageProperties.Elasticsearch elasticsearch = properties.getElasticsearch();
        if (CollUtil.isEmpty(elasticsearch.getUris())) {
            throw new IllegalStateException("app.manage.elasticsearch.uris 不能为空");
        }

        HttpHost[] hosts = elasticsearch.getUris().stream()
            .filter(StrUtil::isNotBlank)
            .map(this::createHttpHost)
            .toArray(HttpHost[]::new);

        org.opensearch.client.RestClientBuilder builder = RestClient.builder(hosts)
            .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                .setConnectTimeout(Timeout.ofMilliseconds(elasticsearch.getConnectTimeoutMillis()))
                .setResponseTimeout(Timeout.ofMilliseconds(elasticsearch.getSocketTimeoutMillis())));

        if (StrUtil.isNotBlank(elasticsearch.getUsername())) {
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                new AuthScope(null, -1),
                new UsernamePasswordCredentials(
                    elasticsearch.getUsername(),
                    StrUtil.blankToDefault(elasticsearch.getPassword(), "").toCharArray()
                )
            );
            builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider));
        }
        return builder.build();
    }

    private HttpHost createHttpHost(String uri) {
        try {
            return HttpHost.create(uri);
        }
        catch (java.net.URISyntaxException exception) {
            throw new IllegalArgumentException("Invalid app.manage.elasticsearch URI: " + uri, exception);
        }
    }

    @Bean(name = "documentManageElasticsearchTransport", destroyMethod = "close")
    public OpenSearchTransport documentManageElasticsearchTransport(
        @Qualifier("documentManageElasticsearchRestClient") RestClient restClient,
        com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        return new RestClientTransport(restClient, new JacksonJsonpMapper(objectMapper));
    }

    @Bean(name = "documentManageElasticsearchClient")
    public OpenSearchClient documentManageElasticsearchClient(
        @Qualifier("documentManageElasticsearchTransport") OpenSearchTransport transport) {
        return new OpenSearchClient(transport);
    }
}

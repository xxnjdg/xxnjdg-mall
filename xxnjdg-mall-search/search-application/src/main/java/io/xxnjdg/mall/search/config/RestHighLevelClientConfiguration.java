package io.xxnjdg.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/15 18:08
 */
@Configuration
public class RestHighLevelClientConfiguration {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Value("${rhlc.hostname}")
    private String hostname;

    @Value("${rhlc.port}")
    private Integer port;

    @Value("${rhlc.scheme}")
    private String scheme;

    @Bean
    public RestHighLevelClient  restHighLevelClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)));
    }

}

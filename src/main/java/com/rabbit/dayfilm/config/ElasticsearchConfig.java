//package com.rabbit.dayfilm.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import reactor.util.annotation.NonNullApi;
//
//@Configuration
//public class ElasticsearchConfig extends ElasticsearchConfiguration {
//
//    @Value("${spring.elasticsearch.uris}")
//    private String host;
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedTo(host)
//                .build();
//    }
//}

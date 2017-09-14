package com.naresh.orderappmicroservice.config;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2FeignAutoConfiguration {

    @Bean
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext,details);
    }


}
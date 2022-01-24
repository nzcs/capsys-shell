package hu.capsys.shell.confing;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class FeignClientConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() throws IOException {
//        FileInputStream inputStream = new FileInputStream(ResourceUtils.getFile("classpath:dto.json"));
//        StringWriter writer = new StringWriter();
//        IOUtils.copy(inputStream, writer, "UTF-8");
//        String json = writer.toString();
//
//        return requestTemplate -> {
//            requestTemplate.header("Content-Type", "application/json");
//        };
//    }
}

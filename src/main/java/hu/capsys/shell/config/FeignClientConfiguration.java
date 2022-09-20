package hu.capsys.shell.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

@Configuration
public class FeignClientConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "security.oauth2.client")
//    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//        return new ClientCredentialsResourceDetails();
//    }
//
//    @Bean
//    public RequestInterceptor oauth2FeignRequestInterceptor() {
//        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
//    }
//
//    @Bean
//    public OAuth2RestTemplate clientCredentialsRestTemplate() {
//        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
//    }


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


    @Value("${callback.keystore.path}")
    private String keystorePath;
    @Value("${callback.keystore.password}")
    private String password;
    @Value("${callback.keystore.type: jks}")
    private String keystoreType;


//    @Bean
//    public HttpClient callbackStatusHttpClient() {
//        HttpClient httpClient = HttpClient.create().secure(spec ->
//                spec.sslContext(createSSLContext())
//                        .handshakeTimeout(Duration.ofSeconds(10))
//        );

//        httpClient
//                .option(CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
//                .option(SO_KEEPALIVE, true)
//                .option(TCP_KEEPIDLE, keepIdle)
//                .option(TCP_KEEPINTVL, keepInterval)
//                .option(TCP_KEEPCNT, keepCount)
//                .responseTimeout(Duration.ofMillis(responseTimeout));

//        return httpClient;
//    }

    private SslContext createSSLContext() {
        try (FileInputStream input = new FileInputStream(ResourceUtils.getFile(keystorePath))) {
            KeyStore keyStore = KeyStore.getInstance(keystoreType);
            keyStore.load(input, password.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());

            return SslContextBuilder.forClient()
                    .keyManager(keyManagerFactory)
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error creating SSL context.", e);
        }
    }
}

package hu.capsys.shell.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
public class FeignClientConfiguration {

//    @Value("${callback.keystore.path}")
//    private String keystorePath;
//    @Value("${callback.keystore.password}")
//    private String password;
//    @Value("${callback.keystore.type: jks}")
//    private String keystoreType;

    @Autowired
    OAuth2Provider oauth2Provider;


    @Bean
    public RequestInterceptor feignInterceptor() {
        return (requestTemplate) ->
                requestTemplate
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                oauth2Provider.getAuthenticationToken("client1")
                        );
    }


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

//    private SslContext createSSLContext() {
//        try (FileInputStream input = new FileInputStream(ResourceUtils.getFile(keystorePath))) {
//            KeyStore keyStore = KeyStore.getInstance(keystoreType);
//            keyStore.load(input, password.toCharArray());
//
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore, password.toCharArray());
//
//            return SslContextBuilder.forClient()
//                    .keyManager(keyManagerFactory)
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                    .build();
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating SSL context.", e);
//        }
//    }
}

package hu.capsys.shell;

import feign.RequestInterceptor;
import hu.capsys.payment.PayeeApiClient;
import hu.capsys.paymentgateway.PayeeControllerApiClient;
import hu.capsys.paymentgateway.PaymentControllerApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
        PayeeApiClient.class,
        PayeeControllerApiClient.class,
        PaymentControllerApiClient.class,
})
public class CapsysClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsysClientApplication.class, args);
    }


    @Bean
    public RequestInterceptor feignInterceptor() {
        return (requestTemplate) -> {
//            requestTemplate.header("X-Request-ID","");
            requestTemplate.header("X-B3-TraceId", "0100f0f5d23c7251");
            requestTemplate.header("X-B3-SpanId", "0200f0f5d23c7251");
            requestTemplate.header("X-B3-ParentSpanId", "0300f0f5d23c7251");
            requestTemplate.header("x-client-trace-id", "2222");
        };
    }
}

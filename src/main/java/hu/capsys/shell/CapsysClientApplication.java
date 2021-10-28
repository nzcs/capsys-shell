package hu.capsys.shell;

import hu.capsys.payment.PayeeApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
        PayeeApiClient.class,
})
public class CapsysClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsysClientApplication.class, args);
    }

}

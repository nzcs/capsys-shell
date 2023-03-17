package hu.capsys.shell;

import hu.capsys.connector.giro.api.GiroConnectorPaymentApiClient;
import hu.capsys.gateway.masterdata.core.api.ApplicationUserBoControllerApiClient;
import hu.capsys.gateway.masterdata.core.api.PartnerBoControllerApiClient;
import hu.capsys.gateway.masterdata.plugin.cpp.api.PayeeBoControllerApiClient;
import hu.capsys.gateway.masterdata.plugin.cpp.api.TerminalBoControllerApiClient;
import hu.capsys.gateway.payment_gateway.api.PaymentGatewayControllerApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
        PartnerBoControllerApiClient.class,
        ApplicationUserBoControllerApiClient.class,
        PayeeBoControllerApiClient.class,
        TerminalBoControllerApiClient.class,
        PaymentGatewayControllerApiClient.class,
        GiroConnectorPaymentApiClient.class,
})
public class CapsysClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapsysClientApplication.class, args);
    }

}

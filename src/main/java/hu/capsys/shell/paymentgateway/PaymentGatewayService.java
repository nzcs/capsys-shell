package hu.capsys.shell.paymentgateway;

import hu.capsys.paymentgateway.PayeeControllerApiClient;
import hu.capsys.paymentgateway.PaymentControllerApiClient;
import hu.capsys.paymentgateway.TerminalControllerApiClient;
import hu.capsys.paymentgateway.model.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.connectedSystem;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.payeeDto;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.payeeReference;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.platformReference;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.shopNumber;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.terminalReference;
import static hu.capsys.shell.paymentgateway.PaymentGatewayUtil.terminalRequestDto;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class PaymentGatewayService {

    final PayeeControllerApiClient payeeApi;
    final PaymentControllerApiClient paymentApi;
    final TerminalControllerApiClient terminalApi;


    public String createPayment(String paymentReference) {
        PaymentResponseDto result = paymentApi.createPayment(
                "5d4493f2-e44a-6587",
                connectedSystem,
                payeeReference,
                shopNumber,
                terminalReference,
                paymentReference,
                PaymentGatewayUtil.paymentRequestDto()
        ).getBody();
        System.out.println(requireNonNull(result).getPaymentStatus());

        return shopNumber + "." + payeeReference + "." + platformReference;
    }


    public void loadPayee() {
        payeeApi.loadPayee(
                "112",
                connectedSystem,
                payeeReference,
                payeeDto()
        );
    }


    public void loadTerminal() {
        terminalApi.loadTerminal(
                "113",
                connectedSystem,
                payeeReference,
                shopNumber,
                terminalReference,
                terminalRequestDto()
        );
    }
}

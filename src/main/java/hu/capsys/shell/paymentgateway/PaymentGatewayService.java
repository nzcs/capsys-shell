package hu.capsys.shell.paymentgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentGatewayService {

//    final PayeeControllerApiClient payeeApi;
//    final PaymentControllerApiClient paymentApi;
//    final TerminalControllerApiClient terminalApi;
//
//
//    public String createPayment(String paymentReference) {
//        PaymentResponse1Dto result = paymentApi.createPayment(
//                connectedSystem,
//                payeeReference,
//                shopNumber,
//                terminalReference,
//                paymentReference,
//                PaymentGatewayUtil.paymentRequestDto()
//        ).getBody();
//        System.out.println(requireNonNull(result).getPaymentStatus());
//
//        return shopNumber + "." + payeeReference + "." + platformReference;
//    }
//
//
//    public void loadPayee() {
//        payeeApi.loadPayee(
//                connectedSystem,
//                payeeReference,
//                payeeDto()
//        );
//    }
//
//
//    public void loadTerminal() {
//        terminalApi.loadTerminal(
//                connectedSystem,
//                payeeReference,
//                shopNumber,
//                terminalReference,
//                terminalRequestDto()
//        );
//    }
}

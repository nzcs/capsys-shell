package hu.capsys.shell.paymentgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@RequiredArgsConstructor
public class PaymentGatewayCommands {

//    final PaymentControllerApiClient api;

//    @ShellMethod("Create PG Payment")
//    public String create_pg_payment() {
//        String paymentReference = "payment_" + System.currentTimeMillis();
//        String shopReference = "LIDL.payeeRef_1.001";
//        System.out.println(paymentReference + ":" + shopReference);
//
//        Instant start = Instant.now();
//
////        api.createPayment(
////                "111",
////                "cmopg",
////                paymentReference,
////                shopReference,
////                paymentDto()
////        );
////        paymentService.createPayment(paymentReference, shopReference);
//
//        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }
}

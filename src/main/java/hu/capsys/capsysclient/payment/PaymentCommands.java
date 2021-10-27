package hu.capsys.capsysclient.payment;

import hu.capsys.payment.PaymentApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.Duration;
import java.time.Instant;

import static hu.capsys.capsysclient.payment.PaymentDtoUtil.paymentDto;

@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentApiClient paymentApiClient;


    @ShellMethod("Create Payment")
    public String create_payment(String a) {
        System.out.println(a);
        String paymentReference = "payment_" + System.currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();
        paymentApiClient.createPayment(
                "111",
                "cmopg",
                paymentReference,
                shopReference,
                paymentDto()
        );
        long timeElapsed = Duration.between(start, Instant.now()).toMillis();
        return "Payment created. Time Elapsed: " + timeElapsed + " ms";
    }
}

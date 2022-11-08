package hu.capsys.shell.payment;

import hu.capsys.shell.statemachine.StateMachineService;
import hu.capsys.statemachine.api.CurrentStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentService paymentService;
    final StateMachineService stateMachineService;


    @ShellMethod("Update Payment")
    public String update_payment(String shopReference, String paymentReference) {
        Instant start = Instant.now();

        paymentService.updateStatus(paymentReference, shopReference, "RJCT");

        printCurrentState(paymentReference, shopReference);

        return format("Payment updated. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


//    @ShellMethod("Accept Payment flow")
//    public String accept_payment() {
//        String paymentReference = "payment_" + currentTimeMillis();
//        String shopReference = "LIDL.payeeRef_1.001";
//        System.out.println(paymentReference + ":" + shopReference);
//
//        Instant start = Instant.now();
//
//        ISOPaymentStatus status = paymentService.createPayment(paymentReference, shopReference).getPaymentStatus().getStatus();
//        System.out.printf("Created: %s%n", status);
//
//        status = paymentService.acceptPayment(paymentReference, shopReference).getStatus();
//        System.out.printf("Accepted: %s%n", status);
//
//        printCurrentState(paymentReference, shopReference);
//
//        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }
//
//    @ShellMethod("Cancel Payment flow")
//    public String cancel_payment() {
//        String paymentReference = "payment_" + currentTimeMillis();
//        String shopReference = "LIDL.payeeRef_1.001";
//        System.out.println(paymentReference + ":" + shopReference);
//
//        Instant start = Instant.now();
//
//        ISOPaymentStatus status = paymentService.createPayment(paymentReference, shopReference).getPaymentStatus().getStatus();
//        System.out.printf("Created: %s%n", status);
//
//        status = paymentService.cancelPayment(paymentReference, shopReference).getStatus();
//        System.out.printf("Canceled: %s%n", status);
//
//        printCurrentState(paymentReference, shopReference);
//
//        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }

    private void printCurrentState(String paymentReference, String shopReference) {
        CurrentStateDto state = paymentService.getState(paymentReference, shopReference)
                .blockLast();
        System.out.printf("Current state: %s%n", requireNonNull(state).getCurrentState());
    }
}

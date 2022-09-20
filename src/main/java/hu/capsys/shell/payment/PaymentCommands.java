package hu.capsys.shell.payment;

import hu.capsys.payment.model.ISOPaymentStatus;
import hu.capsys.shell.statemachine.StateMachineService;
import hu.capsys.statemachine.api.CurrentStateDto;
import hu.capsys.statemachine.api.StateMachineEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.Duration;
import java.time.Instant;

import static hu.capsys.payment.model.ISOPaymentStatus.ACSP;
import static hu.capsys.payment.model.ISOPaymentStatus.PMAT;
import static hu.capsys.payment.model.ISOPaymentStatus.RJCT;
import static hu.capsys.shell.statemachine.StateMachineService.PAYMENT_REFERENCE_VARIABLE_KEY;
import static hu.capsys.shell.statemachine.StateMachineService.SHOP_REFERENCE_VARIABLE_KEY;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentService paymentService;
    final StateMachineService stateMachineService;


    @ShellMethod("Create PP Payment")
    public String create_pp_payment() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();

        paymentService.createPayment(paymentReference, shopReference);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Update PP Payment")
    public String update_pp_payment(String shopReference, String paymentReference) {
        Instant start = Instant.now();

        paymentService.updateStatus(paymentReference, shopReference, RJCT);

        return format("Payment updated. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Accept Payment flow")
    public String accept_payment() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();

        ISOPaymentStatus status = paymentService.createPayment(paymentReference, shopReference).getPaymentStatus().getStatus();
        System.out.printf("Created: %s%n", status);

        status = paymentService.acceptPayment(paymentReference, shopReference).getStatus();
        System.out.printf("Accepted: %s%n", status);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }

    @ShellMethod("Cancel Payment flow")
    public String cancel_payment() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();

        ISOPaymentStatus status = paymentService.createPayment(paymentReference, shopReference).getPaymentStatus().getStatus();
        System.out.printf("Created: %s%n", status);

        status = paymentService.cancelPayment(paymentReference, shopReference).getStatus();
        System.out.printf("Canceled: %s%n", status);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }

    @ShellMethod("IpsNotif flow")
    public String ipsnotif() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();
        paymentService.createPayment(paymentReference, shopReference);
        printCurrentState(paymentReference, shopReference);
        paymentService.updateStatus(paymentReference, shopReference, PMAT);
//        paymentService.updateStatus(paymentReference, shopReference, ACSP);
        paymentService.updateStatus(paymentReference, shopReference, RJCT);
        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Create PP Payment IpsNotif")
    public String create_payment_ipsnotif() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();

        paymentService.updateStatus(paymentReference, shopReference, ACSP);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("IpsTraffic event")
    public String ipsTraffic() {
        String paymentReference = "payment_" + currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();
        paymentService.createPayment(paymentReference, shopReference);
        printCurrentState(paymentReference, shopReference);
        paymentService.updateStatus(paymentReference, shopReference, ACSP);
        printCurrentState(paymentReference, shopReference);

        stateMachineService.sendEvent(paymentReference, shopReference,
                        StateMachineEventRequest.builder()
                                .event("IpsTraffic")
                                .putVariable(SHOP_REFERENCE_VARIABLE_KEY, shopReference)
                                .putVariable(PAYMENT_REFERENCE_VARIABLE_KEY, paymentReference)
                                .putVariable("bankReconItemOriginalStatus", "ACWC")
                                .build())
                .blockLast();
        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    private void printCurrentState(String paymentReference, String shopReference) {
        CurrentStateDto state = paymentService.getState(paymentReference, shopReference)
                .blockLast();
        System.out.printf("Current state: %s%n", requireNonNull(state).getCurrentState());
    }
}

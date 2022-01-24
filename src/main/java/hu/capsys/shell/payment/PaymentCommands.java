package hu.capsys.shell.payment;

import hu.capsys.payment.model.ISOPaymentStatus;
import hu.capsys.statemachine.api.CurrentStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

import static hu.capsys.payment.model.ISOPaymentStatus.RJCT;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentService paymentService;


    @ShellMethod("Create PP Payment")
    public String create_payment() {
        String paymentReference = "payment_" + System.currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();

        paymentService.createPayment(paymentReference, shopReference);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Create loop PG Payment")
    public String loop_payment() throws InterruptedException, ExecutionException {
        long[] array = LongStream.range(0, 100)
                .map(x -> {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                    return System.currentTimeMillis();
                })
                .toArray();

        Instant start = Instant.now();

        ForkJoinPool customThreadPool = new ForkJoinPool(1);
        customThreadPool.submit(
                () -> Arrays.stream(array).parallel()
                        .forEach(i -> {
                            Instant s = Instant.now();
                            try {
                                String paymentReference = "payment_" + i;
                                String shopReference = "LIDL.payeeRef_1.001";
                                paymentService.createPayment(paymentReference, shopReference);
//                                paymentService.acceptPayment(paymentReference, shopReference);
                                paymentService.updateStatus(paymentReference, shopReference, RJCT);
                                CurrentStateDto currentStateDto = paymentService.getState(paymentReference, shopReference).blockLast();
                                System.out.printf("%d: %s (%d ms)\n", i, currentStateDto.getCurrentState(), Duration.between(s, Instant.now()).toMillis());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        })
        ).get();

        return format("Payments created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Accept Payment flow")
    public String accept_payment() {
        String paymentReference = "payment_" + System.currentTimeMillis();
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
        String paymentReference = "payment_" + System.currentTimeMillis();
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
        String paymentReference = "payment_" + System.currentTimeMillis();
        String shopReference = "LIDL.payeeRef_1.001";
        System.out.println(paymentReference + ":" + shopReference);

        Instant start = Instant.now();
        paymentService.createPayment(paymentReference, shopReference);
        paymentService.updateStatus(paymentReference, shopReference, RJCT);

        printCurrentState(paymentReference, shopReference);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    private void printCurrentState(String paymentReference, String shopReference) {
        CurrentStateDto state = paymentService.getState(paymentReference, shopReference)
                .blockLast();
        System.out.printf("Current state: %s%n", requireNonNull(state).getCurrentState());
    }
}

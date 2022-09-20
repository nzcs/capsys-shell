package hu.capsys.shell.paymentgateway;

import hu.capsys.shell.payment.PaymentService;
import hu.capsys.statemachine.api.CurrentStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

import static hu.capsys.payment.model.ISOPaymentStatus.ACSP;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

@ShellComponent
@RequiredArgsConstructor
public class PaymentGatewayCommands {

    final PaymentGatewayService pgService;
    final PaymentService paymentService;


    @ShellMethod("Create Payee")
    public String create_payee() {
        Instant start = Instant.now();

        pgService.loadPayee();
        pgService.loadTerminal();

        return format("Payee created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Create PG Payment")
    public String create_pg_payment(@ShellOption(defaultValue = "") String paymentReference) {
        paymentReference = paymentReference.isEmpty() ? "payment_" + currentTimeMillis() : paymentReference;
        System.out.println(paymentReference);

        Instant start = Instant.now();

        String shopRef = pgService.createPayment(paymentReference);
        System.out.println(shopRef);

//        paymentService.updateStatus(paymentReference, shopRef, RJCT);

        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


    @ShellMethod("Create loop PG Payment")
    public String loop_payment(int n, int parallelism) throws InterruptedException, ExecutionException {
        Instant start = Instant.now();
        long l = currentTimeMillis();

        ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
        customThreadPool.submit(
                () -> LongStream.range(0, n).parallel()
                        .forEach(i -> {
                            long pId = l + i;
                            Instant s = Instant.now();
                            try {
                                String paymentRef = "payment_" + pId;
                                String shopRef = pgService.createPayment(paymentRef);
                                paymentService.updateStatus(paymentRef, shopRef, ACSP);

                                CurrentStateDto currentStateDto = paymentService.getState(paymentRef, shopRef).blockLast();

                                assert currentStateDto != null;
                                System.out.printf("%s: %s (%d ms)\n", paymentRef, currentStateDto.getCurrentState(), Duration.between(s, Instant.now()).toMillis());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        })
        ).get();

        return format("Payments created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
    }


//    private void printCurrentState(String paymentReference, String shopReference) {
//        CurrentStateDto state = paymentService.getState(paymentReference, shopReference)
//                .blockLast();
//        System.out.printf("Current state: %s%n", requireNonNull(state).getCurrentState());
//    }
}

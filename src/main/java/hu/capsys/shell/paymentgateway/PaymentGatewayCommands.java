package hu.capsys.shell.paymentgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@RequiredArgsConstructor
public class PaymentGatewayCommands {

//    final PaymentGatewayService pgService;
//    final PaymentService paymentService;
//
//
//    @ShellMethod("Create Payee")
//    public String create_payee() {
//        Instant start = Instant.now();
//
//        pgService.loadPayee();
//        pgService.loadTerminal();
//
//        return format("Payee created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }
//
//
//    @ShellMethod("Create Payment")
//    public String create_payment(@ShellOption(defaultValue = "") String paymentReference) {
//        paymentReference = paymentReference.isEmpty() ? "payment_" + currentTimeMillis() : paymentReference;
//        System.out.println(paymentReference);
//
//        Instant start = Instant.now();
//
//        String shopRef = pgService.createPayment(paymentReference);
//        System.out.println(shopRef);
//
//        return format("Payment created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }
//
//
//    @ShellMethod("Create loop PG Payment")
//    public String loop_payment(int n, int parallelism) throws InterruptedException, ExecutionException {
//        Instant start = Instant.now();
//        long l = currentTimeMillis();
//
//        ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
//        customThreadPool.submit(
//                () -> LongStream.range(0, n).parallel()
//                        .forEach(i -> {
//                            long pId = l + i;
//                            Instant s = Instant.now();
//                            try {
//                                String paymentRef = "payment_" + pId;
//                                String shopRef = pgService.createPayment(paymentRef);
//                                paymentService.updateStatus(paymentRef, shopRef, "ACSP");
//
//                                CurrentStateDto currentStateDto = paymentService.getState(paymentRef, shopRef).blockLast();
//
//                                assert currentStateDto != null;
//                                System.out.printf("%s: %s (%d ms)\n", paymentRef, currentStateDto.getCurrentState(), Duration.between(s, Instant.now()).toMillis());
//                            } catch (Exception e) {
//                                System.out.println(e.getMessage());
//                            }
//                        })
//        ).get();
//
//        return format("Payments created. Time Elapsed: %d ms", Duration.between(start, Instant.now()).toMillis());
//    }

}

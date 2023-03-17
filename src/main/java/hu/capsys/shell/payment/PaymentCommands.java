package hu.capsys.shell.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentService paymentService;


    @ShellMethod("Update Payment")
    public String updatePayment(String payeeRef, String paymentRef) throws Exception {
        return paymentService.updatePayment(payeeRef, paymentRef).toString();
    }
}

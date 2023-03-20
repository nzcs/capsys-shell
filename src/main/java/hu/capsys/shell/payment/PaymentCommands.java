package hu.capsys.shell.payment;

import hu.capsys.gateway.payment_gateway.api.model.PaymentResponse1Dto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentStatusDto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentStatusResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static hu.capsys.shell.Util.print;
import static java.util.Objects.requireNonNull;


@ShellComponent
@RequiredArgsConstructor
public class PaymentCommands {

    final PaymentService paymentService;


    @ShellMethod("Create Payment")
    public void createPayment(String payeeRef, String terminalRef, String paymentRef) throws Exception {
        ResponseEntity<PaymentResponse1Dto> result = paymentService.createPayment(payeeRef, terminalRef, paymentRef);
        PaymentResponse1Dto p = requireNonNull(result.getBody());
        print("Create Payment", result.getStatusCode(), paymentRef, toString(p.getPaymentStatus()));
    }


    @ShellMethod("Update Payment")
    public void updatePayment(String payeeRef, String paymentRef) throws Exception {
        HttpStatus httpStatus = paymentService.updatePayment(payeeRef, paymentRef);
        print("Update Payment", httpStatus, paymentRef);
    }

    @ShellMethod("Update Payment PMAT")
    public void updatePayment_PMAT(String payeeRef, String paymentRef) throws Exception {
        HttpStatus httpStatus = paymentService.updatePayment_PMAT(payeeRef, paymentRef);
        print("Update Payment", httpStatus, paymentRef);
    }


    @ShellMethod("Accept Payment")
    public void acceptPayment(String payeeRef, String terminalRef, String paymentRef) {
        ResponseEntity<PaymentStatusResponse1Dto> result = paymentService.acceptPayment(payeeRef, terminalRef, paymentRef);
        PaymentStatusResponse1Dto s = requireNonNull(result.getBody());
        print("Update Payment", result.getStatusCode(), paymentRef, toString(s.getPaymentStatus()));
    }


    @ShellMethod("Get Payment Status")
    public void getPaymentStatus(String payeeRef, String terminalRef, String paymentRef) {
        ResponseEntity<PaymentStatusResponse1Dto> result = paymentService.getPaymentStatus(payeeRef, terminalRef, paymentRef);
        PaymentStatusResponse1Dto s = requireNonNull(result.getBody());
        print("Get Payment Status", result.getStatusCode(), paymentRef, toString(s.getPaymentStatus()));
    }


    public String toString(PaymentStatusDto s) {
        String str = s.getStatus().toString();
        if (s.getStatusReasonInformation() != null) {
            str += "(" + s.getStatusReasonInformation().getAdditionalInformation() + ")";
        }
        return str;
    }
}

package hu.capsys.shell;

import hu.capsys.gateway.masterdata.core.api.model.ApplicationUser1Dto;
import hu.capsys.gateway.masterdata.core.api.model.Partner1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeResponse1Dto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentResponse1Dto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentStatusResponse1Dto;
import hu.capsys.shell.masterdata.MasterDataService;
import hu.capsys.shell.payment.PaymentController;
import hu.capsys.shell.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static java.util.Objects.requireNonNull;


@ShellComponent
@RequiredArgsConstructor
public class Commands {

    final MasterDataService mdService;
    final PaymentService paymentService;

    String partnerRef = "A01";
    String userRef = "u1";
    String payeeRef = "dmo1";
    String terminalRef = "t1";
    String paymentRef;


    @ShellMethod("Full Test")
    public void test() throws Exception {
        paymentRef = "payment_" + System.currentTimeMillis();

        HttpStatus partnerStatus = mdService.loadPartner(partnerRef);
        Partner1Dto partner = mdService.getPartner(partnerRef);
        System.out.printf("Partner (%s): %s%n", partner.getPartnerReference(), partnerStatus);

        HttpStatus userStatus = mdService.loadUser(userRef, partnerRef);
        ApplicationUser1Dto user = mdService.getUser(userRef);
        System.out.printf("User (%s, %s): %s%n", user.getUserReference(), user.getPartnersToAccess(), userStatus);

        HttpStatus payeeStatus = mdService.loadPayee(payeeRef, partnerRef, userRef);
        mdService.loadTerminal(payeeRef, terminalRef);
        PayeeResponse1Dto payee = mdService.getPayee(payeeRef);
        System.out.printf(
                "Payee (%s, %s, %s, %s): %s%n",
                payee.getPayeeReference(),
                payee.getPartnerReference(),
                payee.getUsers(),
                payee.getShops().get(0).getTerminals().get(0).getTerminalReference(),
                payeeStatus);


        ResponseEntity<PaymentResponse1Dto> payment = paymentService.createPayment(payeeRef, terminalRef, paymentRef);
        PaymentResponse1Dto p = requireNonNull(payment.getBody());
        System.out.printf("Payment (%s %s): %s%n", paymentRef, p.getPaymentStatus().getStatus(), payment.getStatusCode());


        Flux<PaymentStatusResponse1Dto> updateResult = PaymentController.sink.asFlux();

        HttpStatus updateStatus = paymentService.updatePayment(payeeRef, paymentRef);
        System.out.printf("Update (%s): %s%n", paymentRef, updateStatus);

        updateResult
                .doOnNext(PaymentStatusResponse1Dto::getStatus)
                .blockLast(Duration.ofSeconds(10));
    }
}

package hu.capsys.shell.payment;

import hu.capsys.gateway.payment_gateway.api.model.PaymentStatusResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;


@RestController
@RequiredArgsConstructor
public class PaymentController {

    public static Sinks.Many<PaymentStatusResponse1Dto> sink = Sinks.many().multicast().directBestEffort();


    @PostMapping("/api/{X-Connected-System}/v1/payees/{payeeReference}/shops/{shopNumber}/payments/{paymentReference}/callback-status")
    public ResponseEntity<String> callback(@PathVariable(value = "X-Connected-System") String xConnectedSystem,
                                           @PathVariable("payeeReference") String payeeReference,
                                           @PathVariable("shopNumber") String shopNumber,
                                           @PathVariable("paymentReference") String paymentReference,
                                           @RequestBody PaymentStatusResponse1Dto paymentStatusResponse) {
        System.out.println(paymentStatusResponse.getPaymentStatus());
        sink.tryEmitNext(paymentStatusResponse);
        return ResponseEntity.ok("ACKN");
    }
}

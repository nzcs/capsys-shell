package hu.capsys.shell.payment;

import hu.capsys.payment.api.PaymentApiClient;
import hu.capsys.payment.api.model.PaymentAcceptViewDto;
import hu.capsys.payment.api.model.PaymentCancelViewDto;
import hu.capsys.statemachine.api.CurrentStateDto;
import hu.capsys.statemachine.api.GetStateDto;
import hu.capsys.statemachine.api.StateMachineClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static hu.capsys.shell.payment.PaymentDtoUtil.updateStatusDto;
import static hu.capsys.statemachine.model.StateMachineType.PAYMENT;

@Component
@RequiredArgsConstructor
public class PaymentService {

    final PaymentApiClient paymentApiClient;
    final StateMachineClient stateMachineClient;


//    PaymentResponse1Dto createPayment(String paymentReference, String shopReference) {
//        return paymentApiClient.createPayment(
//                "cmopg",
//                paymentReference,
//                shopReference,
//                paymentDto()
//        ).getBody();
//    }


    PaymentAcceptViewDto acceptPayment(String paymentReference, String shopReference) {
        return paymentApiClient.acceptPayment(
                "cmopg",
                paymentReference,
                shopReference
        ).getBody();
    }


    PaymentCancelViewDto cancelPayment(String paymentReference, String shopReference) {
        return paymentApiClient.cancelPayment(
                "cmopg",
                paymentReference,
                shopReference
        ).getBody();
    }


    public void updateStatus(String paymentReference, String shopReference, String status) {
        paymentApiClient.updateStatus(
                "cmopg",
                paymentReference,
                shopReference,
                updateStatusDto(status)
        );
    }


    public Flux<CurrentStateDto> getState(String paymentReference, String shopReference) {
        return stateMachineClient.getState(
                GetStateDto.builder()
                        .machineId(shopReference + ":" + paymentReference)
                        .type(PAYMENT.getValue())
                        .build()
        );
    }

}

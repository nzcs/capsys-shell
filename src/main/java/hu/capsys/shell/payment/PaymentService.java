package hu.capsys.shell.payment;

import hu.capsys.payment.PaymentApiClient;
import hu.capsys.payment.model.ISOPaymentStatus;
import hu.capsys.payment.model.PaymentAcceptViewDto;
import hu.capsys.payment.model.PaymentCancelViewDto;
import hu.capsys.payment.model.PaymentResponseDto;
import hu.capsys.statemachine.api.CurrentStateDto;
import hu.capsys.statemachine.api.GetStateDto;
import hu.capsys.statemachine.api.StateMachineClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static hu.capsys.shell.payment.PaymentDtoUtil.paymentDto;
import static hu.capsys.shell.payment.PaymentDtoUtil.updateStatusDto;
import static hu.capsys.statemachine.model.StateMachineType.PAYMENT;

@Component
@RequiredArgsConstructor
public class PaymentService {

    final PaymentApiClient paymentApiClient;
    final StateMachineClient stateMachineClient;


    PaymentResponseDto createPayment(String paymentReference, String shopReference) {
        return paymentApiClient.createPayment(
                "111",
                "cmopg",
                paymentReference,
                shopReference,
                paymentDto()
        ).getBody();
    }


    PaymentAcceptViewDto acceptPayment(String paymentReference, String shopReference) {
        return paymentApiClient.acceptPayment(
                "111",
                "cmopg",
                paymentReference,
                shopReference
        ).getBody();
    }


    PaymentCancelViewDto cancelPayment(String paymentReference, String shopReference) {
        return paymentApiClient.cancelPayment(
                "111",
                "cmopg",
                paymentReference,
                shopReference
        ).getBody();
    }


    public void updateStatus(String paymentReference, String shopReference, ISOPaymentStatus status) {
        paymentApiClient.updateStatus(
                "111",
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

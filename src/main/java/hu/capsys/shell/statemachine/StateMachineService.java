package hu.capsys.shell.statemachine;

import hu.capsys.statemachine.api.StateMachineClient;
import hu.capsys.statemachine.api.StateMachineEventRequest;
import hu.capsys.statemachine.api.StateMachineRequest;
import hu.capsys.statemachine.api.StateMachineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static hu.capsys.statemachine.model.StateMachineType.PAYMENT;

@Service
@RequiredArgsConstructor
public class StateMachineService {

    public static final String SHOP_REFERENCE_VARIABLE_KEY = "shopReference";
    public static final String PAYMENT_REFERENCE_VARIABLE_KEY = "paymentReference";

    final StateMachineClient stateMachineClient;


    public Flux<StateMachineResponse> sendEvent(String paymentReference, String shopReference, StateMachineEventRequest eventRequest) {
        return stateMachineClient.sendEvent(
                StateMachineRequest.builder()
                        .type(PAYMENT.getValue())
                        .machineId(createMachineId(shopReference, paymentReference))
                        .putEvent(eventRequest)
                        .build()
        );
    }


    public static String createMachineId(String shopReference, String paymentReference) {
        return shopReference + ":" + paymentReference;
    }
}

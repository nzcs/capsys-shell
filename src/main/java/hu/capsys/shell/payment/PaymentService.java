package hu.capsys.shell.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.capsys.connector.giro.api.GiroConnectorPaymentApiClient;
import hu.capsys.connector.giro.api.model.UpdateStatus1Dto;
import hu.capsys.gateway.payment_gateway.api.PaymentGatewayControllerApiClient;
import hu.capsys.gateway.payment_gateway.api.model.PaymentRequest1Dto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentResponse1Dto;
import hu.capsys.gateway.payment_gateway.api.model.PaymentStatusResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentService {

    final ObjectMapper mapper;
    final PaymentGatewayControllerApiClient paymentClient;
    final GiroConnectorPaymentApiClient giroClient;

    @Value("${xConnectedSystem}")
    String xConnectedSystem;
    @Value("${platformReference}")
    String platformReference;
    @Value("classpath:objects/payment.json")
    Resource paymentJson;
    @Value("classpath:objects/updateStatus.json")
    Resource updateStatusJson;


    public ResponseEntity<PaymentResponse1Dto> createPayment(String payeeRef, String terminalRef, String paymentRef) throws IOException {
        PaymentRequest1Dto p = mapper.readValue(paymentJson.getFile(), PaymentRequest1Dto.class);
        p.getPaymentInfo().setPlatformReference(platformReference);
        return paymentClient.createPayment(xConnectedSystem, payeeRef, "001", terminalRef, paymentRef, p, UUID.randomUUID().toString());
    }


    public HttpStatus updatePayment(String payeeRef, String paymentRef) throws IOException {
        UpdateStatus1Dto dto = mapper.readValue(updateStatusJson.getFile(), UpdateStatus1Dto.class);
        return giroClient.updateStatus(xConnectedSystem, paymentRef, "001." + payeeRef + "." + platformReference, dto).getStatusCode();
    }


    public PaymentStatusResponse1Dto getPaymentStatus(String payeeRef, String terminalRef, String paymentRef, int timeout) {
        return paymentClient.getPaymentStatus(xConnectedSystem, payeeRef, "001", terminalRef, paymentRef, UUID.randomUUID().toString(), timeout).getBody();
    }
}

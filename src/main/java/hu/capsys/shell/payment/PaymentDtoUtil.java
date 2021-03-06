package hu.capsys.shell.payment;

import hu.capsys.payment.model.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static hu.capsys.payment.model.PaymentInitMethodType.INSTORE_PAYEE_QR;
import static java.math.BigDecimal.TEN;

public class PaymentDtoUtil {

    public static PaymentRequestDto paymentDto() {
        return new PaymentRequestDto()
                .requestedQRType("SMARTQR")
                .paymentInfo(newPaymentInfoDto(TEN))
                .payeeInfo(newPayeeInfoDto())
                .paymentInitMethod(INSTORE_PAYEE_QR)
                .terminalDeviceInfo("Test")
                .qrPicRequest(false);
    }

    public static PaymentInfoDto newPaymentInfoDto(BigDecimal amount) {
        return new PaymentInfoDto()
                .transactionAmount(amount)
                .transactionCurrency("HUF")
                .creationDateTime(ZonedDateTime.now())
                .expiryDateTime(ZonedDateTime.now())
                .terminalReference("LIDL-BP-001")
                .mobileAppURL("cap/lidl/001")
                .remittanceInfo("Teszt")
                .invoiceReference("100-001")
                .customerReference("001")
                .loyaltyReference("001")
                .navCheckReference("123456789-1-23");
    }

    public static PayeeInfoDto newPayeeInfoDto() {
        return new PayeeInfoDto()
                .name("Kovács István")
                .accountNumber("HU123-111111111-2222222")
                .bic("HU987");
    }

    public static UpdateStatusDto updateStatusDto(ISOPaymentStatus status) {
        return new UpdateStatusDto()
                .paymentInfo(
                        newPaymentInfoDto(TEN)
                                .mobileAppURL(null))
                .payeeInfo(newPayeeInfoDto())
                .payerInfo(new PayerInfoDto())
                .paymentStatus(new PaymentStatusDto()
                        .status(status)
                        .statusReasonInformation(
                                new StatusReasonInformationDto()
                                        .additionalInformation("bla-bla")
                                        .reason(new StatusReasonDto()
                                                .code("AC03"))
                        )
                );
    }
}

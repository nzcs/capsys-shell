package hu.capsys.shell.payment;

import hu.capsys.payment.api.model.CreditTransferInfoDto;
import hu.capsys.payment.api.model.PayeeInfo1Dto;
import hu.capsys.payment.api.model.PayerInfo1Dto;
import hu.capsys.payment.api.model.PaymentInfo2Dto;
import hu.capsys.payment.api.model.PaymentInfoDto;
import hu.capsys.payment.api.model.StatusDto;
import hu.capsys.payment.api.model.UpdateStatus1Dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static java.math.BigDecimal.TEN;

public class PaymentDtoUtil {

//    public static PaymentRequest1Dto paymentDto() {
//        return new PaymentRequest1Dto()
//                .requestedQRType("SMARTQR")
//                .paymentInfo(newPaymentInfoDto(TEN))
//                .payeeInfo(newPayeeInfoDto())
//                .paymentInitMethod(INSTORE_PAYEE_QR)
//                .terminalDeviceInfo("Test")
//                .qrPicRequest(false);
//    }

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

    public static PayeeInfo1Dto newPayeeInfoDto() {
        return new PayeeInfo1Dto()
                .name("Kovács István")
                .accountNumber("HU123-111111111-2222222")
                .bic("HU987");
    }

    public static UpdateStatus1Dto updateStatusDto(String status) {
        return new UpdateStatus1Dto()
                .paymentInfo(newPaymentInfo2Dto(TEN))
                .payeeInfo(newPayeeInfoDto())
                .payerInfo(new PayerInfo1Dto())
                .creditTransferInfo(new CreditTransferInfoDto()
                        .lastChangeDateTime(ZonedDateTime.now())
                        .creationDateTime(ZonedDateTime.now())
                        .originalMessageId("originalMessageId")
                        .messageId("messageId")
                        .achInvolved(false)
                        .creditTransferOriginalCreationDateTime(ZonedDateTime.now())
                        .endToEndId("endToEndId")
                        .status(new StatusDto()
                                .transactionStatus(status)
                        )
                );
    }

    public static PaymentInfo2Dto newPaymentInfo2Dto(BigDecimal amount) {
        return new PaymentInfo2Dto()
                .transactionAmount(amount)
                .transactionCurrency("HUF")
                .creationDateTime(ZonedDateTime.now())
                .expiryDateTime(ZonedDateTime.now())
                .terminalReference("LIDL-BP-001")
//                .mobileAppURL("cap/lidl/001")
                .remittanceInfo("Teszt")
                .invoiceReference("100-001")
                .customerReference("001")
                .loyaltyReference("001")
                .navCheckReference("123456789-1-23");
    }

}

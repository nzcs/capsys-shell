package hu.capsys.shell.paymentgateway;

import hu.capsys.paymentgateway.model.AccountDto;
import hu.capsys.paymentgateway.model.AddressDto;
import hu.capsys.paymentgateway.model.ElectronicContactDto;
import hu.capsys.paymentgateway.model.LoadTerminalDeviceType;
import hu.capsys.paymentgateway.model.LoadTerminalRequestDto;
import hu.capsys.paymentgateway.model.PayeeDto;
import hu.capsys.paymentgateway.model.PayeeInfo1Dto;
import hu.capsys.paymentgateway.model.PayeeStatus;
import hu.capsys.paymentgateway.model.PaymentInfoDto;
import hu.capsys.paymentgateway.model.PaymentRequest1Dto;
import hu.capsys.paymentgateway.model.PhoneNumberDto;
import hu.capsys.paymentgateway.model.ShopDto;
import hu.capsys.paymentgateway.model.ShopStatus;
import hu.capsys.paymentgateway.model.SubscriptionDto;
import hu.capsys.paymentgateway.model.SubscriptionStatus;
import hu.capsys.paymentgateway.model.TechnicalDeviceInfoDto;
import hu.capsys.paymentgateway.model.TerminalStatus;

import java.time.ZonedDateTime;
import java.util.List;

import static hu.capsys.paymentgateway.model.PaymentInitMethodType.INSTORE_PAYEE_QR;
import static hu.capsys.paymentgateway.model.PhoneNumberType.MOBILE;
import static java.math.BigDecimal.TEN;

public class PaymentGatewayUtil {

    public static final String payeeName = "Szolaris Gesztenye";
    public static final String shopNumber = "shop-num";
    public static final String platformReference = "cmopay";
    public static final String connectedSystem = "cmopg";
    public static final String payeeReference = "payee-ref2";
    public static final String terminalReference = "terminal-ref";


    static PaymentRequest1Dto paymentRequestDto() {
        return new PaymentRequest1Dto()
                .paymentInfo(paymentInfo())
                .payeeInfo(payeeInfoDto())
                .paymentInitMethod(INSTORE_PAYEE_QR)
                .terminalDeviceInfo("Test")
                .qrPicRequest(false)
                .unprotectedFields("");
    }

    static PaymentInfoDto paymentInfo() {
        return new PaymentInfoDto()
                .transactionAmount(TEN)
                .transactionCurrency("HUF")
                .creationDateTime(ZonedDateTime.now())
                .expiryDateTime(ZonedDateTime.now())
                .mobileAppURL("https://bankbitts.com/hct/?")
                .remittanceInfo("Teszt")
                .invoiceReference("1234")
                .customerReference("001")
                .loyaltyReference("001")
                .navCheckReference("123456789-1-23")
                .platformReference(platformReference);
    }

    static PayeeInfo1Dto payeeInfoDto() {
        return new PayeeInfo1Dto()
                .name(payeeName)
                .accountNumber("HU27182032911004412510441250")
                .bic("OTPVHUHB");
    }


    static PayeeDto payeeDto() {
        return new PayeeDto()
                .status(PayeeStatus.ACTIVE)
                .referalAgent("REF999")
                .legalForm("LIMITED_LIABILITY_COMPANY")
                .name(payeeName)
                .companyRegistrationNumber("12-34-123567")
                .address(new AddressDto()
                        .country("HU")
                        .postalCode("6723")
                        .city("Szeged")
                        .streetName("Csuka utca 1")
                        .streetNumber("1")
                        .streetType("."))
                .phoneNumber(new PhoneNumberDto()
                        .countryCode("06")
                        .prefix("70")
                        .lineNumber("1234567")
                        .phoneNumberType(MOBILE))
                .electronicContact(new ElectronicContactDto().electronicContact("joe@black.com"))
                .shops(List.of(new ShopDto()
                        .number(shopNumber)
                        .name("CBA111")
                        .status(ShopStatus.ACTIVE)))
                .subscriptions(List.of(new SubscriptionDto()
                        .status(SubscriptionStatus.ACTIVE)
                        .platformReference(platformReference)
                        .validity(ZonedDateTime.now())
                        .reference("subscription-ref")))
                .account(new AccountDto()
                        .bic("OTPVHUHB")
                        .iban("HU27182032911004412510441250")
                        .currency("HUF"));
    }


    static LoadTerminalRequestDto terminalRequestDto() {
        return new LoadTerminalRequestDto()
                .status(TerminalStatus.ACTIVE)
                .deviceInfo(new TechnicalDeviceInfoDto()
                        .deviceType(LoadTerminalDeviceType.TECHNICALINVOICINGAPP)
                        .osType("OsType")
                        .osVersion("1.7"));
    }
}

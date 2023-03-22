package hu.capsys.shell.masterdata;

import hu.capsys.gateway.masterdata.core.api.model.ApplicationUser1Dto;
import hu.capsys.gateway.masterdata.core.api.model.Partner1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeResponse1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeResponseShop1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeResponseTerminal1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.Subscription2Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

import static hu.capsys.shell.Util.print;


@ShellComponent
@RequiredArgsConstructor
public class MasterDataCommands {

    final MasterDataService service;


    @ShellMethod("Load Partner")
    public void loadPartner(String partnerRef) throws Exception {
        HttpStatus httpStatus = service.loadPartner(partnerRef);
        Partner1Dto partner = service.getPartner(partnerRef);
        print("Load Partner", httpStatus, partner.getPartnerReference());
    }


    @ShellMethod("Load User")
    public void loadUser(String userRef, String... partners) throws Exception {
        HttpStatus httpStatus = service.loadUser(userRef, partners);
        ApplicationUser1Dto user = service.getUser(userRef);
        print("Load User", httpStatus, user.getUserReference(), user.getPartnersToAccess());
    }


    @ShellMethod("Load Payee")
    public void loadPayee(String payeeRef, String partnerRef, String... users) throws Exception {
        HttpStatus httpStatus = service.loadPayee(payeeRef, partnerRef, users);
        PayeeResponse1Dto payee = service.getPayee(payeeRef);
        print("Load Payee", httpStatus,
                payee.getPayeeReference(),
                payee.getPartnerReference(),
                payee.getUsers(),
                getTerminalReference(payee),
                getSubscription(payee));
    }


    @ShellMethod("Load Terminal")
    public void loadTerminal(String payeeRef, String terminalRef) throws Exception {
        HttpStatus httpStatus = service.loadTerminal(payeeRef, terminalRef);
        PayeeResponse1Dto payee = service.getPayee(payeeRef);
        print("Load Terminal", httpStatus,
                payee.getPayeeReference(),
                payee.getPartnerReference(),
                payee.getUsers(),
                getTerminalReference(payee),
                getSubscription(payee));
    }


    String getSubscription(PayeeResponse1Dto payee) {
        List<Subscription2Dto> subscriptions = payee.getSubscriptions();
        if (subscriptions.isEmpty()) {
            return "subscriptions is empty";
        }
        return subscriptions.get(0).getSubscriptionReference() + ":" + subscriptions.get(0).getValidity();
    }

    String getTerminalReference(PayeeResponse1Dto payee) {
        List<PayeeResponseShop1Dto> shops = payee.getShops();
        if (shops.isEmpty()) {
            return "shops is empty";
        }
        List<PayeeResponseTerminal1Dto> terminals = shops.get(0).getTerminals();
        if (terminals.isEmpty()) {
            return "terminals is empty";
        }
        return terminals.get(0).getTerminalReference();
    }
}

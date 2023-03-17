package hu.capsys.shell.masterdata;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
@RequiredArgsConstructor
public class MasterDataCommands {

    final MasterDataService service;


    @ShellMethod("Load Partner")
    public String loadPartner(String partnerRef) throws Exception {
        return service.loadPartner(partnerRef).toString();
    }


    @ShellMethod("Load User")
    public String loadUser(String userRef, String... partners) throws Exception {
        return service.loadUser(userRef, partners).toString();
    }


    @ShellMethod("Load Payee")
    public String loadPayee(String payeeRef, String partnerRef, String... users) throws Exception {
        return service.loadPayee(payeeRef, partnerRef, users).toString();
    }


    @ShellMethod("Load Terminal")
    public String loadTerminal(String payeeRef, String terminalRef) throws Exception {
        return service.loadTerminal(payeeRef, terminalRef).toString();
    }

}

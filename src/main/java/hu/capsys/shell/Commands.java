package hu.capsys.shell;

import hu.capsys.shell.masterdata.MasterDataCommands;
import hu.capsys.shell.payment.PaymentCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
@RequiredArgsConstructor
public class Commands {

    final MasterDataCommands masterData;
    final PaymentCommands payment;

    String partnerRef = "A01";
    String userRef = "u1";
    String payeeRef = "dmo1";
    String terminalRef = "t1";


    @ShellMethod("Test MasterData")
    public void testMasterData() throws Exception {
        masterData.loadPartner(partnerRef);
        masterData.loadUser(userRef, partnerRef);
        masterData.loadPayee(payeeRef, partnerRef, userRef);
        masterData.loadTerminal(payeeRef, terminalRef);
    }


    @ShellMethod("Test Update Payment")
    public void testAcceptPayment() throws Exception {
        String paymentRef = "p_" + System.currentTimeMillis();

        payment.createPayment(payeeRef, terminalRef, paymentRef);
        payment.acceptPayment(payeeRef, terminalRef, paymentRef);
    }


    @ShellMethod("Test Update Payment")
    public void testPayment_ACCC() throws Exception {
        String paymentRef = "p_" + System.currentTimeMillis();

        payment.createPayment(payeeRef, terminalRef, paymentRef);
        payment.updatePayment(payeeRef, paymentRef);
        payment.getPaymentStatus(payeeRef, terminalRef, paymentRef);
    }


    @ShellMethod("Test Update Payment PMAT")
    public void testPayment_PMAT() throws Exception {
        String paymentRef = "p_" + System.currentTimeMillis();

        payment.createPayment(payeeRef, terminalRef, paymentRef);
        payment.updatePayment_PMAT(payeeRef, paymentRef);
        payment.getPaymentStatus(payeeRef, terminalRef, paymentRef);
    }
}

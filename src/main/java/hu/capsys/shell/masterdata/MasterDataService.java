package hu.capsys.shell.masterdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.capsys.gateway.masterdata.core.api.ApplicationUserBoControllerApiClient;
import hu.capsys.gateway.masterdata.core.api.PartnerBoControllerApiClient;
import hu.capsys.gateway.masterdata.core.api.model.ApplicationUser1Dto;
import hu.capsys.gateway.masterdata.core.api.model.ApplicationUser2Dto;
import hu.capsys.gateway.masterdata.core.api.model.Partner1Dto;
import hu.capsys.gateway.masterdata.core.api.model.Partner2Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.PayeeBoControllerApiClient;
import hu.capsys.gateway.masterdata.plugin.cpp.api.TerminalBoControllerApiClient;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.LoadTerminalRequestDto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.Payee1Dto;
import hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeResponse1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static hu.capsys.gateway.masterdata.plugin.cpp.api.model.PayeeDataFilterOptions.TERMINALS;

@Service
@RequiredArgsConstructor
public class MasterDataService {

    final ObjectMapper mapper;
    final PartnerBoControllerApiClient partnerClient;
    final ApplicationUserBoControllerApiClient userClient;
    final PayeeBoControllerApiClient payeeClient;
    final TerminalBoControllerApiClient terminalClient;

    @Value("${xConnectedSystem}")
    String xConnectedSystem;
    @Value("classpath:objects/partner.json")
    Resource partnerJson;
    @Value("classpath:objects/user.json")
    Resource userJson;
    @Value("classpath:objects/payee.json")
    Resource payeeJson;
    @Value("classpath:objects/terminal.json")
    Resource terminalJson;


    public HttpStatus loadPartner(String partnerRef) throws IOException {
        Partner2Dto partner = mapper.readValue(partnerJson.getFile(), Partner2Dto.class);
        return partnerClient.loadPartner(xConnectedSystem, partnerRef, partner, UUID.randomUUID().toString()).getStatusCode();
    }

    public Partner1Dto getPartner(String partnerRef) {
        return partnerClient.getPartner(xConnectedSystem, partnerRef, UUID.randomUUID().toString()).getBody();
    }


    public HttpStatus loadUser(String userRef, String... partners) throws IOException {
        ApplicationUser2Dto user = mapper.readValue(userJson.getFile(), ApplicationUser2Dto.class);
        user.setUserReference(userRef);
        user.setPartnersToAccess(Arrays.stream(partners).toList());
        return userClient.loadApplicationUser(xConnectedSystem, userRef, user, UUID.randomUUID().toString()).getStatusCode();
    }

    public ApplicationUser1Dto getUser(String userRef) {
        return userClient.getApplicationUsers(xConnectedSystem, userRef, UUID.randomUUID().toString()).getBody();
    }


    public HttpStatus loadPayee(String payeeRef, String partnerRef, String... users) throws IOException {
        Payee1Dto payee = mapper.readValue(payeeJson.getFile(), Payee1Dto.class);
        payee.setPayeeReference(payeeRef);
        payee.setPartnerReference(partnerRef);
        payee.setUsers(Arrays.stream(users).toList());
        return payeeClient.loadPayee(xConnectedSystem, payeeRef, payee, UUID.randomUUID().toString()).getStatusCode();
    }

    public PayeeResponse1Dto getPayee(String payeeRef) {
        return payeeClient.getPayee(xConnectedSystem, payeeRef, UUID.randomUUID().toString(), List.of(TERMINALS)).getBody();
    }


    public HttpStatus loadTerminal(String payeeRef, String terminalRef) throws IOException {
        LoadTerminalRequestDto t = mapper.readValue(terminalJson.getFile(), LoadTerminalRequestDto.class);
        return terminalClient.loadTerminal(xConnectedSystem, payeeRef, "001", terminalRef, t, UUID.randomUUID().toString()).getStatusCode();
    }
}

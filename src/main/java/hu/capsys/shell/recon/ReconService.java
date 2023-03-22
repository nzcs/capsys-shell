package hu.capsys.shell.recon;

import hu.capsys.gateway.recon.api.ErrorItemControllerApiClient;
import hu.capsys.gateway.recon.api.model.ErrorItemDto;
import hu.capsys.gateway.recon.api.model.ErrorItemFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReconService {

    final ErrorItemControllerApiClient errorItemClient;

    @Value("${xConnectedSystem}")
    String xConnectedSystem;


    public ResponseEntity<List<ErrorItemDto>> listErrorItems() {
        ErrorItemFilterDto filter = ErrorItemFilterDto.builder().build();
        return errorItemClient.listErrorItems(xConnectedSystem, filter, UUID.randomUUID().toString());
    }
}

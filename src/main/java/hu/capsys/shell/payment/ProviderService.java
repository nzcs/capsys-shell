package hu.capsys.shell.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.capsys.gateway.payment_gateway.api.ProviderBoControllerApiClient;
import hu.capsys.gateway.payment_gateway.api.model.Provider1Dto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class ProviderService {

    final ObjectMapper mapper;
    final ProviderBoControllerApiClient providerClient;

    @Value("${xConnectedSystem}")
    String xConnectedSystem;
    @Value("${platformReference}")
    String platformReference;
    @Value("classpath:objects/provider.json")
    Resource providerJson;


    public HttpStatus loadProvider() throws IOException {
        Provider1Dto dto = mapper.readValue(providerJson.getFile(), Provider1Dto.class);
        dto.setPlatformReference(platformReference);
        return providerClient.loadProvider(xConnectedSystem, dto, UUID.randomUUID().toString()).getStatusCode();
    }


    public List<Provider1Dto> getProviders() {
        return providerClient.getProviders(xConnectedSystem, UUID.randomUUID().toString()).getBody();
    }
}

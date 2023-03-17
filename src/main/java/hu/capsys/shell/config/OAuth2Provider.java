package hu.capsys.shell.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;


@Service
@RequiredArgsConstructor
public class OAuth2Provider {


    public static final Authentication ANONYMOUS_USER_AUTHENTICATION =
            new AnonymousAuthenticationToken(
                    "key",
                    "anonymous",
                    createAuthorityList("ROLE_ANONYMOUS")
            );


    final OAuth2AuthorizedClientManager authorizedClientManager;


    public String getAuthenticationToken(String partnerName) {
        final OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId(partnerName)
                .principal(ANONYMOUS_USER_AUTHENTICATION)
                .build();

        String token = requireNonNull(authorizedClientManager.authorize(request))
                .getAccessToken()
                .getTokenValue();

        return "Bearer " + token;
    }
}

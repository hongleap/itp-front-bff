package hongleap.ebanking.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        Boolean isAuthenticated
) {
}

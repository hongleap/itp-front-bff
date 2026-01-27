package hongleap.ebanking.controller;

import hongleap.ebanking.dto.AuthenticationResponse;
import hongleap.ebanking.dto.ProfileResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static reactor.netty.http.HttpConnectionLiveness.log;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

//    @GetMapping("/me")
//    public ProfileResponse me(@AuthenticationPrincipal OAuth2User principal) {
//        System.out.println(principal);
//        System.out.println(principal.getName());
//        return ProfileResponse.builder()
//                .username(principal.getName())
//                .fullName("ISTAD")
//                .build();
//    }

    @GetMapping("/is-authenticated")
    public AuthenticationResponse isAuthenticated(Authentication authentication) {
        return AuthenticationResponse.builder()
                .isAuthenticated(authentication != null)
                .build();
    }

    @GetMapping("/me")
    public ProfileResponse me(@AuthenticationPrincipal OidcUser oidcUser) {

//        You can use OAuth2User also

        log.info("OIDC USER : {}", oidcUser);
//        log.info("USER INFO : {}", oidcUser.getUserInfo());
//        log.info("FAMILY NAME : {}", oidcUser.getUserInfo().getFamilyName());

        List<String> rolesList = oidcUser.getAttribute("roles");
        Set<String> roles = rolesList != null ? new HashSet<>(rolesList) : new HashSet<>();

        List<String> permissionsList = oidcUser.getAttribute("permissions");
        Set<String> permissions = permissionsList != null ? new HashSet<>(permissionsList) : new HashSet<>();

        return ProfileResponse.builder()
                .username(oidcUser.getName())
                .email(oidcUser.getEmail())
                .familyName(oidcUser.getFamilyName())
                .givenName(oidcUser.getGivenName())
                .phoneNumber(oidcUser.getPhoneNumber())
                .gender(oidcUser.getGender())
                .birthdate(oidcUser.getBirthdate())
                .picture(oidcUser.getPicture())
                .coverImage(oidcUser.getAttribute("cover_image"))
                .roles(roles)
                .permission(permissions)
                .build();
    }
}

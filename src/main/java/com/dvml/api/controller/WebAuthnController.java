package com.dvml.api.controller;


import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.StartAssertionOptions;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.PublicKeyCredentialRequestOptions;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import com.yubico.webauthn.data.UserIdentity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/webauthn")
public class WebAuthnController {

    /*
    private final RelyingParty relyingParty = new RelyingParty(
        RelyingPartyIdentity.builder()
            .id("localhost") // ou seu domínio
            .name("Hospitalar App")
            .build(),
        Collections.emptyList()
    );

    public WebAuthnController() {
    }

    @PostMapping("/register/options")
    public PublicKeyCredentialCreationOptions startRegistration(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        UserIdentity user = UserIdentity.builder()
            .name(username)
            .displayName(username)
            .id(ByteArray.fromBase64Url(username.getBytes(StandardCharsets.UTF_8)))
            .build();

        return relyingParty.startRegistration(
            StartRegistrationOptions.builder()
                .user(user)
                .build()
        ).getPublicKeyCredentialCreationOptions();
    }

    @PostMapping("/register/finish")
    public ResponseEntity<?> finishRegistration(@RequestBody Map<String, Object> body) {
        // Implementar validação e salvamento do autenticador
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login/options")
    public PublicKeyCredentialRequestOptions startLogin(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        return relyingParty.startAssertion(
            StartAssertionOptions.builder()
                .username(username)
                .build()
        ).getPublicKeyCredentialRequestOptions();
    }

    @PostMapping("/login/finish")
    public ResponseEntity<?> finishLogin(@RequestBody Map<String, Object> body) {
        // Implementar validação da resposta do frontend
        return ResponseEntity.ok().build();
    }

     */
}
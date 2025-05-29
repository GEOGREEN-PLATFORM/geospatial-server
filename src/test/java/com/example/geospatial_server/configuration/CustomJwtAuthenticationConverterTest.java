package com.example.geospatial_server.configuration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CustomJwtAuthenticationConverterTest {

    private CustomJwtAuthenticationConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CustomJwtAuthenticationConverter();
    }

    private Jwt createJwt(Map<String, Object> claims) {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user")
                .claims(c -> c.putAll(claims))
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();
        return jwt;
    }

    @Test
    void whenResourceAccessWithRoles_thenAddsRoleAuthorities() {
        Map<String, Object> rolesMap = new HashMap<>();
        rolesMap.put("roles", List.of("admin", "user"));
        Map<String, Object> resourceAccess = Collections.singletonMap("user-client", rolesMap);
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", "read");
        claims.put("resource_access", resourceAccess);

        Jwt jwt = createJwt(claims);
        Authentication auth = converter.convert(jwt);
        Set<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authorities)
                .contains("SCOPE_read", "ROLE_ADMIN", "ROLE_USER");
    }

    @Test
    void whenUserClientWithoutRoles_thenNoExtraRoles() {
        Map<String, Object> userClient = Collections.emptyMap();
        Map<String, Object> resourceAccess = Collections.singletonMap("user-client", userClient);
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", "read");
        claims.put("resource_access", resourceAccess);

        Jwt jwt = createJwt(claims);
        Authentication auth = converter.convert(jwt);
        Set<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authorities).containsExactly("SCOPE_read");
    }

    @Test
    void whenRolesContainMixedCase_thenAuthorityIsUppercasedWithPrefix() {
        Map<String, Object> rolesMap = new HashMap<>();
        rolesMap.put("roles", List.of("AdminRole", "viewer"));
        Map<String, Object> resourceAccess = Collections.singletonMap("user-client", rolesMap);
        Map<String, Object> claims = new HashMap<>();
        claims.put("scope", "read");
        claims.put("resource_access", resourceAccess);

        Jwt jwt = createJwt(claims);
        Authentication auth = converter.convert(jwt);
        Set<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authorities)
                .contains("SCOPE_read", "ROLE_ADMINROLE", "ROLE_VIEWER");
    }

    @Test
    void whenJwtGrantedAuthoritiesConverterProvidesOtherAuthorities_thenCombined() {
        Map<String, Object> claims = new HashMap<>();
        // simulate default converter picks client roles from 'scope'
        claims.put("scope", "write");
        Map<String, Object> rolesMap = Map.of("roles", List.of("op"));
        claims.put("resource_access", Collections.singletonMap("user-client", rolesMap));

        Jwt jwt = createJwt(claims);
        Authentication auth = converter.convert(jwt);

        Set<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authorities)
                .containsExactlyInAnyOrder("SCOPE_write", "ROLE_OP");
    }
}
package com.aidanwhiteley.springbootadmin;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtHeaderProvider implements HttpHeadersProvider {

    public static final String JWT_COOKIE_NAME = "CLOUDY-JWT";

    @Value("${jwt.token.actuator.user}")
    private String jwtTokenActuatorUser;

    @Override
    public HttpHeaders getHeaders(Instance instance) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, JWT_COOKIE_NAME + "=" + jwtTokenActuatorUser);
        return headers;
    }
}

package com.bibliomania.BiblioMania.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

	@Value("${jwt.secret}")
	private String secret;

    public String getSecret() {
        return secret;
    }
}

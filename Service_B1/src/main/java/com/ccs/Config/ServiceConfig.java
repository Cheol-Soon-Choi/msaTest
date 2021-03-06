package com.ccs.Config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Getter
public class ServiceConfig {

    @Value("${example.property}")
    private String exampleProperty;

    @Value("${signing.key}")
    private String jwtSigningKey;
}

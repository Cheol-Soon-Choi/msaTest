package com.ccs.Clients;

import com.ccs.Model.Organization;
import com.ccs.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    public Organization getOrganization(String organizationId){
        logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://localhost:8081/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}

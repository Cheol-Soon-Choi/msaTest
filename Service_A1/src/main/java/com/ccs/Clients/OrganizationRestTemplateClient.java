package com.ccs.Clients;

import brave.Tracer;
import com.ccs.Model.Organization;
import com.ccs.Repository.OrganizationRedisRepository;
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

    @Autowired
    OrganizationRedisRepository orgRedisRepo;

    @Autowired
    Tracer tracer;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    private Organization checkRedisCache(String organizationId) {
        brave.Span newSpan = tracer.nextSpan().name("readLicensingDataFromRedis");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            return orgRedisRepo.findOrganization(organizationId);
        } catch (Exception ex) {
            logger.error("***** Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
            return null;
        } finally {
            newSpan.tag("peer.service", "redis");
            newSpan.annotate("cr");
            newSpan.finish();
        }
    }

    private void cacheOrganizationObject(Organization org) {
        try {
            orgRedisRepo.saveOrganization(org);
        } catch (Exception ex) {
            logger.error("***** Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
        }
    }


    public Organization getOrganization(String organizationId) {
        logger.debug("***** In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());

        Organization org = checkRedisCache(organizationId);

        if (org != null) {
            logger.debug("***** I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        logger.debug("***** Unable to locate organization from the redis cache: {}.", organizationId);

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://localhost:8081/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        /*Save the record from cache*/
        org = restExchange.getBody();

        if (org != null) {
            cacheOrganizationObject(org);
        }

        return restExchange.getBody();
    }
}

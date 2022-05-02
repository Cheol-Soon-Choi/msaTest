package com.ccs.event.handlers;

import com.ccs.Repository.OrganizationRedisRepository;
import com.ccs.event.CustomChannels;
import com.ccs.event.models.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(CustomChannels.class)
public class OrganizationChangeHandler {

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    @StreamListener("inboundOrgChanges") // 메시지가 입력채널에서 수신될 때마다 메서드 수행 output->input
    public void loggerSink(OrganizationChangeModel orgChange) {
        logger.debug("***** Received a message of type " + orgChange.getType());
        switch (orgChange.getAction()) {
            case "GET":
                logger.debug("***** Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "SAVE":
                logger.debug("***** Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "UPDATE":
                logger.debug("***** Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            case "DELETE":
                logger.debug("***** Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            default:
                logger.error("***** Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
                break;
        }
    }
}

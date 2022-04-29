package com.ccs.Service;

import com.ccs.Model.Organization;
import com.ccs.Model.OrganizationRepository;
import com.ccs.event.soruce.SimpleSourceBean;
import com.ccs.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final SimpleSourceBean simpleSourceBean;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    public Organization getOrg(String organizationId) {

        logger.debug("OrganizationService.getOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return organizationRepository.findById(organizationId).orElseThrow(EntityNotFoundException::new);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        organizationRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    public void updateOrg(Organization org){
        organizationRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());

    }

    public void deleteOrg(String  orgId){
        organizationRepository.deleteById( orgId );
        simpleSourceBean.publishOrgChange("DELETE", orgId);
    }
}

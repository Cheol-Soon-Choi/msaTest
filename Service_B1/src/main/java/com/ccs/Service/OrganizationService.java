package com.ccs.Service;

import com.ccs.Model.Organization;
import com.ccs.Model.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {
        return organizationRepository.findById(organizationId).orElseThrow(EntityNotFoundException::new);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        organizationRepository.save(org);

    }

    public void updateOrg(Organization org) {
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        organizationRepository.delete(org);
    }
}

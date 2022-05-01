package com.ccs.Repository;

import com.ccs.Model.Organization;

public interface OrganizationRedisRepository {
    void saveOrganization(Organization org);

    void updateOrganization(Organization org);

    void deleteOrganization(String organizationId);

    Organization findOrganization(String organizationId);
}

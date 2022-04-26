package com.ccs.Contoller;

import com.ccs.Model.Organization;
import com.ccs.Service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug(String.format("Looking up data for org {}", organizationId));

        Organization org = organizationService.getOrg(organizationId);
        org.setContactName("NEW::" + org.getContactName());

        return organizationService.getOrg(organizationId);
    }

    @PutMapping("/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @PostMapping("/{organizationId}")
    public void saveOrganization(@RequestBody Organization org) {
        organizationService.saveOrg(org);
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId, @RequestBody Organization org) {
        organizationService.deleteOrg(org);
    }

}

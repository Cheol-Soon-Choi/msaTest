package com.ccs.Contoller;

import com.ccs.Model.Organization;
import com.ccs.Service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        System.out.println("성공");
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

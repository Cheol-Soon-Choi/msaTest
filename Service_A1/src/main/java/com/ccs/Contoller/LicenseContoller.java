package com.ccs.Contoller;

import com.ccs.Model.entity.License;
import com.ccs.Service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
public class LicenseContoller {

//    private final License license;
    private final LicenseService licenseService;

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {

        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {

//        return licenseService.getLicense(organizationId, licenseId, "");
        return licenseService.getLicense(organizationId, licenseId);
    }

//    @GetMapping("/{licenseId}/{clientType}")
//    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
//                                          @PathVariable("licenseId") String licenseId,
//                                          @PathVariable("clientType") String clientType) {
//
//        return licenseService.getLicense(organizationId,licenseId, clientType);
//    }

}

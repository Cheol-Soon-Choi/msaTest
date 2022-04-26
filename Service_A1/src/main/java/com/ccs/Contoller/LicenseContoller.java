package com.ccs.Contoller;

import com.ccs.Model.entity.License;
import com.ccs.Service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
public class LicenseContoller {

//    private final License license;
    private final LicenseService licenseService;
    private final HttpServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(LicenseContoller.class);

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {

        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        logger.debug("Found tmx-correlation-id in license-service-controller: {} ", request.getHeader("tmx-correlation-id"));
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

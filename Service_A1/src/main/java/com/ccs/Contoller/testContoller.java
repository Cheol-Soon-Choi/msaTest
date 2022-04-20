package com.ccs.Contoller;

import com.ccs.Model.entity.License;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
public class testContoller {

//    private final License license;

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {

        return License.builder()
                .licenseId(licenseId)
                .organizationId(organizationId)
                .productName("haha")
                .licenseType("type_v1")
                .build();
    }

}

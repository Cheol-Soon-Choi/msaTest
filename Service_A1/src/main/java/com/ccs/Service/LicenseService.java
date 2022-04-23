package com.ccs.Service;

import com.ccs.Clients.OrganizationDiscoveryClient;
import com.ccs.Clients.OrganizationFeignClient;
import com.ccs.Clients.OrganizationRestTemplateClient;
import com.ccs.Config.ServiceConfig;
import com.ccs.Model.entity.License;
import com.ccs.Model.entity.LicenseRepository;
import com.ccs.Model.entity.Organization;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

//    private Organization retrieveOrgInfo(String organizationId, String clientType) {
//        Organization organization = null;
//
//        switch (clientType) {
//            case "feign":
//                System.out.println("I am using the feign client");
//                organization = organizationFeignClient.getOrganization(organizationId);
//                break;
//            case "rest":
//                System.out.println("I am using the rest client");
//                organization = organizationRestClient.getOrganization(organizationId);
//                break;
//            case "discovery":
//                System.out.println("I am using the discovery client");
//                organization = organizationDiscoveryClient.getOrganization(organizationId);
//                break;
//            default:
//                organization = organizationRestClient.getOrganization(organizationId);
//        }
//
//        return organization;
//    }

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
//        Organization org = retrieveOrgInfo(organizationId, clientType);
        Organization org = getOrganization(organizationId);

        license.setComment(serviceConfig.getExampleProperty());
        license.setOrganizationName(org.getName());
        license.setContactName(org.getContactName());
        license.setContactEmail(org.getContactEmail());
        license.setContactPhone(org.getContactPhone());
        return license;
    }

    private void randomlyRunLong() {
        Random rand = new Random();

        int randomNum = rand.nextInt((3 - 1) + 1) + 1;

        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @HystrixCommand
    public Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize", value = "30"),
                            @HystrixProperty(name = "maxQueueSize", value = "10")},
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"),
//                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="12000")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        randomlyRunLong();

        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();

        fallbackList.add(License.builder()
                .licenseId("0000000-00-00000")
                .organizationId(organizationId)
                .productName("Sorry no licensing information currently available")
                .build());
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.setLicenseId((UUID.randomUUID().toString()));
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.delete(license);
    }

}

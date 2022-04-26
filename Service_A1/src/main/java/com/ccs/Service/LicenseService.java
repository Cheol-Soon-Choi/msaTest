package com.ccs.Service;

import com.ccs.Clients.OrganizationDiscoveryClient;
import com.ccs.Clients.OrganizationFeignClient;
import com.ccs.Clients.OrganizationRestTemplateClient;
import com.ccs.Config.ServiceConfig;
import com.ccs.Model.entity.License;
import com.ccs.Model.entity.LicenseRepository;
import com.ccs.Model.entity.Organization;
import com.ccs.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);

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

    @HystrixCommand
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
            //쓰레드 풀 설정 - 벌크헤드
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            },
            //회로 차단기 설정 = 15초 동안 10건의 호출 중 75% 에러(타임아웃, 예외, HTTP 500 등)가 발생하면 Circuit open 7초 수행
            commandProperties = {
                    // 12초 이상 지연되면 time out
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000"),
                    // =15초 (default = 10초)
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    // =10건 (default = 20건) =최소 호출 횟수
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // =75% (default = 50%)
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    // Circuit open 7초 (default = 5초) =회로 차단기를 차단한 후 서비스 재호출을 위해 대기하는 시간
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    // 설정한 시간 간격 동안 통계 수집 횟수 (default = 10)
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        logger.debug("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
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

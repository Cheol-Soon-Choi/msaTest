package com.ccs.Contoller;

import com.ccs.Service.DiscoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "v1/tools")
@RequiredArgsConstructor
public class SubController {

    private final DiscoveryService discoveryService;

    @GetMapping("/eureka/services")
    public List<String> getEurekaServices() {

        return discoveryService.getEurekaServices();
    }
}

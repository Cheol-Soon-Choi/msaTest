package com.ccs.controllers;


import com.ccs.model.AbTestingRoute;
import com.ccs.services.AbTestingRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/route/")
@RequiredArgsConstructor
public class SpecialRoutesServiceController {

    public final AbTestingRouteService routeService;

    @GetMapping("abtesting/{serviceName}")
    public AbTestingRoute abstestings(@PathVariable("serviceName") String serviceName) {

        return routeService.getRoute(serviceName);
    }

}

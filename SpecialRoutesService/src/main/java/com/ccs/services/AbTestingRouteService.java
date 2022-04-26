package com.ccs.services;

import com.ccs.exception.NoRouteFound;
import com.ccs.model.AbTestingRoute;
import com.ccs.model.AbTestingRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AbTestingRouteService {

    private final AbTestingRouteRepository abTestingRouteRepository;

    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);
        if (route == null) {
            throw new NoRouteFound();
        }
        return route;
    }

    public void saveAbTestingRoute(AbTestingRoute route) {
        abTestingRouteRepository.save(route);
    }

    public void updateRouteAbTestingRoute(AbTestingRoute route) {
        abTestingRouteRepository.save(route);
    }

    public void deleteRoute(AbTestingRoute route) {
        abTestingRouteRepository.delete(route);
    }
}

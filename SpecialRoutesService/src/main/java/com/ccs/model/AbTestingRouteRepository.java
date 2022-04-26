package com.ccs.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AbTestingRouteRepository extends JpaRepository<AbTestingRoute, String> {
    AbTestingRoute findByServiceName(String serviceName);
}

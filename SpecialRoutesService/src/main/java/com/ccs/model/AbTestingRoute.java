package com.ccs.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "abtesting")
@Getter
@Setter
public class AbTestingRoute {

    @Id
    @Column(name = "service_name", nullable = false)
    String serviceName;

    @Column(name = "active", nullable = false)
    String active;

    @Column(name = "endpoint", nullable = false)
    String endpoint;

    @Column(name = "weight", nullable = false)
    Integer weight;

}
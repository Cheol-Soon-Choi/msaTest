package com.ccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_orgs")
@Getter
@Setter
public class UserOrganization implements Serializable {
    @Column(name = "organization_id", nullable = false)
    String organizationId;

    @Id
    @Column(name = "user_name", nullable = false)
    String userName;


}

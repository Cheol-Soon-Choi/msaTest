package com.ccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {
    @Column(name = "password", nullable = false)
    String password;

    @Id
    @Column(name = "user_name", nullable = false)
    String userName;

    @Column(name = "enabled", nullable = false)
    Boolean enabled;

}

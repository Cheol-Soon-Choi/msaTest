package com.ccs.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long user_role_id;

    @Column(name = "role", nullable = false)
    String role;

    @Column(name = "user_name", nullable = false)
    String userName;

}

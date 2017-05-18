package com.xs.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    private String email;

    private String phone;

    @Column(name = "lastLoginIP")
    private String lastloginip;

}
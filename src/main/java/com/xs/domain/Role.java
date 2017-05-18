package com.xs.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by xs on 2017-05-17.
 */
@Getter
@Setter
@Entity
public class Role  {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Resource> resources;
}

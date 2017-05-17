package com.xs.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by xs on 2017-05-17.
 */
@Data
@Entity
public class Role extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Resource> resources;
}

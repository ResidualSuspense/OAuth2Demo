package com.xs.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xiaosong on 2017/4/25.
 */
public interface UserDao extends JpaRepository<User,Long>{

    User findByName(String name);
}

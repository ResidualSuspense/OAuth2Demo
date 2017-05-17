package com.xs.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xiaosong on 2017/5/17.
 */
@Data
@Entity
public class Resource extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String name;   //资源名称

    private String url;    //资源路径

    private String  remark; //备注

    private String methodPath; //资源所对应的方法名

    private String sn;

    private String value;  //资源标识
}

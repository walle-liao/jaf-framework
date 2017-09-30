package com.jaf.framework.core.mybatis.model;

import com.jaf.framework.core.model.BaseUser;

/**
 * @author: liaozhicheng
 * @date: 2017-09-30
 * @since: 1.0
 */
public class User extends BaseUser {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

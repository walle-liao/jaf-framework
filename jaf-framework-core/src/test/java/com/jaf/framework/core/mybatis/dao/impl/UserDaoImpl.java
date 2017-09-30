package com.jaf.framework.core.mybatis.dao.impl;

import com.jaf.framework.core.dao.impl.BaseDaoImpl;
import com.jaf.framework.core.mapper.BaseMapper;
import com.jaf.framework.core.mybatis.dao.UserDao;
import com.jaf.framework.core.mybatis.mapper.UserMapper;
import com.jaf.framework.core.mybatis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: liaozhicheng
 * @date: 2017-09-30
 * @since: 1.0
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected BaseMapper<User> getMapper() {
        return userMapper;
    }
}

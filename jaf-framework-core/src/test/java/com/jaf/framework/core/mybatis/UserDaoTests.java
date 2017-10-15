package com.jaf.framework.core.mybatis;

import com.jaf.framework.core.mybatis.dao.UserDao;
import com.jaf.framework.core.mybatis.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: liaozhicheng
 * @date: 2017-09-30
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mybatis/applicationContext.xml"})
public class UserDaoTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testHotRefresh() throws InterruptedException {
        User u = userDao.selectById("1");
        System.out.println(u.getName());

        Thread.sleep(20000);

        u = userDao.selectById("1");
        System.out.println(u.getName());
    }

}

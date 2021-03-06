package ssm_angel.dynamicrouting.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ssm_angel.dynamicrouting.bean.User;
import ssm_angel.dynamicrouting.common.GsonUtils;
import ssm_angel.dynamicrouting.dbrouting.contextholder.DbContextHolder;
import ssm_angel.dynamicrouting.service.IUserService;

/**
 * Created by supers on 2017/3/9.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-config.xml")
public class TestUserService {

    @Autowired
    private IUserService userService;

    /**
     * @Description 测试分库分表插入
     * @Autohr supers【weChat:13031016567】
     */
    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUserNum("430502199107265896g");
        user.setUserName("hnnd11");
        user.setAge(23);
        user.setPassword("hnnd");
        int re = userService.insertUser(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的插入结果:"+GsonUtils.toJson(re));
    }

    /**
     * @Description 测试分库分表删除
     * @Autohr supers【weChat:13031016567】
     */
    @Test
    public void testDeleteByuserNum(){
        User user = new User();
        user.setUserNum("wergsgdf3243");
        int re = userService.deleteByuserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的删除结果:"+GsonUtils.toJson(re));
    }


    /**
     * @Description 测试分库分表修改
     * @Autohr supers【weChat:13031016567】
     */
    @Test
    public void testupdateByUserNum(){
        User user = new User();
        user.setUserNum("wergsgdf3243");
        user.setAge(34);
        int re = userService.updateByUserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的更新结果:"+GsonUtils.toJson(re));
    }

    /**
     * @Description 测试分库分表查询
     * @Autohr supers【weChat:13031016567】
     */
    @Test
    public void testQueryUserByNum(){
        User user = new User();
        user.setId(1);
        user.setUserNum("wergsgdf3243");
        User userDb = userService.selectByUserNum(user);
        System.out.println(DbContextHolder.getDbKey()+"库 "+DbContextHolder.getTableIndex()+"表 的查询结果:"+GsonUtils.toJson(userDb));
    }
}

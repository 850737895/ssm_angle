package ssm_angel.dynamicrouting.service.impl;

import org.springframework.stereotype.Service;

import ssm_angel.dynamicrouting.bean.User;
import ssm_angel.dynamicrouting.dao.IUserDao;
import ssm_angel.dynamicrouting.dbrouting.annotation.Router;
import ssm_angel.dynamicrouting.service.IUserService;

import javax.annotation.Resource;

/**
 * @Description
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    @Router
    public int insertUser(User user) {
        return this.userDao.insertUser(user);
    }

    @Router
    public int deleteByuserNum(User user) {
        return this.userDao.deleteByuserNum(user);
    }

    @Router
    public int updateByUserNum(User user) {
        return this.userDao.updateByUserNum(user);
    }

    @Router
    public User selectByUserNum(User user) {
        return this.userDao.selectByUserNum(user);
    }
}

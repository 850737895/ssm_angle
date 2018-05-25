package ssm_angel.dynamicrouting.service;

import ssm_angel.dynamicrouting.bean.User;

/**
 * @Description
 */
public interface IUserService {

	/**
	 * @Description
	 */
	public int insertUser(User user);

	/**
	 * @Description
	 */
	public int deleteByuserNum(User user);

	/**
	 * @Description
	 */
	public int updateByUserNum(User user);

	/**
	 * @Description
	 */
	public User selectByUserNum(User user);


}

package ssm_angel.dynamicrouting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ssm_angel.dynamicrouting.bean.User;
import ssm_angel.dynamicrouting.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/queryUser")
	public String toIndex(HttpServletRequest request,Model model,User user){
		User userDb = this.userService.selectByUserNum(user);
		model.addAttribute("user", userDb);
		return "queryUser";
	}
}

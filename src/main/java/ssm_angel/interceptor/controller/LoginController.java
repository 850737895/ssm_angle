package ssm_angel.interceptor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssm_angel.interceptor.bean.UserInfo;

@Controller
public class LoginController {
	
	@RequestMapping("/doLogin")
	public String login(ModelAndView modelAndView,UserInfo userInfo) {
		System.out.println("登录的用户信息:"+userInfo);
		return "userInfo";
	}
	
	@RequestMapping("/login.html")
	public String tologin() {
		return "login";
	}
}

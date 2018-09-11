package per.itachi.test.controller.mybatis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import per.itachi.test.pojo.oracle.User;
import per.itachi.test.service.oracle.UserService;

/**
 * 默认情况下@org.springframework.stereotype.Controller的bean名称为首字母小写的类名(不包含包名)
 * */
@Controller("mybatisUserController")
@RequestMapping("/mybatis/user")
public class UserController {
	
	@Autowired
	@Qualifier("oracleUserService")
	private UserService userService;
	
	@RequestMapping(path="/showBasicInfo", method={RequestMethod.GET})
	public ModelAndView showBasicInfo() {
		ModelAndView mvc = new ModelAndView("/user-mybatis");
		List<User> listBeforeNow = userService.getUsersBeforeInsertTime(LocalDateTime.now());
		mvc.addObject("listBeforeInsertTime", listBeforeNow);
		return mvc;
	}
	
	@RequestMapping(path="/addNewUser", method={RequestMethod.POST})
	public ModelAndView addNewUser(@ModelAttribute User user) {
		user.setUserStatus(ThreadLocalRandom.current().nextInt(8));
		userService.insertUser(user);
		return showBasicInfo();
	}
	
	@RequestMapping(path="/testNestedBean", method={RequestMethod.GET})
	public ModelAndView testNestedBean() {
		userService.testNestedBean();
		return new ModelAndView("/default");
	}
}

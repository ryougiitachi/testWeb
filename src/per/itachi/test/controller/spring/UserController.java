package per.itachi.test.controller.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import per.itachi.test.pojo.postgresql.User;
import per.itachi.test.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	@Qualifier("defaultUserService")//("per.itachi.test.service.impl.UserServiceImpl") not ok
	private UserService userService;

	/**
	 * The actual url path is /springmvc/testUser because of /springmvc/* added in web.xml
	 * */
	@RequestMapping(path="/testUser", method={RequestMethod.GET})
	public ModelAndView testSpringMvcUser() {
		ModelAndView mvc = new ModelAndView("/user-springmvc");
		User userLoad = userService.load(1l);
		User userGet = userService.get(2l);
		List<User> userFindAll = userService.findAll();
		//put value into request attributes?
		mvc.addObject("userServiceload", userLoad);
		mvc.addObject("userServiceget", userGet);
		mvc.addObject("userServicefindAll", userFindAll);
		return mvc;
	}
	
}

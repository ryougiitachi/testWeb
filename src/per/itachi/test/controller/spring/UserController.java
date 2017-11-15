package per.itachi.test.controller.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import per.itachi.test.pojo.postgresql.User;
import per.itachi.test.service.postgresql.UserService;

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
		List<User> listFindUser = new ArrayList<>();
		listFindUser.add(userService.findUser(6l, 1));
		listFindUser.add(userService.findUser(6l, 2));
		listFindUser.add(userService.findUser(6l, 3));
		listFindUser.add(userService.findUser(6l, 4));
		listFindUser.add(userService.findUser(6l, 5));
//		User userLoad = userService.load(100l);	//exception when not found
//		User userGet = userService.get(200l);	//null when not found
		List<User> userFindAll = userService.findAll(1);
		//put value into request attributes?
//		mvc.addObject("userServiceload", userLoad);
//		mvc.addObject("userServiceget", userGet);
//		mvc.addObject("userServicefind", userFind);
		mvc.addObject("userServicefindUser", listFindUser);
		mvc.addObject("userServicefindAll", userFindAll);
		return mvc;
	}
	
	@RequestMapping(path="/testSaveUser", method={RequestMethod.POST})
	public ModelAndView testSaveUser(
			@RequestParam(name="userID") long userID, 
			@RequestParam(name="username") String username) {
		User user = new User();
		user.setUserID(userID);
		user.setUsername(username);
		userService.save(user);
		return testSpringMvcUser();
	}
	
	@RequestMapping(path="/testUpdateUser", method={RequestMethod.POST})
	public ModelAndView testUpdateUser(@ModelAttribute() User user) {
		userService.update(user);
		return testSpringMvcUser();
	}
	
	//@RequestBody
	@RequestMapping(path="/testSaveOrUpdateUser", method={RequestMethod.POST})
	public ModelAndView testSaveOrUpdateUser(@ModelAttribute User user) {
		userService.saveOrUpdate(user);
		return testSpringMvcUser();
	}
	
	@RequestMapping(path="/testTrxPersistence", method={RequestMethod.GET})
	public ModelAndView testTrxPersistence() {
		ModelAndView mvc = new ModelAndView("/default");
		userService.testTrxPersistence();
		return mvc;
	}
	
	@RequestMapping(path="/testSessionCache", method={RequestMethod.GET})
	public ModelAndView testSessionCache() {
		ModelAndView mvc = new ModelAndView("/default");
		userService.testSessionCache();
		return mvc;
	}
	
	@RequestMapping(path="/testSessionFactoryCache", method={RequestMethod.GET})
	public ModelAndView testSessionFactoryCache() {
		ModelAndView mvc = new ModelAndView("/default");
		userService.testSessionFactoryCache();
		return mvc;
	}
	
}

package per.itachi.test.controller.mybatis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import per.itachi.test.pojo.oracle.BankCity;
import per.itachi.test.service.oracle.BankCityService;

@Controller
@RequestMapping("/bankCity")
public class BankCityController {
	
	private static final Logger logger = LoggerFactory.getLogger(BankCityController.class);
	
	@Autowired
	private BankCityService bankCityService;
	
	@RequestMapping(path="/showBasicInfo", method={RequestMethod.GET})
	public ModelAndView showBasicInfo() {
		ModelAndView mvc = new ModelAndView("/bankCity-mybatis");
		return mvc;
	}
	
	@RequestMapping(path="/getBankCityByID", method={RequestMethod.POST})
	public ModelAndView getBankCityByID(@RequestParam("bankCityID") int id) {
		ModelAndView mvc = showBasicInfo();
		BankCity bankCity = bankCityService.getBankCityByID(id);
		mvc.addObject("bankCity", bankCity);
		return mvc;
	}
	
	@RequestMapping(path="/getAllItems", method={RequestMethod.POST})
	public ModelAndView getAllItems() {
		ModelAndView mvc = showBasicInfo();
		bankCityService.getAllItems();
		return mvc;
	}
	
	@RequestMapping(path="/mapAllItems", method={RequestMethod.POST})
	public ModelAndView mapAllItems() {
		ModelAndView mvc = showBasicInfo();
		Map<Integer, BankCity> map = bankCityService.mapAllItems();
		mvc.addObject("map", map);
		return mvc;
	}
	
	@RequestMapping(path="/add", method={RequestMethod.POST})
	public ModelAndView add(@ModelAttribute BankCity bankCity) {
		ModelAndView mvc = showBasicInfo();
		logger.debug("The number of inserted BankCity is {}", bankCityService.insertBankCity(bankCity));
		mvc.addObject("bankCityInserted", bankCity);
		return mvc;
	}
	
	@RequestMapping(path="/update", method={RequestMethod.POST})
	public ModelAndView update(@ModelAttribute BankCity bankCity) {
		ModelAndView mvc = showBasicInfo();
		logger.debug("The number of updated BankCity is {}", bankCityService.updBankCityByID(bankCity));
		mvc.addObject("bankCityUpdated", bankCity);
		return mvc;
	}
	
	@RequestMapping(path="/delete", method={RequestMethod.POST})
	public ModelAndView delete(@RequestParam int id) {
		ModelAndView mvc = showBasicInfo();
		logger.debug("The number of deleted BankCity is {}", bankCityService.delBankCityByID(id));
		return mvc;
	}
}

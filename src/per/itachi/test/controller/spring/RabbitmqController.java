package per.itachi.test.controller.spring;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import per.itachi.test.jms.rabbitmq.producer.CommonProducer;

@Controller
public class RabbitmqController {
	
	@Autowired
	private CommonProducer commonProducer;
	
	private ThreadLocal<StringBuilder> strBuilderLocal;
	
	@RequestMapping(path="/showRabbitmq", method=RequestMethod.GET)
	public ModelAndView showRabbitmq() {
		return new ModelAndView("/rabbitmq-springmvc");
	}

	@RequestMapping(path="/testRabbitmq", method=RequestMethod.POST)
	public ModelAndView testRabbitmq(@RequestParam(name="rabbitmq") int rabbitmq) {
		StringBuilder builder = getStringBuilder();
		switch (rabbitmq) {
		case 1://User Order
			builder.append("Order ID ").append(ThreadLocalRandom.current().nextInt(100000) + 100000);
			commonProducer.sendMsgUserOrder(builder.toString());
			break;
		case 2://Black List
			builder.append("Black Item ID ").append(ThreadLocalRandom.current().nextInt(100000) + 100000);
			commonProducer.sendMsgBlackList(builder.toString());
			break;
		case 3://Pay Log
			builder.append("Payment ID ").append(ThreadLocalRandom.current().nextInt(100000) + 100000);
			commonProducer.sendMsgPayLog(builder.toString());
			break;
		default:
			break;
		}
		builder.setLength(0);
		return showRabbitmq();
	}
	
	private StringBuilder getStringBuilder() {
		if (strBuilderLocal == null) {
			strBuilderLocal = new ThreadLocal<>();
		}
		StringBuilder builder = strBuilderLocal.get();
		if (builder == null) {
			builder = new StringBuilder();
			strBuilderLocal.set(builder);
		}
		return builder;
	}
}

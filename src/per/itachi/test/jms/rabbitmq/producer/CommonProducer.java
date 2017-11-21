package per.itachi.test.jms.rabbitmq.producer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CommonProducer {

	private static final Logger logger = LoggerFactory.getLogger(CommonProducer.class);
	
	@Autowired(required=false)
	@Qualifier("rabbitTemplate01")
	private AmqpTemplate rabbitTemplate01;
	
	@Resource(name="rabbitTemplate02")
	private AmqpTemplate rabbitTemplate02;
	
	public void sendMsgUserOrder(String order) {
		logger.debug("The common producer sent a new user order: {}", order);
		rabbitTemplate01.convertAndSend("itachi.jms.user.order", order);
		rabbitTemplate01.convertAndSend("itachi.jms.order", order);
	}
	
	public void sendMsgBlackList(String black) {
		logger.debug("The common producer sent a new black item: {}", black);
		rabbitTemplate01.convertAndSend("itachi.jms.black.list", black);
	}
	
	public void sendMsgPayLog(String paylog) {
		logger.debug("The common producer sent a new payment: {}", paylog);
		rabbitTemplate02.convertAndSend("itachi.jms.paylog", paylog);
		rabbitTemplate02.convertAndSend("itachi.jms.pay.log", paylog);
	}
}

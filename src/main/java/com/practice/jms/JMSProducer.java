/**
 *
 */
package com.practice.jms;

import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author sulabhtiwari
 *
 */
@Component
public class JMSProducer {

	@Autowired
	private JmsTemplate producerTemplate;

	@Value("${student.transaction.queue}")
	private String destinationName;

	public void sendMessage(Object message) {
		producerTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		producerTemplate.convertAndSend(destinationName, message);

	}
}

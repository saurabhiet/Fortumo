package fortumo.service;

import javax.jms.Destination;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


public class ProviderMessageSender {
	@Autowired  
	private JmsTemplate providerJmsTemplate;  
	public void sendMessage(final String message){  
		providerJmsTemplate.send(new MessageCreator(){  
	  
	        @Override  
	        public Message createMessage(Session session) throws JMSException {  
	            return session.createTextMessage(message);  
	        }  
	    });  
	}  }

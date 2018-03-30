package fortumo.service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

public class ProviderMessageListener implements MessageListener{  
	static Logger log = Logger.getLogger(ProviderMessageListener.class.getName());
	@Override  
	public void onMessage(Message m) {  
		TextMessage message=(TextMessage)m;  
		String msg = null;
		ResponseEntity<String> response = null;
		int res = 0;
		JSONObject jsonObject = null;
		try{  
			msg = message.getText();
		
			jsonObject = Util.stringToJson(msg);
			response =  Util.makeGetRequest(jsonObject);
			res = response.getStatusCode().value();
		}catch (Exception e) {
			Util.log(jsonObject, e, "Error in contacting provider"); 
		}

		Util.log(jsonObject, null, "Successful operation");

	}  
} 

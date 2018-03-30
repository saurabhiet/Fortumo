package fortumo.service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class MerchantMessageListener implements MessageListener{

	@Autowired
	private ProviderMessageSender providerMessageSender;  
	@Override  
	public void onMessage(Message m) {  
		
		TextMessage message=(TextMessage)m;  
		JSONObject jsonObject = null;
		String res = null;
		String msg = null;
		try{  
			msg = message.getText();
			jsonObject = Util.stringToJson(msg);
	
			JSONObject o = (JSONObject)jsonObject.get("payload");
			String str = o.toString();
			String keyword = o.getString("keyword");
			res = Util.makePostRequest(str,keyword);
			res =  Util.stringToJson(res).getString("reply_message");
			jsonObject.put("reply_message", res);
			// check reply msg log if failed
			//Send to provider if successful
			if(res.equals("this is the reply message content"))
				providerMessageSender.sendMessage(jsonObject.toString());
		}catch (Exception e) {
			Util.log(jsonObject, e, "Error in contacting merchant"); 
		}

	}  
} 

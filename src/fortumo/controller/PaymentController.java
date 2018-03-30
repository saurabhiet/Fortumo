package fortumo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fortumo.service.MerchantMessageSender;
import fortumo.service.Util;

@Controller
@RequestMapping("/v1")
public class PaymentController {


	 @Autowired
	 MerchantMessageSender merchantMessageSender;

	 @RequestMapping(value = "/sms", method = RequestMethod.GET)
	 @ResponseBody
	 public ResponseEntity<String> pay(@RequestParam(value = "message_id", required = true) String message_id,
			 @RequestParam(value = "sender", required = true) String sender ,
			 @RequestParam(value = "text", required = true) String text,
			 @RequestParam(value = "receiver", required = true) String receiver,
			 @RequestParam(value = "operator", required = true) String operator,
			 @RequestParam(value = "timestamp", required = true) String timestamp) {

		 
		 String msg = Util.createMessage(sender, operator, text, receiver,message_id);
		 merchantMessageSender.sendMessage(msg);
	  return new ResponseEntity(HttpStatus.OK);


	 }
}

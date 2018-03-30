package fortumo.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeReference;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class Util {
	
	static Logger log = Logger.getLogger("Fortumo");

	public static String createMessage(String sender, String operator, String message, String shortcode, String message_id)
	{
		StringBuilder strb = new StringBuilder();

		strb.append("{\"message_id\":\"").append(message_id);
		strb.append("\",\"payload\":");
		strb.append("{\"sender\":\"").append(sender);
		strb.append("\",\"operator\":\"").append(operator);
		strb.append("\",\"message\":\"").append(message);
		strb.append("\",\"shortcode\":\"").append(shortcode);
		strb.append("\",\"keyword\":\"").append(message.split(" ")[0]);
		strb.append("\",\"transaction_id\":\"");
		strb.append(UUID.randomUUID().toString());
		strb.append("\"}}");

		return strb.toString();

	}
	public static String makePostRequest(String json, String keyword)
	{
		String url = "http://bratwurst.fortumo.mobi/api/sms/" + keyword.toLowerCase();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");


		HttpEntity <String> httpEntity = new HttpEntity <String> (json, httpHeaders);

		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(url, httpEntity, String.class);

		return response;
	}

	public static ResponseEntity<String> makeGetRequest(JSONObject json)
	{
		String transactionUrl = "http://bratwurst.fortumo.mobi/sms/send";

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(transactionUrl)
				// Add query parameter
				.queryParam("message", json.getString("reply_message"))
				.queryParam("mo_message_id", json.getString("message_id"))
				.queryParam("receiver", ((JSONObject)(json.get("payload"))).getString("sender"))
				.queryParam("operator", ((JSONObject)(json.get("payload"))).getString("operator"));
		RestTemplate restTemplate = new RestTemplate();
		String plainClientCredentials="fortumo:topsecret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(builder.build().toUriString(), HttpMethod.GET, request, String.class);
		return response;
	}
	public static JSONObject stringToJson(String stringToParse)
	{
		JSONObject jsonObject = JSONObject.fromObject( stringToParse ); 
		return jsonObject;
	}
	
	public static void log(JSONObject json, Exception e, String txt)
	{
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String message = timestamp + " " + txt + " message_id=" + json.getString("message_id") + " transaction_id=" + ((JSONObject)(json.get("payload"))).getString("transaction_id") + " sender=" + ((JSONObject)(json.get("payload"))).getString("sender");
		if(null!=e)
		{
			log.error(message, e);
		} else
		{
			log.info(message);
		}
	}
}

package messages;

import org.json.JSONObject;

public class MessageNormal extends Message {
	private String message;

	public MessageNormal() {
		super();
		this.message = null;
	}
	
	public String getMessage() {
		return message;
	}

	public MessageNormal(String message) {
		super(2);
		this.message = message;
	}
	
	public JSONObject messageToJSON() {
		JSONObject ret = super.messageToJSON();
		ret.put("message",this.message);		
		return ret;
	}
	
	public void JSONToMessage(JSONObject message) {
		super.JSONToMessage(message);
		String mes = message.getString("message");
		this.message = mes;
	}

}

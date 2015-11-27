package messages;

import org.json.JSONObject;

public class Message {
	private int type;
	
	public Message() {
		this.type = -1;
	}

	public Message(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public JSONObject messageToJSON() {
		JSONObject ret = new JSONObject();
		ret.put("type", this.type);
		return ret;
	}
	
	public void JSONToMessage(JSONObject message) {
		this.type = message.getInt("type");
	}
	
}

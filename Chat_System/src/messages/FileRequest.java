package messages;

import org.json.JSONObject;

public class FileRequest extends Message {
	private String name;

	public FileRequest() {
		super();
		this.name = null;
	}

	public FileRequest(String name) {
		super(3);
		this.name = name;
	}
	
	public JSONObject messageToJSON() {
		JSONObject ret = super.messageToJSON();
		ret.put("name",this.name);		
		return ret;
	}
	
	public void JSONToMessage(JSONObject message) {
		super.JSONToMessage(message);
		this.name = message.getString("name");
	}
	
	public String getFileName() {
		return this.name;
	}
	
}

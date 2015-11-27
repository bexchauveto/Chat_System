package messages;

import org.json.JSONObject;

public class FileRequestReply extends Message {
	private boolean acceptFile;
	
	public FileRequestReply() {
		super();
		this.acceptFile = false;
	}

	public FileRequestReply(boolean acceptFile) {
		super(4);
		this.acceptFile = acceptFile;
	}

	public JSONObject messageToJSON() {
		JSONObject ret = super.messageToJSON();
		ret.put("ok",this.acceptFile);
		return ret;
	}
	
	public void JSONToMessage(JSONObject message) {
		super.JSONToMessage(message);
		this.acceptFile = message.getBoolean("ok");
	}


}

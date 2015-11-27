package messages;

import org.json.JSONObject;

public class MessageHello extends Message {
	private String nickName;
	private boolean reqReply;

	public MessageHello() {
		super();
		this.reqReply = false;
		this.nickName = null;
	}

	public MessageHello(String nickName, boolean reqReply) {
		super(0);
		this.nickName = nickName;
		this.reqReply = reqReply;
		
	}
	
	public String getNickName() {
		return nickName;
	}

	public boolean isReqReply() {
		return reqReply;
	}

	public JSONObject messageToJSON() {
		JSONObject ret = super.messageToJSON();
		ret.put("nickname", this.nickName);	
		ret.put("reqReply",this.reqReply);	
		return ret;
	}
	
	public void JSONToMessage(JSONObject message) {
		super.JSONToMessage(message);
		this.nickName = message.getString("nickname");
		this.reqReply = message.getBoolean("reqReply");
	}
}

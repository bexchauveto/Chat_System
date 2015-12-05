package messages;


import java.util.Date;

import org.json.JSONObject;

public class Message {
	private int type;
	private Date dateRecption;

	public Message() {
		this.type = -1;
		this.setDateRecption(new Date());
	}

	public Message(int type) {
		this.type = type;
		this.setDateRecption(new Date());
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

	public Date getDateRecption() {
		return dateRecption;
	}

	public void setDateRecption(Date dateRecption) {
		this.dateRecption = dateRecption;
	}

}

package messages;


import java.time.LocalDate;
import java.time.LocalTime;

import org.json.JSONObject;

public class Message {
	private int type;
	private LocalDate dateReception;
	private LocalTime timeReception;

	public Message() {
		this.type = -1;
		this.setDateReception(LocalDate.now());
		this.setTimeReception(LocalTime.now());
	}

	public Message(int type) {
		this.type = type;
		this.setDateReception(LocalDate.now());
		this.setTimeReception(LocalTime.now());
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

	public LocalDate getDateReception() {
		return dateReception;
	}

	public void setDateReception(LocalDate dateReception) {
		this.dateReception = dateReception;
	}

	public LocalTime getTimeReception() {
		return timeReception;
	}

	public void setTimeReception(LocalTime timeReception) {
		this.timeReception = timeReception;
	}

}

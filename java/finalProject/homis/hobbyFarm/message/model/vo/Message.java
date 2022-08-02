package finalProject.homis.hobbyFarm.message.model.vo;

import java.sql.Date;

public class Message {
	private int mNo;
	private String msg_to;
	private String msg_from;
	private String msg_title;
	private String msg_content;
	private Date msg_date;
	private int msg_view;
	private String to_delete;
	private String from_delete;
	
	public Message() {}
	
	public Message(int mNo, String msg_to, String msg_from, String msg_title, String msg_content, Date msg_date,
			int msg_view, String to_delete, String from_delete) {
		super();
		this.mNo = mNo;
		this.msg_to = msg_to;
		this.msg_from = msg_from;
		this.msg_title = msg_title;
		this.msg_content = msg_content;
		this.msg_date = msg_date;
		this.msg_view = msg_view;
		this.to_delete = to_delete;
		this.from_delete = from_delete;
	}

	public int getmNo() {
		return mNo;
	}

	public void setmNo(int mNo) {
		this.mNo = mNo;
	}

	public String getMsg_to() {
		return msg_to;
	}

	public void setMsg_to(String msg_to) {
		this.msg_to = msg_to;
	}

	public String getMsg_from() {
		return msg_from;
	}

	public void setMsg_from(String msg_from) {
		this.msg_from = msg_from;
	}

	public String getMsg_title() {
		return msg_title;
	}

	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public Date getMsg_date() {
		return msg_date;
	}

	public void setMsg_date(Date msg_date) {
		this.msg_date = msg_date;
	}

	public int getMsg_view() {
		return msg_view;
	}

	public void setMsg_view(int msg_view) {
		this.msg_view = msg_view;
	}

	public String getTo_delete() {
		return to_delete;
	}

	public void setTo_delete(String to_delete) {
		this.to_delete = to_delete;
	}

	public String getFrom_delete() {
		return from_delete;
	}

	public void setFrom_delete(String from_delete) {
		this.from_delete = from_delete;
	}

	@Override
	public String toString() {
		return "Message [mNo=" + mNo + ", msg_to=" + msg_to + ", msg_from=" + msg_from + ", msg_title=" + msg_title
				+ ", msg_content=" + msg_content + ", msg_date=" + msg_date + ", msg_view=" + msg_view + ", to_delete="
				+ to_delete + ", from_delete=" + from_delete + "]";
	}
}

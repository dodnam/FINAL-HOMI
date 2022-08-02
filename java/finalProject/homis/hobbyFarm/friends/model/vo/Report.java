package finalProject.homis.hobbyFarm.friends.model.vo;

import java.sql.Date;

public class Report {
	private int rpNo;
	private String rpSender;
	private String rpReciever;
	private Date rpDate;
	private String rpReason;
	private String check;
	private String rpDetail;
	private String rpUrl;
	private int rpCount;
	
	public Report() {}

	public Report(int rpNo, String rpSender, String rpReciever, Date rpDate, String rpReason, String check,
			String rpDetail, String rpUrl, int rpCount) {
		super();
		this.rpNo = rpNo;
		this.rpSender = rpSender;
		this.rpReciever = rpReciever;
		this.rpDate = rpDate;
		this.rpReason = rpReason;
		this.check = check;
		this.rpDetail = rpDetail;
		this.rpUrl = rpUrl;
		this.rpCount = rpCount;
	}

	public int getRpNo() {
		return rpNo;
	}

	public void setRpNo(int rpNo) {
		this.rpNo = rpNo;
	}

	public String getRpSender() {
		return rpSender;
	}

	public void setRpSender(String rpSender) {
		this.rpSender = rpSender;
	}

	public String getRpReciever() {
		return rpReciever;
	}

	public void setRpReciever(String rpReciever) {
		this.rpReciever = rpReciever;
	}

	public Date getRpDate() {
		return rpDate;
	}

	public void setRpDate(Date rpDate) {
		this.rpDate = rpDate;
	}

	public String getRpReason() {
		return rpReason;
	}

	public void setRpReason(String rpReason) {
		this.rpReason = rpReason;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getRpDetail() {
		return rpDetail;
	}

	public void setRpDetail(String rpDetail) {
		this.rpDetail = rpDetail;
	}

	public String getRpUrl() {
		return rpUrl;
	}

	public void setRpUrl(String rpUrl) {
		this.rpUrl = rpUrl;
	}

	
	public int getRpCount() {
		return rpCount;
	}

	public void setRpCount(int rpCount) {
		this.rpCount = rpCount;
	}

	@Override
	public String toString() {
		return "Report [rpNo=" + rpNo + ", rpSender=" + rpSender + ", rpReciever=" + rpReciever + ", rpDate=" + rpDate
				+ ", rpReason=" + rpReason + ", check=" + check + ", rpDetail=" + rpDetail + ", rpUrl=" + rpUrl + ", rpCount=" + rpCount + "]";
	}
	
	
	
	
}

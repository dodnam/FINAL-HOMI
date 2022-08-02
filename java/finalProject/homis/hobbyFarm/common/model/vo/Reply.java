package finalProject.homis.hobbyFarm.common.model.vo;

import java.sql.Date;

public class Reply {
	
	// 댓글
	private int rNo;
	private int refPno;
	private String rWriter;
	private String rNickName;
	private String rContent;
	private Date rWriteDate;
	private Date rmodifyDate;
	private String rDeleteYN;
	
	// 이미지
	private int imgId;
	private String originName;
	private String changeName;
	private String imgSrc;

	
	public Reply() {}


	public Reply(int rNo, int refPno, String rWriter, String rNickName, String rContent, Date rWriteDate,
			Date rmodifyDate, String rDeleteYN, int imgId, String originName, String changeName, String imgSrc) {
		super();
		this.rNo = rNo;
		this.refPno = refPno;
		this.rWriter = rWriter;
		this.rNickName = rNickName;
		this.rContent = rContent;
		this.rWriteDate = rWriteDate;
		this.rmodifyDate = rmodifyDate;
		this.rDeleteYN = rDeleteYN;
		this.imgId = imgId;
		this.originName = originName;
		this.changeName = changeName;
		this.imgSrc = imgSrc;
	}


	public int getrNo() {
		return rNo;
	}


	public void setrNo(int rNo) {
		this.rNo = rNo;
	}


	public int getRefPno() {
		return refPno;
	}


	public void setRefPno(int refPno) {
		this.refPno = refPno;
	}


	public String getrWriter() {
		return rWriter;
	}


	public void setrWriter(String rWriter) {
		this.rWriter = rWriter;
	}


	public String getrNickName() {
		return rNickName;
	}


	public void setrNickName(String rNickName) {
		this.rNickName = rNickName;
	}


	public String getrContent() {
		return rContent;
	}


	public void setrContent(String rContent) {
		this.rContent = rContent;
	}


	public Date getrWriteDate() {
		return rWriteDate;
	}


	public void setrWriteDate(Date rWriteDate) {
		this.rWriteDate = rWriteDate;
	}


	public Date getRmodifyDate() {
		return rmodifyDate;
	}


	public void setRmodifyDate(Date rmodifyDate) {
		this.rmodifyDate = rmodifyDate;
	}


	public String getrDeleteYN() {
		return rDeleteYN;
	}


	public void setrDeleteYN(String rDeleteYN) {
		this.rDeleteYN = rDeleteYN;
	}


	public int getImgId() {
		return imgId;
	}


	public void setImgId(int imgId) {
		this.imgId = imgId;
	}


	public String getOriginName() {
		return originName;
	}


	public void setOriginName(String originName) {
		this.originName = originName;
	}


	public String getChangeName() {
		return changeName;
	}


	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}


	public String getImgSrc() {
		return imgSrc;
	}


	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}


	@Override
	public String toString() {
		return "Reply [rNo=" + rNo + ", refPno=" + refPno + ", rWriter=" + rWriter + ", rNickName=" + rNickName
				+ ", rContent=" + rContent + ", rWriteDate=" + rWriteDate + ", rmodifyDate=" + rmodifyDate
				+ ", rDeleteYN=" + rDeleteYN + ", imgId=" + imgId + ", originName=" + originName + ", changeName="
				+ changeName + ", imgSrc=" + imgSrc + "]";
	}


}

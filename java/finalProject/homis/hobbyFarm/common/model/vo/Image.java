package finalProject.homis.hobbyFarm.common.model.vo;

import java.sql.Date;

public class Image {

	private int imgId;
	private int postNo;
	private String writer;
	private String originName;
	private String changeName;
	private String imgSrc;
	private Date uploadDate;
	private int fileLevel;
	
	
	public Image() {}

	public Image(int imgId, int postNo, String originName, String changeName, String imgSrc, Date uploadDate,
			int fileLevel) {
		super();
		this.imgId = imgId;
		this.postNo = postNo;
		this.originName = originName;
		this.changeName = changeName;
		this.imgSrc = imgSrc;
		this.uploadDate = uploadDate;
		this.fileLevel = fileLevel;
	}
	
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
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

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getFileLevel() {
		return fileLevel;
	}

	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}

	@Override
	public String toString() {
		return "Image [imgId=" + imgId + ", postNo=" + postNo + ", originName=" + originName + ", changeName="
				+ changeName + ", imgSrc=" + imgSrc + ", uploadDate=" + uploadDate + ", fileLevel=" + fileLevel + "]";
	}
	
}

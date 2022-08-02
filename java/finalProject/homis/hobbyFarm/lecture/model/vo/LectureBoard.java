package finalProject.homis.hobbyFarm.lecture.model.vo;

import java.sql.Date;

import finalProject.homis.hobbyFarm.common.model.vo.Image;

public class LectureBoard {
	private int postNo;
	private String writer;
	private String nickName;
	private String title;
	private String content;
	private int viewCount;
	private Date writeDate;
	private Date modifyDate;
	
	private int hobbyNo;
	private int otTuition;
	private int otTime;
	private int otAllTime;
	private String learner;
	private String teacherInfo;
	private String ableTime;
	private String ablePlace;
	
	private String hobbyName;
	
	private String originName;
	private String changeName;
	private String imgSrc;
	
	public LectureBoard() {}
	
	public LectureBoard(String writer, String nickName, String title, String content, int hobbyNo, int otTuition,
			int otTime, int otAllTime, String learner, String teacherInfo, String ableTime, String ablePlace) {
		super();
		this.writer = writer;
		this.nickName = nickName;
		this.title = title;
		this.content = content;
		this.hobbyNo = hobbyNo;
		this.otTuition = otTuition;
		this.otTime = otTime;
		this.otAllTime = otAllTime;
		this.learner = learner;
		this.teacherInfo = teacherInfo;
		this.ableTime = ableTime;
		this.ablePlace = ablePlace;
	}
	
	public LectureBoard(int postNo, String writer, String nickName, String title, String content, int viewCount,
			Date writeDate, Date modifyDate, int hobbyNo, int otTuition, int otTime, int otAllTime, String learner,
			String teacherInfo, String ableTime, String ablePlace) {
		super();
		this.postNo = postNo;
		this.writer = writer;
		this.nickName = nickName;
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
		this.writeDate = writeDate;
		this.modifyDate = modifyDate;
		this.hobbyNo = hobbyNo;
		this.otTuition = otTuition;
		this.otTime = otTime;
		this.otAllTime = otAllTime;
		this.learner = learner;
		this.teacherInfo = teacherInfo;
		this.ableTime = ableTime;
		this.ablePlace = ablePlace;
	}
	
	

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
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

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHobbyNo() {
		return hobbyNo;
	}

	public void setHobbyNo(int hobbyNo) {
		this.hobbyNo = hobbyNo;
	}

	public int getOtTuition() {
		return otTuition;
	}

	public void setOtTuition(int otTuition) {
		this.otTuition = otTuition;
	}

	public int getOtTime() {
		return otTime;
	}

	public void setOtTime(int otTime) {
		this.otTime = otTime;
	}

	public String getLearner() {
		return learner;
	}

	public void setLearner(String learner) {
		this.learner = learner;
	}

	public String getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(String teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public String getAbleTime() {
		return ableTime;
	}

	public void setAbleTime(String ableTime) {
		this.ableTime = ableTime;
	}

	public String getAblePlace() {
		return ablePlace;
	}

	public void setAblePlace(String ablePlace) {
		this.ablePlace = ablePlace;
	}
	
	public int getOtAllTime() {
		return otAllTime;
	}

	public void setOtAllTime(int otAllTime) {
		this.otAllTime = otAllTime;
	}

	@Override
	public String toString() {
		return "LectureBoard [writer=" + writer + ", nickName=" + nickName + ", title=" + title + ", content=" + content
				+ ", hobbyNo=" + hobbyNo + ", otTuition=" + otTuition + ", otTime=" + otTime + ", otAllTime="
				+ otAllTime + ", learner=" + learner + ", teacherInfo=" + teacherInfo + ", ableTime=" + ableTime
				+ ", ablePlace=" + ablePlace + "]";
	}

	

	

	
	
	
	
	
}

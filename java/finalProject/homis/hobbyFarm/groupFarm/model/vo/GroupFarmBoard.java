package finalProject.homis.hobbyFarm.groupFarm.model.vo;

import java.sql.Date;

public class GroupFarmBoard {
	
	private int postNo;
	private String writer;
	private String nickName;
	private int boardNo;
	private String title;
	private String content;
	private int viewCount;
	private Date writeDate;
	private Date modifyDate;
	private String deleteYN;
	private int hobbyNo;
	private String hobbyName;
	private String location;
	private String personnel;
	private Date startDate;
	private Date endDate;
	private String day;
	private String startHour;
	private String endHour;
	private String closeYN;
	private String offerYN;
	private String teacher;
	
	public GroupFarmBoard() {}

	public GroupFarmBoard(int postNo, String writer, String nickName, int boardNo, String title, String content,
			int viewCount, Date writeDate, Date modifyDate, String deleteYN, int hobbyNo, String hobbyName,
			String location, String personnel, Date startDate, Date endDate, String day, String startHour,
			String endHour, String closeYN, String offerYN, String teacher) {
		super();
		this.postNo = postNo;
		this.writer = writer;
		this.nickName = nickName;
		this.boardNo = boardNo;
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
		this.writeDate = writeDate;
		this.modifyDate = modifyDate;
		this.deleteYN = deleteYN;
		this.hobbyNo = hobbyNo;
		this.hobbyName = hobbyName;
		this.location = location;
		this.personnel = personnel;
		this.startDate = startDate;
		this.endDate = endDate;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.closeYN = closeYN;
		this.offerYN = offerYN;
		this.teacher = teacher;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
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

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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

	public String getDeleteYN() {
		return deleteYN;
	}

	public void setDeleteYN(String deleteYN) {
		this.deleteYN = deleteYN;
	}

	public int getHobbyNo() {
		return hobbyNo;
	}

	public void setHobbyNo(int hobbyNo) {
		this.hobbyNo = hobbyNo;
	}

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getCloseYN() {
		return closeYN;
	}

	public void setCloseYN(String closeYN) {
		this.closeYN = closeYN;
	}

	public String getOfferYN() {
		return offerYN;
	}

	public void setOfferYN(String offerYN) {
		this.offerYN = offerYN;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "GroupFarmBoard [postNo=" + postNo + ", writer=" + writer + ", nickName=" + nickName + ", boardNo="
				+ boardNo + ", title=" + title + ", content=" + content + ", viewCount=" + viewCount + ", writeDate="
				+ writeDate + ", modifyDate=" + modifyDate + ", deleteYN=" + deleteYN + ", hobbyNo=" + hobbyNo
				+ ", hobbyName=" + hobbyName + ", location=" + location + ", personnel=" + personnel + ", startDate="
				+ startDate + ", endDate=" + endDate + ", day=" + day + ", startHour=" + startHour + ", endHour="
				+ endHour + ", closeYN=" + closeYN + ", offerYN=" + offerYN + ", teacher=" + teacher + "]";
	}
	
	
	
}

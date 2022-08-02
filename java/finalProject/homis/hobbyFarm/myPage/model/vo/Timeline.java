package finalProject.homis.hobbyFarm.myPage.model.vo;

public class Timeline {
	private int tableNo; // 테이블 고유 시퀀스 넘버링
	private String userId; // 유저 아이디(로그인한 강사의 아이디)
	private int postNo; // 강의, 모임 글 번호(디테일 접근 위함)
	private String timeline; // 시간 저장해서 오는 곳(YYYYMMDDHH) 식으로 들어옴
	private String date; // subString으로 YYYYMMDD 저장하는 곳
	private String time; // subString으로 HH 저장하는 곳(24시간 형식)
	private int concNo; // conclusion No 받아오는거(준목씨 전용 변수ㅎ..)
	
	private String title; // 글 제목
	private String hobbyName; // 취미 이름
	private String learnerId; // 배우는 사람 아이디(미니 마이페이지 접근을 위함)
	private String learnerNick; // 배우는 사람 닉네임(표시용)
	private int otTime; // 강의 수강시간(n시간)
	
	private String tKind; // 강의, 모종 구분
	
	private int personnel; // 구하는 총 인원수(모임 전용)
	private int personCount; // 구해진 인원수(모임 전용)
	
	public Timeline() {}

	public Timeline(int tableNo, String userId, int postNo, String timeline, String date, String time, int concNo,
			String title, String hobbyName, String learnerId, String learnerNick, int otTime, String tKind,
			int personnel, int personCount) {
		super();
		this.tableNo = tableNo;
		this.userId = userId;
		this.postNo = postNo;
		this.timeline = timeline;
		this.date = date;
		this.time = time;
		this.concNo = concNo;
		this.title = title;
		this.hobbyName = hobbyName;
		this.learnerId = learnerId;
		this.learnerNick = learnerNick;
		this.otTime = otTime;
		this.tKind = tKind;
		this.personnel = personnel;
		this.personCount = personCount;
	}

	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getTimeline() {
		return timeline;
	}

	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	public String getLearnerId() {
		return learnerId;
	}

	public void setLearnerId(String learnerId) {
		this.learnerId = learnerId;
	}

	public String getLearnerNick() {
		return learnerNick;
	}

	public void setLearnerNick(String learnerNick) {
		this.learnerNick = learnerNick;
	}

	public String gettKind() {
		return tKind;
	}

	public void settKind(String tKind) {
		this.tKind = tKind;
	}

	public int getPersonnel() {
		return personnel;
	}

	public void setPersonnel(int personnel) {
		this.personnel = personnel;
	}

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	public int getConcNo() {
		return concNo;
	}

	public void setConcNo(int concNo) {
		this.concNo = concNo;
	}
	
	public int getOtTime() {
		return otTime;
	}

	public void setOtTime(int otTime) {
		this.otTime = otTime;
	}

	@Override
	public String toString() {
		return "Timeline [tableNo=" + tableNo + ", userId=" + userId + ", postNo=" + postNo + ", timeline=" + timeline
				+ ", date=" + date + ", time=" + time + ", concNo=" + concNo + ", title=" + title + ", hobbyName="
				+ hobbyName + ", learnerId=" + learnerId + ", learnerNick=" + learnerNick + ", otTime=" + otTime
				+ ", tKind=" + tKind + ", personnel=" + personnel + ", personCount=" + personCount + "]";
	}

}

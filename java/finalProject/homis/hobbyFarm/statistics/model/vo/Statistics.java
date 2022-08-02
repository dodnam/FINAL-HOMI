package finalProject.homis.hobbyFarm.statistics.model.vo;

import java.sql.Date;

public class Statistics {
	private Date sysdate;
	private int memberCount;
	private int visitCount;
	private int lectureCount;
	private int conLectureCount;
	
	public Statistics() {}

	public Statistics(Date sysdate, int memberCount, int visitCount, int lectureCount, int conLectureCount) {
		super();
		this.sysdate = sysdate;
		this.memberCount = memberCount;
		this.visitCount = visitCount;
		this.lectureCount = lectureCount;
		this.conLectureCount = conLectureCount;
	}

	
	public Date getSysdate() {
		return sysdate;
	}

	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}

	public int getConLectureCount() {
		return conLectureCount;
	}

	public void setConLectureCount(int conLectureCount) {
		this.conLectureCount = conLectureCount;
	}

	@Override
	public String toString() {
		return "Statistics [sysdate=" + sysdate + ", memberCount=" + memberCount + ", visitCount=" + visitCount
				+ ", lectureCount=" + lectureCount + ", conLectureCount=" + conLectureCount + "]";
	}

	
}

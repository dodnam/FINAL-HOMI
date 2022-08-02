package finalProject.homis.hobbyFarm.myPage.model.vo;

public class myPageCount {
	private int myLectureCount; // 강사가 운영중인 강의 수
	private int myFarmCount; // 참여중인 모임 수(강사, 학생 공통)
	private int finishedLectureCount; // 끝난 모임, 강의 수(강사, 학생 공통)
	private int stLectureCount; // 학생이 수강중인 강의 수
	
	public myPageCount() {}

	public myPageCount(int myLectureCount, int myFarmCount, int finishedLectureCount, int stLectureCount) {
		super();
		this.myLectureCount = myLectureCount;
		this.myFarmCount = myFarmCount;
		this.finishedLectureCount = finishedLectureCount;
		this.stLectureCount = stLectureCount;
	}

	public int getMyLectureCount() {
		return myLectureCount;
	}

	public void setMyLectureCount(int myLectureCount) {
		this.myLectureCount = myLectureCount;
	}

	public int getMyFarmCount() {
		return myFarmCount;
	}

	public void setMyFarmCount(int myFarmCount) {
		this.myFarmCount = myFarmCount;
	}

	public int getFinishedLectureCount() {
		return finishedLectureCount;
	}

	public void setFinishedLectureCount(int finishedLectureCount) {
		this.finishedLectureCount = finishedLectureCount;
	}

	public int getStLectureCount() {
		return stLectureCount;
	}

	public void setStLectureCount(int stLectureCount) {
		this.stLectureCount = stLectureCount;
	}

	@Override
	public String toString() {
		return "myPageCount [myLectureCount=" + myLectureCount + ", myFarmCount=" + myFarmCount
				+ ", finishedLectureCount=" + finishedLectureCount + ", stLectureCount=" + stLectureCount + "]";
	}
	
}

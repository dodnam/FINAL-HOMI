package finalProject.homis.hobbyFarm.common.model.vo;

public class Teacher {
	
	private String userId;
	private String nickName;
	private String originName;
	private String changeName;
	private String activeLoc;
	private int hobbyNo;
	private String hobbyName;
	private int postNo;
	private String title;
	
	// 기본 생성자
	public Teacher() {}

	public Teacher(String userId, String nickName, String originName, String changeName, String activeLoc, int hobbyNo,
			String hobbyName, int postNo, String title) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.originName = originName;
		this.changeName = changeName;
		this.activeLoc = activeLoc;
		this.hobbyNo = hobbyNo;
		this.hobbyName = hobbyName;
		this.postNo = postNo;
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getActiveLoc() {
		return activeLoc;
	}

	public void setActiveLoc(String activeLoc) {
		this.activeLoc = activeLoc;
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

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Teacher [userId=" + userId + ", nickName=" + nickName + ", originName=" + originName + ", changeName="
				+ changeName + ", activeLoc=" + activeLoc + ", hobbyNo=" + hobbyNo + ", hobbyName=" + hobbyName
				+ ", postNo=" + postNo + ", title=" + title + "]";
	}

}

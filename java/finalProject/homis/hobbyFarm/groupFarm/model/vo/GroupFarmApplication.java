package finalProject.homis.hobbyFarm.groupFarm.model.vo;

public class GroupFarmApplication {
	private int postNo;
	private String teacher;
	private String userId;
	private String nickName;
	private int mKind;
	private int imgId;
	private String originName;
	private String changeName;
	private String imgSrc;
	
	public GroupFarmApplication() {}

	

	public GroupFarmApplication(int postNo, String teacher, String userId, String nickName, int mKind, int imgId,
			String originName, String changeName, String imgSrc) {
		super();
		this.postNo = postNo;
		this.teacher = teacher;
		this.userId = userId;
		this.nickName = nickName;
		this.mKind = mKind;
		this.imgId = imgId;
		this.originName = originName;
		this.changeName = changeName;
		this.imgSrc = imgSrc;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
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

	public int getmKind() {
		return mKind;
	}

	public void setmKind(int mKind) {
		this.mKind = mKind;
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
		return "GroupFarmApplication [postNo=" + postNo + ", teacher=" + teacher + ", userId=" + userId + ", nickName="
				+ nickName + ", mKind=" + mKind + ", imgId=" + imgId + ", originName=" + originName + ", changeName="
				+ changeName + ", imgSrc=" + imgSrc + "]";
	}


}

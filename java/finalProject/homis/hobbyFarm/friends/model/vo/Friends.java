package finalProject.homis.hobbyFarm.friends.model.vo;

public class Friends {
	private int    fdNo;
	private String fdFrom;
	private String fdTo;
	private int    fdStatus;
	private String userId;
	private String userName;
	private String introduction;
	private String nickName;
	private String imgSrc;
	private	int	   mKind ;
	private String changeName; 
	
	public Friends() {}
	
	public Friends(int fdNo, String fdFrom, String fdTo, int fdStatus, String userId, String userName,
			String introduction, String nickName, String imgSrc, int mKind, String changeName) {
		super();
		this.fdNo = fdNo;
		this.fdFrom = fdFrom;
		this.fdTo = fdTo;
		this.fdStatus = fdStatus;
		this.userId = userId;
		this.userName = userName;
		this.introduction = introduction;
		this.nickName = nickName;
		this.imgSrc = imgSrc;
		this.mKind = mKind;
	}
	
	public int getFdNo() {
		return fdNo;
	}
	public void setFdNo(int fdNo) {
		this.fdNo = fdNo;
	}
	public String getFdFrom() {
		return fdFrom;
	}
	public void setFdFrom(String fdFrom) {
		this.fdFrom = fdFrom;
	}
	public String getFdTo() {
		return fdTo;
	}
	public void setFdTo(String fdTo) {
		this.fdTo = fdTo;
	}
	public int getFdStatus() {
		return fdStatus;
	}
	public void setFdStatus(int fdStatus) {
		this.fdStatus = fdStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public void setmKind(int mKind) {
		this.mKind = mKind;
	}
	public int getmKind() {
		return mKind;
	}
	
	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	@Override
	public String toString() {
		return "Friends [fdNo=" + fdNo + ", fdFrom=" + fdFrom + ", fdTo=" + fdTo + ", fdStatus=" + fdStatus
				+ ", userId=" + userId + ", userName=" + userName + ", introduction=" + introduction + ", nickName="
				+ nickName + ", imgSrc=" + imgSrc + ", changeName=" + changeName + "]";
	}	
}

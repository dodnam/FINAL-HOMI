package finalProject.homis.hobbyFarm.common.model.vo;

public class SearchSelect {
	private String title;
	private String content;
	private String writer;
	private String userId;
	private String nickName;
	private String userName;
	private String location;
	private String sido;
	private String gugun;
	private String hobby;
	private String searchValue;
	
	public SearchSelect() {}

	public SearchSelect(String title, String content, String writer, String userId, String nickName, String userName,
			String location, String sido, String gugun, String hobby, String searchValue) {
		super();
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.userId = userId;
		this.nickName = nickName;
		this.userName = userName;
		this.location = location;
		this.sido = sido;
		this.gugun = gugun;
		this.hobby = hobby;
		this.searchValue = searchValue;
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

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	@Override
	public String toString() {
		return "SearchSelect [title=" + title + ", content=" + content + ", writer=" + writer + ", userId=" + userId
				+ ", nickName=" + nickName + ", userName=" + userName + ", location=" + location + ", sido=" + sido
				+ ", gugun=" + gugun + ", hobby=" + hobby + ", searchValue=" + searchValue + "]";
	}

}

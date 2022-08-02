package finalProject.homis.hobbyFarm.member.model.vo;
import	java.sql.Date ;

public	class Member {
	private	String	userId ;
	private	String	userPwd ;
	private	String	userName ;
	private	String	nickName ;
	private	Date	birthDate ;
	private	String	gender ;
	private	String	phone ;
	private	String	email ;
	private	int		postalCode ;
	private	String	bAddress ;
	private	String	lAddress ;
	private	Date	createDate ;
	private	Date	modifyDate ;
	private	String	mStatus ;
	private	int		mKind ;
	private	String	introduction ;
	private	int		reportCount ;
	private	String	vipYn ;
	private	Date	vipDate ;
	private	String	originName ;
	private	String	changeName ;
	private	String	imgSrc ;
	private	Date	uploadDate ;
	private	String	activeLoc ;
	private	int		hobbyNo  ;
	private	String	hobbyName ;
	private	String	fdStatus ;
	
	public	Member() {}
	public	Member(String userId, String userPwd, String userName, String nickName, Date birthDate, String gender, String phone, String email,  
				   int postalCode, String bAddress, String lAddress, Date createDate, Date modifyDate, String mStatus, int mKind, String introduction,   
				   int reportCount, String vipYn, Date vipDate, String originName, String changeName, String imgSrc, Date uploadDate, String activeLoc,
				   int hobbyNo, String hobbyName, String fdStatus) {
		this.userId = userId;				this.userPwd = userPwd;				this.userName = userName;
		this.nickName = nickName;			this.birthDate = birthDate;			this.gender = gender;
		this.phone = phone;					this.email = email;					this.postalCode = postalCode;
		this.bAddress = bAddress;			this.lAddress = lAddress;			this.createDate = createDate;
		this.modifyDate = modifyDate;		this.mStatus = mStatus;				this.mKind = mKind;
		this.introduction = introduction;	this.reportCount = reportCount;		this.vipYn = vipYn;
		this.vipDate = vipDate;				this.originName = originName;		this.changeName = changeName;
		this.imgSrc = imgSrc;				this.uploadDate = uploadDate;		this.activeLoc = activeLoc;
		this.hobbyNo = hobbyNo;				this.hobbyName = hobbyName;			this.fdStatus = fdStatus;
	}

	public	String	getUserId()		  { return userId; }
	public	String	getUserPwd()	  { return userPwd; }
	public	String	getUserName()	  { return userName; }
	public	String	getNickName()	  { return nickName; }
	public	Date	getBirthDate()	  { return birthDate; }
	public	String	getGender()		  { return gender; }
	public	String	getPhone()		  { return phone; }
	public	String	getEmail()		  { return email; }
	public	int		getPostalCode()	  { return postalCode; }
	public	String	getbAddress()	  { return bAddress; }
	public	String	getlAddress()	  { return lAddress; }
	public	Date	getCreateDate()	  { return createDate; }
	public	Date	getModifyDate()	  { return modifyDate; }
	public	String	getmStatus()	  { return mStatus; }
	public	int		getmKind()		  { return mKind; }
	public	String	getIntroduction() { return introduction; }
	public	int		getReportCount()  { return reportCount; }
	public	String	getVipYn()		  { return vipYn; }
	public	Date	getVipDate()	  { return vipDate; }
	public	String	getOriginName()	  { return originName; }
	public	String	getChangeName()	  { return changeName; }
	public	String	getImgSrc()		  { return imgSrc; }
	public	Date	getUploadDate()	  { return uploadDate; }
	public	String	getActiveLoc()	  { return activeLoc; }
	public	int		getHobbyNo()	  { return hobbyNo; }
	public	String	getHobbyName()	  { return hobbyName; }
	public	String	getFdStatus()	  { return fdStatus; }
	
	public	void	setUserId(String userId)			 { this.userId = userId; }
	public	void	setUserPwd(String userPwd)			 { this.userPwd = userPwd; }
	public	void	setUserName(String userName)		 { this.userName = userName; }
	public	void	setNickName(String nickName)		 { this.nickName = nickName; }
	public	void	setBirthDate(Date birthDate)		 { this.birthDate = birthDate; }
	public	void	setGender(String gender)			 { this.gender = gender; }
	public	void	setPhone(String phone)				 { this.phone = phone; }
	public	void	setEmail(String email)				 { this.email = email; }
	public	void	setPostalCode(int postalCode)	 	 { this.postalCode = postalCode; }
	public	void	setbAddress(String bAddress)		 { this.bAddress = bAddress; }
	public	void	setlAddress(String lAddress)		 { this.lAddress = lAddress; }
	public	void	setCreateDate(Date createDate)		 { this.createDate = createDate; }
	public	void	setModifyDate(Date modifyDate)		 { this.modifyDate = modifyDate; }
	public	void	setmStatus(String mStatus)			 { this.mStatus = mStatus; }
	public	void	setmKind(int mKind)				 	 { this.mKind = mKind; }
	public	void	setIntroduction(String introduction) { this.introduction = introduction; }
	public	void	setReportCount(int reportCount)		 { this.reportCount = reportCount; }
	public	void	setVipYn(String vipYn)				 { this.vipYn = vipYn; }
	public	void	setVipDate(Date vipDate)			 { this.vipDate = vipDate; }
	public	void	setOriginName(String originName)	 { this.originName = originName; }
	public	void	setChangeName(String changeName)	 { this.changeName = changeName; }
	public	void	setImgSrc(String imgSrc)			 { this.imgSrc = imgSrc; }
	public	void	setUploadDate(Date uploadDate)		 { this.uploadDate = uploadDate; }
	public	void	setActiveLoc(String activeLoc)		 { this.activeLoc = activeLoc; }
	public	void	setHobbyNo(int hobbyNo)				 { this.hobbyNo = hobbyNo; }
	public	void	setHobbyName(String hobbyName)		 { this.hobbyName = hobbyName; }
	public	void	setFdStatus(String fdStatus)		 { this.fdStatus = fdStatus; }
	
	@Override
	public String toString() {
		return "Member [userId="+userId+", userPwd="+userPwd+", userName="+userName+", nickName="+nickName+", birthDate="+birthDate
					+", gender="+gender+", phone="+phone+", email="+email+", postalCode="+postalCode+", bAddress="+bAddress+", lAddress="+lAddress
					+", createDate="+createDate+", modifyDate="+modifyDate+", mStatus="+mStatus+", mKind="+mKind+", introduction="+introduction
					+", reportCount="+reportCount+", vipYn="+vipYn+", vipDate="+vipDate+", activeLoc="+activeLoc+", hobbyNo="+hobbyNo
					+", hobbyName="+hobbyName+", fdStatus="+fdStatus+"]" ;
	}
}

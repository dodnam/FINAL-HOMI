package finalProject.homis.hobbyFarm.member.model.service;

import finalProject.homis.hobbyFarm.member.model.vo.Member;

public interface MemberService {
	
	Member memberLogin(Member m) ;
	
	int checkIdDup(String userId) ;

	int checkNickDup(String nickName) ;

	int checkEmailDup(String email) ;

	int insertMember(Member m) ;

	int insertUserImg(Member m) ;

	int insertTeacher(String userId) ;
	
	int checkGoogleDup(String googleId);

	Member findid(Member m) ;

	int isExist(String email) ;

	int updatePwd(String userId, String ranText) ;

	int deleteMember(String userId) ;

	int updateMember(Member m) ;

	int updateUserImg(Member m) ;

	int updateTeacher(Member m) ;

	int countVisit(String sysdate) ;
	
	int countUser(String sysdate) ;
	
	int countUser2(String sysdate) ;

	String getPwd(String id) ;
	
}
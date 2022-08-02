package finalProject.homis.hobbyFarm.member.model.service ;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.member.model.dao.MemberDAO;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Service("mService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private	SqlSessionTemplate sqlSession ;
	
	@Autowired
	private	MemberDAO mDAO ;
	
	@Override
	public Member memberLogin(Member m) {
		return mDAO.selectMember(sqlSession, m) ;
	}
	
	@Override
	public int checkIdDup(String userId) {
		return mDAO.checkIdDup(sqlSession, userId) ;
	}

	@Override
	public int checkNickDup(String nickName) {
		return mDAO.checkNickDup(sqlSession, nickName) ;
	}

	@Override
	public int checkEmailDup(String email) {
		return mDAO.checkEmailDup(sqlSession, email) ;
	}

	@Override
	public int insertMember(Member m) {
		return mDAO.insertMember(sqlSession, m) ;
	}

	@Override
	public int insertUserImg(Member m) {
		return mDAO.insertUserImg(sqlSession, m) ;
	}
	
	@Override
	public int insertTeacher(String userId) {
		return mDAO.insertTeacher(sqlSession, userId) ;
	}
	
	@Override
	public int checkGoogleDup(String googleId) {
		return mDAO.checkGoogleDup(sqlSession, googleId) ;
	}

	@Override
	public int isExist(String email) {
		return mDAO.isExist(sqlSession, email) ;
	}
	
	@Override
	public Member findid(Member member) {
		return mDAO.findid(sqlSession, member) ;
	}

	@Override
	public int updatePwd(String userId, String ranText) {
		Member m = new Member() ;
		m.setUserId(userId)   ;
		m.setUserPwd(ranText) ;
		return mDAO.updatePwd(sqlSession, m) ;
	}

	@Override
	public int deleteMember(String userId) {
		return mDAO.deleteMember(sqlSession, userId) ;
	}

	@Override
	public int updateMember(Member m) {
		return mDAO.updateMemberN(sqlSession, m) ;
	}

	@Override
	public int updateUserImg(Member m) {
		return mDAO.updateImg(sqlSession, m) ;
	}

	@Override
	public int updateTeacher(Member m) {
		return mDAO.updateTeacher(sqlSession, m) ;
	}

	@Override
	public int countVisit(String sysdate) {
		return mDAO.countVisit(sqlSession, sysdate) ;
	}
	
	@Override
	public int countUser(String sysdate) {
		return mDAO.countUser(sqlSession, sysdate) ;
	}
	
	@Override
	public int countUser2(String sysdate) {
		return mDAO.countUser2(sqlSession, sysdate) ;
	}

	@Override
	public String getPwd(String id) {
		return mDAO.getPwd(sqlSession, id) ;
	}
}
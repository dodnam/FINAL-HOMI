package finalProject.homis.hobbyFarm.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Repository("mDAO")
public class MemberDAO {

	public Member selectMember(SqlSessionTemplate sqlSession, Member m) {
		return (Member)sqlSession.selectOne("memberMapper.selectOne", m) ;
	}

	public int checkIdDup(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.selectOne("memberMapper.checkIdDup", userId) ;
	}

	public int checkNickDup(SqlSessionTemplate sqlSession, String nickName) {
		return sqlSession.selectOne("memberMapper.checkNickDup", nickName) ;
	}

	public int checkEmailDup(SqlSessionTemplate sqlSession, String email) {
		return sqlSession.selectOne("memberMapper.checkEmailDup", email) ;
	}

	public int insertMember(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertMember", m) ;
	}

	public int insertUserImg(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.insert("memberMapper.insertUserImg", m) ;
	}
	
	public int insertTeacher(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.insert("memberMapper.insertTeacher", userId) ;
	}

	public int checkGoogleDup(SqlSessionTemplate sqlSession, String googleId) {
		return sqlSession.selectOne("memberMapper.checkGoogleDup", googleId) ;
	}

	public int isExist(SqlSessionTemplate sqlSession, String email) {
		return sqlSession.selectOne("memberMapper.isExist", email) ;
	}
	
	public Member findid(SqlSessionTemplate sqlSession, Member member) {
		return (Member)sqlSession.selectOne("memberMapper.findid", member) ;
	}

	public int updatePwd(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updatePwd", m) ;
	}

	public int deleteMember(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.update("memberMapper.deleteMember", userId) ;
	}

	public int updateMemberN(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateMember", m) ;
	}

	public int updateImg(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateImg", m) ;
	}
	
	public int updateTeacher(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.update("memberMapper.updateTeacher", m) ;
	}

	public int countVisit(SqlSessionTemplate sqlSession, String sysdate) {
		return sqlSession.update("memberMapper.countVisit", sysdate) ;
	}
	
	public int countUser(SqlSessionTemplate sqlSession, String sysdate) {
		return sqlSession.update("memberMapper.countUser", sysdate) ;
	}

	public int countUser2(SqlSessionTemplate sqlSession, String sysdate) {
		return sqlSession.update("memberMapper.countUser2", sysdate) ;
	}

	public String getPwd(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("memberMapper.getPwd", id) ;
	}
}
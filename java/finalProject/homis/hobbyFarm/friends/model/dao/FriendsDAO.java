package finalProject.homis.hobbyFarm.friends.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.vo.Friends;
import finalProject.homis.hobbyFarm.friends.model.vo.Report;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Repository("fDAO")
public class FriendsDAO {
	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("friendMapper.getListCount");
	}
	
	public int getSearchList(SqlSessionTemplate sqlSession, String id) {
		int result = sqlSession.selectOne("friendMapper.getSearchList", id);
		return result;
	}
	
	public ArrayList<Member> selectList(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		ArrayList<Member> list = (ArrayList)sqlSession.selectList("friendMapper.selectList", id, rowBounds);
		return list ;
	}

	public ArrayList<Member> searchList(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		ArrayList<Member> list = (ArrayList)sqlSession.selectList("friendMapper.searchList", id, rowBounds);
		return list;
	}

	public Member selectUser(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.selectOne("friendMapper.selectUser", userId);
	}

	public int getSearchListFCount(SqlSessionTemplate sqlSession, String searchValue) {
		return sqlSession.selectOne("friendMapper.getSearchListFCount", searchValue);
	}
 
	public ArrayList<Member> selectFList(SqlSessionTemplate sqlSession, PageInfo pi, String searchValue) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("friendMapper.selectFList", searchValue, rowBounds);
	}

	public int fRequest(SqlSessionTemplate sqlSession, Friends f) {
		return sqlSession.insert("friendMapper.fRequest", f);
	}

	public int fReSet(SqlSessionTemplate sqlSession, Friends f) {
		return sqlSession.delete("friendMapper.fReSet", f);
	}
	
	public int getListCountR(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("friendMapper.getListCountR", id);
	}
	
	public ArrayList<Member> fRequestPage(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		ArrayList<Member> list = (ArrayList)sqlSession.selectList("friendMapper.RequestPage", id, rowBounds);
		return list ;
	}

	public int fReSetRequest(SqlSessionTemplate sqlSession, Friends f) {
		return sqlSession.delete("friendMapper.fReSetRequest", f);
	}

	public int fConsend(SqlSessionTemplate sqlSession, Friends f) {
		return sqlSession.update("friendMapper.fConsend", f);
	}

	public int reportGo(SqlSessionTemplate sqlSession, Report r) {
		return sqlSession.insert("friendMapper.reportGo", r);
	}

	public int getRCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("friendMapper.getRCount");
	}

	public ArrayList<Report> rePortPage(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		ArrayList<Report> list = (ArrayList)sqlSession.selectList("friendMapper.rePortPage",null, rowBounds);
		return list ;
	}

	public int pRequest(SqlSessionTemplate sqlSession, String rpNo) {
		return sqlSession.update("friendMapper.pRequest", rpNo);
	}

	public void rpCount(SqlSessionTemplate sqlSession, Report r) {
		sqlSession.update("friendMapper.rpCount", r);
	}

	public int deleteFrd(SqlSessionTemplate sqlSession, Friends friend) {
		return sqlSession.delete("friendMapper.deleteFrd", friend) ;
	}

	public ArrayList<Conclusion> selectClass(SqlSessionTemplate sqlSession, String userId) {
		ArrayList<Conclusion> list = (ArrayList)sqlSession.selectList("friendMapper.selectClass", userId);
		return list;
	}

	public int reportreset(SqlSessionTemplate sqlSession, Integer rpNo) {
		return sqlSession.delete("friendMapper.reportreset", rpNo);
	}

}

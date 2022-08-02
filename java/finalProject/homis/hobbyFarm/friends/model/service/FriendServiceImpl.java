package finalProject.homis.hobbyFarm.friends.model.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.dao.FriendsDAO;
import finalProject.homis.hobbyFarm.friends.model.vo.Friends;
import finalProject.homis.hobbyFarm.friends.model.vo.Report;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Service("fService")
public class FriendServiceImpl implements FriendService {
	
	@Autowired
	private FriendsDAO fDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getListCount() {
		return fDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Member> selectList(PageInfo pi, String id) {
		return fDAO.selectList(sqlSession, pi, id);
	}

	@Override
	public int getSearchList(String id) {
		return fDAO.getSearchList(sqlSession, id);
	}

	@Override
	public ArrayList<Member> searchList(PageInfo pi, String id) {
		return fDAO.searchList(sqlSession, pi, id);
	}

	@Override
	public Member selectUser(String userId) {
		return fDAO.selectUser(sqlSession, userId);
	}

	@Override
	public int getSearchListFCount(String searchValue) {
		return fDAO.getSearchListFCount(sqlSession, searchValue);
	} 

	@Override
	public ArrayList<Member> selectFList(PageInfo pi, String searchValue) {
		return fDAO.selectFList(sqlSession, pi, searchValue);
	}

	@Override
	public int fRequest(Friends f) {
		return fDAO.fRequest(sqlSession, f);
	}

	@Override
	public int fReSet(Friends f) {
		return fDAO.fReSet(sqlSession, f);
	}

	@Override
	public int getListCountR(String id) {
		return fDAO.getListCountR(sqlSession, id);
	}
	
	@Override
	public ArrayList<Member> fRequestPage(PageInfo pi, String id) {
		return fDAO.fRequestPage(sqlSession, pi, id);
	}

	@Override
	public int fReSetRequest(Friends f) {
		return fDAO.fReSetRequest(sqlSession, f);
	}

	@Override
	public int fConsend(Friends f) {
		return fDAO.fConsend(sqlSession, f);
	}

	@Override
	public int reportGo(Report r) {
		return fDAO.reportGo(sqlSession, r);
	}

	@Override
	public int getRCount() {
		return fDAO.getRCount(sqlSession);
	}

	@Override
	public ArrayList<Report> rePortPage(PageInfo pi) {
		return fDAO.rePortPage(sqlSession, pi);
	}

	@Override
	public int pRequest(String rpNo) {
		return fDAO.pRequest(sqlSession, rpNo);
	}

	@Override
	public void rpCount(Report r) {
		fDAO.rpCount(sqlSession, r);
	}

	@Override
	public int deleteFrd(ArrayList<Friends> fArr) {
		int result = 0 ;
		for(int i=0; i<fArr.size(); i++)
			result += fDAO.deleteFrd(sqlSession, fArr.get(i)) ;
		
		if(result == fArr.size())	return 1 ;
		else						return 0 ;
	}

	@Override
	public ArrayList<Conclusion> selectClass(String userId) {
		return fDAO.selectClass(sqlSession, userId);
	}

	@Override
	public int reportreset(Integer rpNo) {
		return fDAO.reportreset(sqlSession, rpNo);
	}
	
	
}

package finalProject.homis.hobbyFarm.myPage.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.dao.LectureBoardDAO;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.myPage.model.dao.myPageDAO;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;

@Service("mpService")
public class myPageServiceImpl implements myPageService {
	@Autowired
	private myPageDAO mpDAO;
	@Autowired
	private LectureBoardDAO lbDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getRunningFarm(String id) {
		Search search = new Search();
		search.setSearchWhere("");
		search.setSearchTag("writer");
		search.setSearchValue(id);
		search.setSearchCate(0);
		return lbDAO.selectLBCount(sqlSession, search);
	}

	@Override
	public int getRunningGroup(String id) {
		return mpDAO.selectGroupCount(sqlSession, id);
	}

	@Override
	public int upgradeVip(String id) {
		return mpDAO.upgradeVip(sqlSession, id);
	}

	@Override
	public Member resetMemberSession(String id) {
		return mpDAO.getMemberSession(sqlSession, id);
	}

	@Override
	public int getRunningLecture(String id) {
		return mpDAO.getRunningLecture(sqlSession, id);
	}

	@Override
	public int getFinished(String id) {
			int lec = mpDAO.getFinishedLecture(sqlSession, id);
			int farm = mpDAO.getFinishedFarm(sqlSession, id);
			int total = lec + farm;
		return total;
	}

	@Override
	public ArrayList<LectureBoard> myLectureSt(String id) {
		return mpDAO.myLectureSt(sqlSession, id);
	}

	@Override
	public ArrayList<LectureBoard> myFinishedLectureList(String id) {
		return mpDAO.myFinishedLectureList(sqlSession, id);
	}

	@Override
	public int getListCount(String id) {
		return mpDAO.getListCount(sqlSession, id);
	}
	@Override
	public ArrayList<GroupFarmBoard> myFarm(PageInfo pi, String id) {
		return mpDAO.myFarm(sqlSession, pi, id);
	}

	@Override
	public ArrayList<Timeline> getTimeline(Timeline tl) {
		return mpDAO.getTimeline(sqlSession, tl);
	}


}

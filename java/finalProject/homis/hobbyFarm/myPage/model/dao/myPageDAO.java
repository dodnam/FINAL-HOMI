package finalProject.homis.hobbyFarm.myPage.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;

@Repository
public class myPageDAO {

	public int selectGroupCount(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getGroupCount", id);
	}

	public int upgradeVip(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.update("myPageMapper.upgradeVip", id);
	}

	public Member getMemberSession(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getMemberSession",id);
	}

	public int getRunningLecture(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getRunningLecture", id);
	}

	public int getFinishedLecture(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getfinishedLecture", id);
	}

	public int getFinishedFarm(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getfinishedFarm", id);
	}

	public ArrayList<LectureBoard> myLectureSt(SqlSessionTemplate sqlSession, String id) {
		return (ArrayList)sqlSession.selectList("myPageMapper.myLectureSt", id);
	}

	public ArrayList<LectureBoard> myFinishedLectureList(SqlSessionTemplate sqlSession, String id) {
		return (ArrayList)sqlSession.selectList("myPageMapper.myFinishedLectureList", id);
	}

	public int getListCount(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("myPageMapper.getListCount", id);
	}

	public ArrayList<GroupFarmBoard> myFarm(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("myPageMapper.myFarm", id, rowBounds);
	}

	public ArrayList<Timeline> getTimeline(SqlSessionTemplate sqlSession, Timeline tl) {
		return (ArrayList)sqlSession.selectList("myPageMapper.getTimeline", tl);
	}


}

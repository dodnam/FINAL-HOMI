package finalProject.homis.hobbyFarm.lecture.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;

@Repository("lbDAO")
public class LectureBoardDAO {

	public ArrayList<Hobby> selectHobbyList(SqlSession sqlSession) {
		return (ArrayList)sqlSession.selectList("lectureMapper.selectHobbyList");
	}

	public int insertBoard(SqlSession sqlSession, LectureBoard lb) {
		return sqlSession.insert("lectureMapper.insertBoard", lb);
	}

	public int insertLecture(SqlSession sqlSession, LectureBoard lb) {
		return sqlSession.insert("lectureMapper.insertLecture", lb);
	}

	public int insertImg(SqlSession sqlSession, Image img) {
		return sqlSession.insert("lectureMapper.insertImg", img);
	}

	public int selectLBCount(SqlSession sqlSession, Search search) {
		search.setSearchValue("%"+search.getSearchValue()+"%");
		search.setSearchWhere("%"+search.getSearchWhere()+"%");
		return sqlSession.selectOne("lectureMapper.selectLBCount", search);
	}

	public ArrayList<LectureBoard> selectLBPage(SqlSession sqlSession, PageInfo pi, Search search) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("lectureMapper.selectLBPage", search, rowBounds);
	}

	public int deleteCategory(SqlSession sqlSession, int hobbyNo) {
		return sqlSession.delete("lectureMapper.deleteCategory", hobbyNo);
	}

	public int insertCategory(SqlSession sqlSession, String hobbyName) {
		return sqlSession.insert("lectureMapper.insertCateogy", hobbyName);
	}

	public int updateCategory(SqlSession sqlSession, Hobby hobby) {
		return sqlSession.update("lectureMapper.updateCategory", hobby);
	}

	public LectureBoard selectLB(SqlSession sqlSession, int postNo) {
		return sqlSession.selectOne("lectureMapper.selectLB", postNo);
	}

	public ArrayList<Reply> selectRList(SqlSession sqlSession, int postNo) {
		return (ArrayList)sqlSession.selectList("lectureMapper.selectRList", postNo);
	}

	public int insertReply(SqlSession sqlSession, Reply r) {
		return sqlSession.insert("lectureMapper.insertReply", r);
	}

	public int insertConclusion(SqlSession sqlSession, Conclusion c) {
		return sqlSession.insert("lectureMapper.insertConclusion", c);
	}

	public int updateLecture(SqlSession sqlSession, LectureBoard lb) {
		return sqlSession.update("lectureMapper.updateLecture", lb);
	}

	public int checkBeforeConclusion(SqlSession sqlSession, Conclusion c) {
		return sqlSession.selectOne("lectureMapper.checkBeforeConclusion", c);
	}

	public int updateReply(SqlSession sqlSession, Reply r) {
		return sqlSession.update("lectureMapper.updateReply", r);
	}

	public int deleteReply(SqlSession sqlSession, Reply r) {
		return sqlSession.delete("lectureMapper.deleteReply", r);
	}

	public int updateBoard(SqlSession sqlSession, LectureBoard lb) {
		return sqlSession.update("lectureMapper.updateBoard", lb);
	}

	public int deleteBoard(SqlSession sqlSession, int postNo) {
		return sqlSession.update("lectureMapper.deleteBoard", postNo);
	}

	public int updateImg(SqlSession sqlSession, Image img) {
		return sqlSession.update("lectureMapper.updateImg",img);
	}

	public Conclusion selectConclusion(SqlSession sqlSession, Conclusion c) {
		return sqlSession.selectOne("lectureMapper.selectConclusion", c);
	}

	public int insertTimeline(SqlSession sqlSession, Timeline timeline) {
		return sqlSession.insert("lectureMapper.insertTimeline", timeline);
	}
	
	public int selectCCCount(SqlSession sqlSession) {
		return sqlSession.selectOne("lectureMapper.selectCCCount") ;
	}

	public ArrayList<LectureBoard> selectCCPage(SqlSession sqlSession, PageInfo pi) {
		return (ArrayList)sqlSession.selectList("lectureMapper.selectCCPage") ;
	}

	public int updateStatics(SqlSession sqlSession) {
		return sqlSession.update("lectureMapper.updateStatics");
	}

}

package finalProject.homis.hobbyFarm.lecture.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;
import finalProject.homis.hobbyFarm.lecture.model.dao.LectureBoardDAO;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;

@Service("lbService")
public class LectureBoardServiceImp implements LectureBoardService{
	
	@Autowired
	private LectureBoardDAO lbDAO;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public ArrayList<Hobby> selectHobbyList() {
		return lbDAO.selectHobbyList(sqlSession);
	}
	
	@Override
	public int insertBoard(LectureBoard lb) {
		return lbDAO.insertBoard(sqlSession, lb);
	}
	
	@Override
	public int insertLecture(LectureBoard lb) {
		return lbDAO.insertLecture(sqlSession, lb);
	}
	
	@Override
	public int insertImg(Image img) {
		return lbDAO.insertImg(sqlSession, img);
	}
	
	@Override
	public int selectLBCount(Search search) {
		return lbDAO.selectLBCount(sqlSession, search);
	}
	
	@Override
	public ArrayList<LectureBoard> selectLBPage(PageInfo pi, Search search) {
		return lbDAO.selectLBPage(sqlSession, pi, search);
	}
	
	@Override
	public int deleteCategory(int hobbyNo) {
		return lbDAO.deleteCategory(sqlSession, hobbyNo);
	}
	
	@Override
	public int insertCategory(String hobbyName) {
		return lbDAO.insertCategory(sqlSession, hobbyName);
	}
	
	@Override
	public int updateCategory(Hobby hobby) {
		return lbDAO.updateCategory(sqlSession, hobby);
	}
	
	@Override
	public LectureBoard selectLB(int postNo) {
		return lbDAO.selectLB(sqlSession, postNo);
	}
	
	@Override
	public ArrayList<Reply> selectRList(int postNo) {
		return lbDAO.selectRList(sqlSession, postNo);
	}
	
	@Override
	public int insertReply(Reply r) {
		return lbDAO.insertReply(sqlSession, r);
	}
	
	@Override
	public int insertConclusion(Conclusion c) {
		return lbDAO.insertConclusion(sqlSession, c);
	}
	
	@Override
	public int updateLecture(LectureBoard lb) {
		return lbDAO.updateLecture(sqlSession, lb);
	}
	
	@Override
	public int checkBeforeConclusion(Conclusion c) {
		return lbDAO.checkBeforeConclusion(sqlSession, c);
	}
	
	@Override
	public int updateReply(Reply r) {
		return lbDAO.updateReply(sqlSession, r);
	}
	
	@Override
	public int deleteReply(Reply r) {
		return lbDAO.deleteReply(sqlSession, r);
	}
	
	@Override
	public int updateBoard(LectureBoard lb) {
		return lbDAO.updateBoard(sqlSession, lb);
	}
	
	@Override
	public int deleteBoard(int postNo) {
		return lbDAO.deleteBoard(sqlSession, postNo);
	}
	
	@Override
	public int updateImg(Image img) {
		return lbDAO.updateImg(sqlSession, img);
	}
	
	@Override
	public Conclusion selectConclusion(Conclusion c) {
		return lbDAO.selectConclusion(sqlSession, c);
	}
	
	@Override
	public int insertTimeline(Timeline timeline) {
		return lbDAO.insertTimeline(sqlSession, timeline);
	}
	
	@Override
	public int selectCCCount() {
		return lbDAO.selectCCCount(sqlSession) ;
	}
	
	@Override
	public ArrayList<LectureBoard> selectCCPage(PageInfo pi) {
		return lbDAO.selectCCPage(sqlSession, pi) ;
	}
	
	@Override
	public int updateStatics() {
		return lbDAO.updateStatics(sqlSession);
	}
}

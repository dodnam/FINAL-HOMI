package finalProject.homis.hobbyFarm.groupFarm.model.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.common.model.vo.Teacher;
import finalProject.homis.hobbyFarm.groupFarm.model.dao.GroupFarmDAO;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmApplication;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Service("gfService")
public class GroupFarmServiceImpl implements GroupFarmService{

	@Autowired
	private GroupFarmDAO gfDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 취미 목록
	@Override
	public ArrayList<Hobby> selectHList() {
		return gfDAO.selectHList(sqlSession);
	}
	
	// 게시글 listCount
	@Override
	public int getListCount(SearchSelect ss) {
		return gfDAO.getListCount(sqlSession, ss);
	}

	// 게시글 목록
	@Override
	public ArrayList<GroupFarmBoard> selectList(PageInfo pi, SearchSelect ss) {
		return gfDAO.selectList(sqlSession, pi, ss);
	}

	// 게시글 목록 이미지
	@Override
	public ArrayList<Image> selectTList(PageInfo pi, SearchSelect ss) {
		return gfDAO.selectTList(sqlSession, pi, ss);
	}
	
	// 게시글 작성
	@Override
	public int insertBoard(GroupFarmBoard gf, Image img) {
		return gfDAO.insertBoard(sqlSession, gf, img);
	}

	// 게시글 상세 조회
	@Override
	public GroupFarmBoard selectBoard(Integer postNo) {
		GroupFarmBoard gf = null;
		
		int result = gfDAO.addReadCount(sqlSession, postNo);
		
		if(result > 0) {
			gf = gfDAO.selectBoard(sqlSession, postNo);
		}
		
		return gf;
		
	}

	// 게시글 상세 조회 이미지
	@Override
	public Image selectImage(Integer postNo) {
		return gfDAO.selectImage(sqlSession, postNo);
	}

	// 모임 참여자 목록
	@Override
	public ArrayList<GroupFarmApplication> selectGfaList(Integer postNo) {
		return gfDAO.selectGfaList(sqlSession, postNo);
	}

	// 댓글 목록
	@Override
	public ArrayList<Reply> selectReplyList(int postNo) {
		return gfDAO.selectReplyList(sqlSession, postNo);
	}

	// 댓글 작성
	@Override
	public int insertReply(Reply r) {
		return gfDAO.insertReply(sqlSession, r);
	}
	
	// 댓글 수정
	@Override
	public int updateReply(Reply r) {
		return gfDAO.updateReply(sqlSession, r);
	}
	
	// 댓글 삭제
	@Override
	public int deleteReply(Reply r) {
		return gfDAO.deleteReply(sqlSession, r);
	}

	// 모임 참가 + 강의 신청
	@Override
	public int enterGroup(GroupFarmApplication gfa) {
		return gfDAO.enterGroup(sqlSession, gfa);
	}
	
	// 모임 탈퇴 + 강의 취소
	@Override
	public int exeuntGroup(GroupFarmApplication gfa) {
		return gfDAO.exeuntGroup(sqlSession, gfa);
	}
	
	// 모임 마감
	@Override
	public int closeGroup(int postNo) {
		return gfDAO.closeGroup(sqlSession, postNo);
	}

	// 게시글 삭제
	@Override
	public int deleteBoard(Integer postNo) {
		return gfDAO.deleteBoard(sqlSession, postNo);
	}

	// 강사 추천 목록
	@Override
	public ArrayList<Teacher> teacherList(GroupFarmBoard gf) {
		return gfDAO.selectTeacherList(sqlSession, gf);
	}

	@Override
	public GroupFarmBoard selectLastInsertInfo(String loginId) {
		return gfDAO.selectLastInsertInfo(sqlSession, loginId);
	}

	@Override
	public int cancleClose(int postNo) {
		return gfDAO.cancleClose(sqlSession, postNo);
	}

	@Override
	public LectureBoard recentLec(String userId) {
		return gfDAO.recentLec(sqlSession, userId);
	}

	// 친구 리스트
	@Override
	public ArrayList<Member> selectFdList(String userId) {
		return gfDAO.selectFdList(sqlSession, userId);
	}

	// 게시글 수정
	@Override
	public int updateBoard(GroupFarmBoard gf) {
		return gfDAO.updateBoard(sqlSession, gf);
	}

	@Override
	public int updateGroup(GroupFarmBoard gf) {
		return gfDAO.updateGroup(sqlSession, gf);
	}

	@Override
	public int updateImg(Image img) {
		return gfDAO.updateImg(sqlSession, img);
	}

	

	












}

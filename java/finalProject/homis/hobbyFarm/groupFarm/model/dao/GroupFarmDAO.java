package finalProject.homis.hobbyFarm.groupFarm.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.common.model.vo.Teacher;
import finalProject.homis.hobbyFarm.groupFarm.model.exception.GroupFarmBoardException;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmApplication;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@Repository("gfDAO")
public class GroupFarmDAO {

	// 취미 목록
	public ArrayList<Hobby> selectHList(SqlSessionTemplate sqlSession) {
		return (ArrayList)sqlSession.selectList("groupFarmMapper.selectHList");
	}
	
	// 게시글 listCount
	public int getListCount(SqlSessionTemplate sqlSession, SearchSelect ss) {
		return sqlSession.selectOne("groupFarmMapper.getListCount", ss);
	}
	
	// 게시글 목록
	public ArrayList<GroupFarmBoard> selectList(SqlSessionTemplate sqlSession, PageInfo pi, SearchSelect ss) {
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("groupFarmMapper.selectList", ss, rowBounds);
	}

	// 게시글 목록 이미지
	public ArrayList<Image> selectTList(SqlSessionTemplate sqlSession, PageInfo pi, SearchSelect ss) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("groupFarmMapper.selectFList", ss, rowBounds);
	}
	
	// 게시글 검색 listCount
	public int getSearchListCount(SqlSessionTemplate sqlSession, SearchSelect ss) {
		return sqlSession.selectOne("groupFarmMapper.getSearchListCount");
	}

	// 게시글 작성
	public int insertBoard(SqlSessionTemplate sqlSession, GroupFarmBoard gf, Image img) {
		
		List<GroupFarmBoard> list = new ArrayList<GroupFarmBoard>();
		list.add(gf);
		
		int result = sqlSession.insert("groupFarmMapper.insertBoard", list);
		
		if(result > 0) {
			return sqlSession.insert("groupFarmMapper.insertImg", img);
		} else {
			throw new GroupFarmBoardException("DB등록에 실패하였습니다.");
		}
		
	}

	// 게시글 조회수 증가
	public int addReadCount(SqlSessionTemplate sqlSession, Integer postNo) {
		return sqlSession.update("groupFarmMapper.addReadCount", postNo);
	}

	// 게시글 상세 조회
	public GroupFarmBoard selectBoard(SqlSessionTemplate sqlSession, Integer postNo) {
		return sqlSession.selectOne("groupFarmMapper.selectBoard", postNo);
	}

	// 게시글 상세 조회 이미지
	public Image selectImage(SqlSessionTemplate sqlSession, Integer postNo) {
		return sqlSession.selectOne("groupFarmMapper.selectImage", postNo);
	}

	// 모임 참여자 목록
	public ArrayList<GroupFarmApplication> selectGfaList(SqlSessionTemplate sqlSession, Integer postNo) {
		return (ArrayList)sqlSession.selectList("groupFarmMapper.selectGfaList", postNo);
	}

	// 댓글 목록
	public ArrayList<Reply> selectReplyList(SqlSessionTemplate sqlSession, int postNo) {
		return (ArrayList)sqlSession.selectList("groupFarmMapper.selectReplyList", postNo);
	}

	// 댓글 등록
	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("groupFarmMapper.insertReply", r);
	}
	
	// 댓글 수정
	public int updateReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("groupFarmMapper.updateReply", r);
	}
	
	// 댓글 삭제
	public int deleteReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("groupFarmMapper.deleteReply", r);
	}

	public int enterGroup(SqlSessionTemplate sqlSession, GroupFarmApplication gfa) {
		if(gfa.getmKind() == 2) {
			int update = sqlSession.update("groupFarmMapper.updateTeacher", gfa);
			if(update > 0) {
				return sqlSession.insert("groupFarmMapper.enterGroup", gfa);
			} else {
				throw new GroupFarmBoardException("Teacher 등록에 실패하였습니다.");
			}
		} else {
			return sqlSession.insert("groupFarmMapper.enterGroup", gfa);
		}
	}

	public int exeuntGroup(SqlSessionTemplate sqlSession, GroupFarmApplication gfa) {
		if(gfa.getmKind() == 2) {
			
			int update = sqlSession.update("groupFarmMapper.teacherNull", gfa);
			
			if(update > 0) {
				return sqlSession.delete("groupFarmMapper.exeuntGroup", gfa);
			} else {
				throw new GroupFarmBoardException("강의 신청 취소에 실패하였습니다.");
			}
		} else {
			return sqlSession.delete("groupFarmMapper.exeuntGroup", gfa);
		}
	}

	public int closeGroup(SqlSessionTemplate sqlSession, int postNo) {
		return sqlSession.update("groupFarmMapper.closeGroup", postNo);
	}

	//게시글 삭제
	public int deleteBoard(SqlSessionTemplate sqlSession, Integer postNo) {
		return sqlSession.update("groupFarmMapper.deleteBoard", postNo);
	}

	public int getFilterSearchListCount(SqlSessionTemplate sqlSession, SearchSelect ss) {
		return sqlSession.selectOne("groupFarmMapper.getFilterListCount", ss);
		
	}

	// 추천 강사 목록
	public ArrayList<Teacher> selectTeacherList(SqlSessionTemplate sqlSession, GroupFarmBoard gf) {
		return (ArrayList)sqlSession.selectList("groupFarmMapper.setelctTeacherList", gf);
	}

	public GroupFarmBoard selectLastInsertInfo(SqlSessionTemplate sqlSession, String loginId) {
		return sqlSession.selectOne("groupFarmMapper.selectLastInsertInfo", loginId);
	}

	public int cancleClose(SqlSessionTemplate sqlSession, int postNo) {
		return sqlSession.update("groupFarmMapper.cancleClose", postNo);
	}

	public LectureBoard recentLec(SqlSessionTemplate sqlSession, String userId) {
		return sqlSession.selectOne("groupFarmMapper.selectLecture", userId);
	}

	// 친구 리스트
	public ArrayList<Member> selectFdList(SqlSessionTemplate sqlSession, String userId) {
		return (ArrayList)sqlSession.selectList("friendMapper.selectList", userId);
	}

	public int updateBoard(SqlSessionTemplate sqlSession, GroupFarmBoard gf, Image img) {
		
		int result = sqlSession.update("groupFarmMapper.updateBoard", gf);
		
		if(result > 0) {
			return sqlSession.update("groupFarmMapper.updateImg", img);
		} else {
			throw new GroupFarmBoardException("DB등록에 실패하였습니다.");
		}
	}

	public int updateBoard(SqlSessionTemplate sqlSession, GroupFarmBoard gf) {
		return sqlSession.update("groupFarmMapper.updateBoard", gf);
	}

	public int updateGroup(SqlSessionTemplate sqlSession, GroupFarmBoard gf) {
		return sqlSession.update("groupFarmMapper.updateGroup", gf);
	}

	public int updateImg(SqlSessionTemplate sqlSession, Image img) {
		return sqlSession.update("groupFarmMapper.updateImg", img);
	}

	

	






		
	
}

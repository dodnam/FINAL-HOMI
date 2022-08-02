package finalProject.homis.hobbyFarm.groupFarm.model.service;

import java.util.ArrayList;
import java.util.List;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.common.model.vo.Teacher;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmApplication;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

public interface GroupFarmService {

	int getListCount(SearchSelect ss);

	ArrayList<Hobby> selectHList();
	
	ArrayList<GroupFarmBoard> selectList(PageInfo pi, SearchSelect ss);

	ArrayList<Image> selectTList(PageInfo pi, SearchSelect ss);

	int insertBoard(GroupFarmBoard gf, Image img);

	ArrayList<Teacher> teacherList(GroupFarmBoard gf);

	GroupFarmBoard selectBoard(Integer postNo);

	Image selectImage(Integer postNo);

	ArrayList<GroupFarmApplication> selectGfaList(Integer postNo);

	ArrayList<Reply> selectReplyList(int postNo);

	int insertReply(Reply r);

	int updateReply(Reply r);
	
	int deleteReply(Reply r);

	int enterGroup(GroupFarmApplication gfa);

	int exeuntGroup(GroupFarmApplication gfa);

	int closeGroup(int postNo);

	int deleteBoard(Integer postNo);

	GroupFarmBoard selectLastInsertInfo(String loginId);

	// 모임 마감 취소
	int cancleClose(int postNo);

	LectureBoard recentLec(String userId);

	// 친구 리스트
	ArrayList<Member> selectFdList(String userId);

	// 게시글 수정
	int updateBoard(GroupFarmBoard gf);

	int updateGroup(GroupFarmBoard gf);
	
	int updateImg(Image img);





	




}

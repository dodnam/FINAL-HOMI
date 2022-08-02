package finalProject.homis.hobbyFarm.lecture.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;

public interface LectureBoardService {

	ArrayList<Hobby> selectHobbyList();

	int insertBoard(LectureBoard lb);

	int insertLecture(LectureBoard lb);

	int insertImg(Image img);

	int selectLBCount(Search search);

	ArrayList<LectureBoard> selectLBPage(PageInfo pi, Search search);

	int deleteCategory(int hobbyNo);

	int insertCategory(String hobbyName);

	int updateCategory(Hobby hobby);

	LectureBoard selectLB(int postNo);

	ArrayList<Reply> selectRList(int postNo);

	int insertReply(Reply r);

	int insertConclusion(Conclusion c);

	int updateLecture(LectureBoard lb);

	int checkBeforeConclusion(Conclusion c);

	int updateReply(Reply r);

	int deleteReply(Reply r);

	int updateBoard(LectureBoard lb);

	int deleteBoard(int postNo);

	int updateImg(Image img);

	Conclusion selectConclusion(Conclusion c);

	int insertTimeline(Timeline timeline);

	int selectCCCount() ;

	ArrayList<LectureBoard> selectCCPage(PageInfo pi);

	int updateStatics();
}

package finalProject.homis.hobbyFarm.friends.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.vo.Friends;
import finalProject.homis.hobbyFarm.friends.model.vo.Report;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

public interface FriendService {

	int getListCount();

	ArrayList<Member> selectList(PageInfo pi, String id);
 
	int getSearchList(String id);

	ArrayList<Member> searchList(PageInfo pi, String id);

	Member selectUser(String userId);

	int getSearchListFCount(String searchValue);

	ArrayList<Member> selectFList(PageInfo pi, String searchValue);

	int fRequest(Friends f);

	int fReSet(Friends f);

	int getListCountR(String id);
	
	ArrayList<Member> fRequestPage(PageInfo pi, String id);

	int fReSetRequest(Friends f);

	int fConsend(Friends f);

	int reportGo(Report r);

	int getRCount();

	ArrayList<Report> rePortPage(PageInfo pi);

	int pRequest(String rpNo);

	void rpCount(Report r);

	int deleteFrd(ArrayList<Friends> fArr) ;

	ArrayList<Conclusion> selectClass(String userId);

	int reportreset(Integer rpNo);
}

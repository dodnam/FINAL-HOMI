package finalProject.homis.hobbyFarm.message.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.vo.Message;

public interface MessageService {
	
	/* 받은 쪽지 리스트 조회*/
	int getListCount(String id);
	ArrayList<Message> selectList(PageInfo pi, String id);
	/* 보낸 쪽지 리스트 조회*/
	int sentListCount(String id);
	ArrayList<Message> selectsentList(PageInfo pi, String id);
	
	/* 쪽지 디테일 조회*/
	int updateMessageCount(Message message);
	Message selectMsg(Message message);
	
	/* 쪽지 삭제 */
	int deleteGetMsg(int mNo);
	int deleteSentMsg(int mNo);
	
	/* 쪽지 전송 */
		/* 닉네임, 회원종류 찾아오기 */ 
	Member findNick(String msg_to);
	Member findKind(String msg_to);
		/* 쪽지 전송 */
	int insertMsg(Message message);
	ArrayList<Member> selectFriendsList(String id);

}

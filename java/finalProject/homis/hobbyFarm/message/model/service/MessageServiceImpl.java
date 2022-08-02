package finalProject.homis.hobbyFarm.message.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.dao.MessageDAO;
import finalProject.homis.hobbyFarm.message.model.vo.Message;

@Service("msgService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDAO msgDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	// 쪽지 리스트 불러오기
	@Override
	public int getListCount(String id) {
		return msgDAO.getListCount(sqlSession, id);
	}
	@Override
	public ArrayList<Message> selectList(PageInfo pi, String id) {
		return msgDAO.selectList(sqlSession, pi, id);
	}
	@Override
	public int sentListCount(String id) {
		return msgDAO.sentListCount(sqlSession, id);
	}
	@Override
	public ArrayList<Message> selectsentList(PageInfo pi, String id) {
		return msgDAO.selectSentList(sqlSession, pi, id);
	}
	@Override
	public int updateMessageCount(Message message) {
		return msgDAO.updateMessageCount(sqlSession, message);
	}
	@Override
	public Message selectMsg(Message message) {
		return msgDAO.selectMsg(sqlSession, message);
	}
	@Override
	public Member findNick(String msg_to) {
		return msgDAO.findNick(sqlSession, msg_to);
	}
	@Override
	public Member findKind(String msg_to) {
		return msgDAO.findKind(sqlSession, msg_to);
	}
	
	@Override
	public int insertMsg(Message message) {
		return msgDAO.insertMsg(sqlSession, message);
	}
	@Override
	public int deleteGetMsg(int mNo) {
		return msgDAO.deleteGetMsg(sqlSession, mNo);
	}
	@Override
	public int deleteSentMsg(int mNo) {
		return msgDAO.deleteSentMsg(sqlSession, mNo);
	}
	@Override
	public ArrayList<Member> selectFriendsList(String id) {
		return msgDAO.selectFriendsList(sqlSession, id);
	}
}

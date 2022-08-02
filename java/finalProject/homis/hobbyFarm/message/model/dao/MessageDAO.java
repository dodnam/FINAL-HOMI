package finalProject.homis.hobbyFarm.message.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.vo.Message;

@Repository
public class MessageDAO {

	// '받은' 쪽지 리스트 불러오기
	public int getListCount(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("messageMapper.getListCount", id);
	}
	public ArrayList<Message> selectList(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("messageMapper.selectList", id, rowBounds);
	}
	public int sentListCount(SqlSessionTemplate sqlSession, String id) {
		return sqlSession.selectOne("messageMapper.sentListCount", id);
	}
	public ArrayList<Message> selectSentList(SqlSessionTemplate sqlSession, PageInfo pi, String id) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("messageMapper.selectSentList", id, rowBounds);
	}
	public int updateMessageCount(SqlSessionTemplate sqlSession, Message message) {
		return sqlSession.update("messageMapper.updateMessageCount", message);
	}
	public Message selectMsg(SqlSessionTemplate sqlSession, Message message) {
		return sqlSession.selectOne("messageMapper.selectMsg", message);
	}
	public Member findNick(SqlSessionTemplate sqlSession, String msg_to) {
		return sqlSession.selectOne("messageMapper.findNick", msg_to);
	}
	public Member findKind(SqlSessionTemplate sqlSession, String msg_to) {
		return sqlSession.selectOne("messageMapper.findKind", msg_to);
	}
	public int insertMsg(SqlSessionTemplate sqlSession, Message message) {
		return sqlSession.insert("messageMapper.insertMsg", message);
	}
	public int deleteGetMsg(SqlSessionTemplate sqlSession, int mNo) {
		return sqlSession.update("messageMapper.deleteGetMsg", mNo);
	}
	public int deleteSentMsg(SqlSessionTemplate sqlSession, int mNo) {
		return sqlSession.update("messageMapper.deleteSentMsg", mNo);
	}
	public ArrayList<Member> selectFriendsList(SqlSessionTemplate sqlSession, String id) {
		ArrayList<Member> list = (ArrayList)sqlSession.selectList("friendMapper.selectList", id);
		return list;
	}
}

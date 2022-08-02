package finalProject.homis.hobbyFarm.questions.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.questions.model.vo.Questions;

@Repository
public class QuestionDAO {

	public int getListCount(SqlSessionTemplate sqlSession, SearchSelect ss) {
		return sqlSession.selectOne("questionMapper.getListCount");
	}

	public ArrayList<Questions> selectList(SqlSessionTemplate sqlSession, PageInfo pi, SearchSelect ss) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("questionMapper.selectList", ss, rowBounds);
	}

	public int insertQuestion(SqlSessionTemplate sqlSession, Questions q) {
		return sqlSession.insert("questionMapper.insertQuestion", q);
	}
	public int insertQuestion2(SqlSessionTemplate sqlSession, Questions q) {
		return sqlSession.insert("questionMapper.insertQuestion2", q);
	}

	public int updateQuestionCount(SqlSessionTemplate sqlSession, Questions question) {
		return sqlSession.update("questionMapper.updateQuestionCount", question);
	}

	public Questions selectQuestion(SqlSessionTemplate sqlSession, Questions question) {
		return sqlSession.selectOne("questionMapper.selectQuestion", question);
	}

	public int updateQuestion(SqlSessionTemplate sqlSession, Questions question) {
		return sqlSession.update("questionMapper.updateQuestion", question);
	}

	public int reportChange(SqlSessionTemplate sqlSession, Questions question) {
		return sqlSession.update("questionMapper.reportChange", question);
	}

	public int deleteQuestion(SqlSessionTemplate sqlSession, Questions question) {
		return sqlSession.update("questionMapper.deleteQuestion", question);
	}

	public int addReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("questionMapper.addReply", r);
	}

	public ArrayList<Reply> selectReplyList(SqlSessionTemplate sqlSession, int post_no) {
		return (ArrayList)sqlSession.selectList("questionMapper.selectReplyList", post_no);
	}
	public int updateReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("questionMapper.updateReply", r);
	}
	public int deleteReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.update("questionMapper.deleteReply", r);
	}
	





}

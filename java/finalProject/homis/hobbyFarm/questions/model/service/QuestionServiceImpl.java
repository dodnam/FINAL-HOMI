package finalProject.homis.hobbyFarm.questions.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.questions.model.dao.QuestionDAO;
import finalProject.homis.hobbyFarm.questions.model.vo.Questions;

@Service("qService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDAO qDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int getListCount(SearchSelect ss) {
		return qDAO.getListCount(sqlSession, ss);
	}

	@Override
	public ArrayList<Questions> selectList(PageInfo pi, SearchSelect ss) {
		return qDAO.selectList(sqlSession, pi, ss);
	}

	@Override
	public int insertQuestion(Questions q) {
		return qDAO.insertQuestion(sqlSession, q);
	}
	@Override
	public int insertQuestion2(Questions q) {
		return qDAO.insertQuestion2(sqlSession, q);
	}
	
	@Override
	public int updateQuestionCount(Questions question) {
		return qDAO.updateQuestionCount(sqlSession, question);
	}
	@Override
	public Questions selectQuestion(Questions question) {
		return qDAO.selectQuestion(sqlSession, question);
	}

	@Override
	public int updateQuestion(Questions question) {
		return qDAO.updateQuestion(sqlSession, question);
	}

	@Override
	public int reportChange(Questions question) {
		return qDAO.reportChange(sqlSession, question);
	}

	@Override
	public int deleteQuestion(Questions question) {
		return qDAO.deleteQuestion(sqlSession, question);
	}

	@Override
	public ArrayList<Reply> selectReplyList(int post_no) {
		return qDAO.selectReplyList(sqlSession, post_no);
	}

	@Override
	public int addReply(Reply r) {
		return qDAO.addReply(sqlSession, r);
	}

	@Override
	public int updateReply(Reply r) {
		return qDAO.updateReply(sqlSession, r);
	}

	@Override
	public int deleteReply(Reply r) {
		return qDAO.deleteReply(sqlSession, r);
	}
	
}

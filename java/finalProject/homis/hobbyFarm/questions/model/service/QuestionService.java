package finalProject.homis.hobbyFarm.questions.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.questions.model.vo.Questions;

public interface QuestionService {

	int getListCount(SearchSelect ss);

	ArrayList<Questions> selectList(PageInfo pi, SearchSelect ss);

	int insertQuestion(Questions q); //board table
	int insertQuestion2(Questions q); //question table

	int updateQuestionCount(Questions question);

	Questions selectQuestion(Questions question);

	int updateQuestion(Questions question);

	int reportChange(Questions question);

	int deleteQuestion(Questions question);

	// 댓글
	ArrayList<Reply> selectReplyList(int post_no);
	int addReply(Reply r);
	int updateReply(Reply r);
	int deleteReply(Reply r);




}

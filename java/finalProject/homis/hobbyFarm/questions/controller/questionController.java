package finalProject.homis.hobbyFarm.questions.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.questions.model.exception.QuestionException;
import finalProject.homis.hobbyFarm.questions.model.service.QuestionService;
import finalProject.homis.hobbyFarm.questions.model.vo.Pagination;
import finalProject.homis.hobbyFarm.questions.model.vo.Questions;

@SessionAttributes("loginUser")
@Controller
public class questionController {
	
	@Autowired
	private QuestionService qService;
	
	@RequestMapping("qlist.qu")
	public ModelAndView questionList(@RequestParam(value="page", required=false) Integer page, 
									@RequestParam(value="sCategory", required=false) String sCategory, 
									@RequestParam(value="sContent", required=false) String sContent,
									ModelAndView mv) {
		// 현재 페이지 설정
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		// 검색 조건이 있다면 SearchSelect 에 설정하기
		SearchSelect ss = new SearchSelect();
		if(sCategory != null && sContent != null) {
			if(sCategory.equals("title")) { // 제목으로 찾는 경우
				ss.setTitle(sContent);
			} else if(sCategory.equals("writer")) { // 작성자(닉네임)으로 찾는 경우
				ss.setWriter(sContent);
			} else if(sCategory.equals("content")){ // 내용으로 찾는 경우
				ss.setContent(sContent);
			}
		}
		// 조건에 맞는 게시글 갯수 가져오기
		int listCount = qService.getListCount(ss);
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Questions> list = qService.selectList(pi, ss);
		
		if(list != null) {
			mv.addObject("list", list).addObject("pi", pi).setViewName("questionList");
		} else {
			throw new QuestionException("1:1 게시글 조회에 실패하였습니다.");
		}
		return mv;
	}
	
	
	@RequestMapping("qInsertView.qu")
	public String questionInsertView() {
		return "questionInsertForm";
	}
	
	@RequestMapping("qinsert.qu")
//	public String questionInsert(@ModelAttribute Questions q) {
		public String questionInsert(String title, String writer, String content) {
		Questions q = new Questions();
		q.setTitle(title);
		q.setWriter(writer);
		if(content == null) {
			q.setContent("");
		} else {
			q.setContent(content);
		}
		int result = qService.insertQuestion(q);
		
		if(result > 0) {
			int result2 = qService.insertQuestion2(q);
			if(result2 > 0) {
				return "redirect:qlist.qu";
			} else {
				throw new QuestionException("게시글 등록에 실패하였습니다.");
			}
		} else {
			throw new QuestionException("게시글 등록에 실패하였습니다.");
		}
	}
	
	@RequestMapping("qdetail.qu")
	public ModelAndView questionDetail(ModelAndView mv, int post_no, Integer page) {
		Questions question = new Questions();
		question.setPost_no(post_no);
		
		int result = qService.updateQuestionCount(question);
		
		if(result > 0) {
			question = qService.selectQuestion(question);
		} else {
			throw new QuestionException("게시글 조회수 변경에 실패하였습니다.");
		}
		
		if(question != null) {
			mv.addObject("page", page).addObject("question", question).setViewName("questionDetail");
			return mv;
		} else {
			throw new QuestionException("게시글 상세 조회에 실패하였습니다.");
		}
	}
	
	@RequestMapping("qupdateView.qu")
	public ModelAndView questionUpdateView(ModelAndView mv, int post_no, Integer page) {
		Questions question = new Questions();
		question.setPost_no(post_no);
		
		question = qService.selectQuestion(question);
		
		mv.addObject("question", question).addObject("page", page).setViewName("questionUpdateForm");
		
		return mv;
	}
	
	@RequestMapping("qupdate.qu")
	public ModelAndView questionUpdate(ModelAndView mv, Questions question, int post_no, Integer page) {
		question.setPost_no(post_no);
		int result = qService.updateQuestion(question);
		
		if(result > 0) {
			mv.addObject("page", page).addObject("question", question).setViewName("redirect:qdetail.qu?post_no=" + post_no);
		} else {
			throw new QuestionException("게시글 수정을 실패하였습니다.");
		}
		return mv;
	}
	
	@RequestMapping("qdelete.qu")
	public ModelAndView questionDelete(ModelAndView mv, int post_no, Integer page) {
		Questions question = new Questions();
		question.setPost_no(post_no);
		
		int result = qService.deleteQuestion(question);
		
		if(result > 0) {
			mv.addObject("page", page).setViewName("redirect:qlist.qu");
		} else {
			throw new QuestionException("게시글 삭제를 실패했습니다.");
		}
		return mv;
	}
	
	@RequestMapping("reportCng.qu")
	public void reportChange(HttpServletResponse response, @RequestParam("post_no") int post_no) {
		Questions question = new Questions();
		question.setPost_no(post_no);
		question.setReport_yn("Y");
		
		int result = qService.reportChange(question);
		
		if(result > 0) {
		} else {
			throw new QuestionException("답변 완료 처리에 실패했습니다.");
		}
	}
	
	@RequestMapping("addReply.qu")
	public void addReply(HttpServletResponse response, HttpSession session,
						@RequestParam("post_no") int post_no, 
						@RequestParam("rContent") String rContent) {
		Reply r = new Reply();
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		r.setRefPno(post_no);
		r.setrContent(rContent);
		r.setrWriter(id);
		
		int result = qService.addReply(r);
		
		if(result > 0) {
		} else {
			throw new QuestionException("댓글 등록에 실패했습니다.");
		}
	}
	
	@RequestMapping("rList.qu")
	public void getReplyList(HttpServletResponse response,
							 @RequestParam("post_no") int post_no) throws JsonIOException, IOException {
		ArrayList<Reply> rList = qService.selectReplyList(post_no);
		
		for(Reply r : rList) {
			r.setrNickName(URLEncoder.encode(r.getrNickName(), "utf-8"));
			r.setrContent(URLEncoder.encode(r.getrContent(), "utf-8"));
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(rList, response.getWriter());
	}
	
	@RequestMapping("updateReply.qu")
	public void updateReply(HttpServletResponse response,
							@RequestParam("rNo") int rNo, @RequestParam("rContent") String rContent) {
		Reply r = new Reply();
		r.setrNo(rNo);
		r.setrContent(rContent);
		
		int result = qService.updateReply(r);
		
		if(result > 0) {
		} else {
			throw new QuestionException("댓글 수정에 실패했습니다.");
		}
	}
	
	@RequestMapping("deleteReply.qu")
	public void deleteReply(HttpServletResponse response,
			@RequestParam("rNo") int rNo) {
		Reply r = new Reply();
		r.setrNo(rNo);
		
		int result = qService.deleteReply(r);
		
		if(result > 0) {
		} else {
			throw new QuestionException("댓글 삭제에 실패했습니다.");
		}
	}
	
	
}

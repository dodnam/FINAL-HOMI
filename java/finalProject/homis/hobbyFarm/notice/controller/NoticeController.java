package finalProject.homis.hobbyFarm.notice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.notice.Pagination;
import finalProject.homis.hobbyFarm.notice.model.exception.NoticeException;
import finalProject.homis.hobbyFarm.notice.model.service.NoticeService;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;


@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService nService;
	
	@RequestMapping("nlist.no")
	public ModelAndView noticeList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = nService.getListCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Notice> list = nService.selectList(pi);
	
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("noticeList");
			
		} else {
			throw new NoticeException("게시글 전체 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping("ninsertView.no")
	public String noticeInsertView() {
		return "noticeInsert";
	}
	
	// 제목, 내용
	@RequestMapping("ninsert.no")
	public String noticeInsert(@ModelAttribute Notice n, HttpServletRequest request) {
		
		int result = nService.insertNotice(n);
		
		if(result > 0) {
			return "redirect:nlist.no";
		} else {
			throw new NoticeException("공지사항 등록에 실패하였습니다.");
		}
	}
	
	@RequestMapping("ndetail.no")
	public ModelAndView noticeDetail(@RequestParam("post_no") int post_no, @RequestParam("page") int page, ModelAndView mv) {
		
		Notice notice = nService.selectNotice(post_no);
		if(notice != null) {
			mv.addObject("notice", notice);
			mv.addObject("page", page);
			mv.setViewName("noticeDetail");
			int cnt = nService.addReadCount(post_no);
		} else {
			throw new NoticeException("공지사항 상세보기를 실패하였습니다.");
		}
		return mv;
	}
	
	@RequestMapping("nupView.no")
	public ModelAndView noticeUpdateView(@RequestParam("post_no") int post_no, @RequestParam("page") int page, ModelAndView mv) {
		Notice notice = nService.selectNotice(post_no);
		mv.addObject("notice", notice)
		  .addObject("page", page)
		  .setViewName("noticeUpdate");
		
		return mv;
	}
	
	@RequestMapping("nupdate.no")
	public ModelAndView noticeUpdate(@ModelAttribute Notice n, @RequestParam("page") int page, ModelAndView mv) {
		
		if(n.getPost_fix() == null) {
			n.setPost_fix("N");
		}
		
		int result = nService.updateNotice(n);
		if(result > 0) {
			mv.addObject("page", page)
			  .setViewName("redirect:ndetail.no?post_no=" + n.getPost_no());
		} else {
			throw new NoticeException("공지사항 수정을 실패하였습니다.");
		}
		return mv;
	}
	
	@RequestMapping("ndelete.no")
	public ModelAndView noticeDelete(ModelAndView mv, int post_no, Integer page) {
		Notice notice = new Notice();
		notice.setPost_no(post_no);
		
		int result = nService.deleteNotice(post_no);
		
		if(result > 0) {
			mv.addObject("page", page);
			mv.setViewName("redirect:nlist.no");
		} else {
			throw new NoticeException("공지사항 삭제에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping("nSearchList.no")
	public ModelAndView noticeSearchList(@RequestParam(value="page", required=false) Integer page,
										 @RequestParam("nSelect") String nSelect, @RequestParam("nWord") String nWord,
										 ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		
		int searchListCount = 0;
		if(nSelect.equals("title")) {
			searchListCount = nService.getTitleListCount(nWord);
		} else {
			searchListCount = nService.getContentListCount(nWord);
		}
		
		ArrayList<Notice> searchList = null;
		PageInfo pi = Pagination.getPageInfo(currentPage, searchListCount);
		
		if(nSelect.equals("title")) {
			searchList = nService.selectTitleList(pi, nWord);
		} else{
			searchList = nService.selectContentList(pi, nWord);
		}
		
		if(searchList != null) {
			mv.addObject("list", searchList)
			  .addObject("pi", pi)
			  .addObject("nSelect", nSelect)
			  .addObject("nWord", nWord)
			  .setViewName("noticeList");
		} else {
			throw new NoticeException("공지사항 검색 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

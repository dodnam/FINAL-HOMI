package finalProject.homis.hobbyFarm.management.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.management.Pagination;
import finalProject.homis.hobbyFarm.management.model.exception.ManagementException;
import finalProject.homis.hobbyFarm.management.model.service.ManagementService;
import finalProject.homis.hobbyFarm.member.model.exception.MemberException;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.notice.model.exception.NoticeException;

@Controller
public class ManagementController {
	
	@Autowired
	private ManagementService maService;
	
	@RequestMapping("mlist.ma")
	public ModelAndView managementList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = maService.getListCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Member> list = maService.selectList(pi);
		
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("management");
		} else {
			throw new MemberException("회원 조회에 실패하였습니다.");
		}
		
		return mv;
	}
	
	@RequestMapping("madelete.ma")
	public String deleteUser(@RequestParam("mNo") String mNo, HttpServletRequest request) {
		
		ArrayList<String> list = new ArrayList<String>();
		String[] userArr = mNo.split(",");
		
		for(int i=0; i<userArr.length; i++) {
			list.add(userArr[i]);
		}
		
		int result = maService.deleteUser(list);
		
		if(result == userArr.length) {
			return "redirect:mlist.ma";
		} else {
			throw new ManagementException("회원 탈퇴에 실패하였습니다.");
		}
	}
	
	@RequestMapping("maSearchList.ma")
	public ModelAndView noticeSearchList(@RequestParam(value="page", required=false) Integer page,
										 @RequestParam("searchSelect") String searchSelect, @RequestParam("word") String word,
										 ModelAndView mv) {
		SearchSelect ss = new SearchSelect();
		
		if(searchSelect.equals("selectId")) {
			ss.setUserId(word);
		} else if(searchSelect.equals("selectNick")) {
			ss.setNickName(word);
		} else {
			ss.setUserName(word);
		}
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int searchListCount = 0;
		
		searchListCount = maService.getSelectListCount(ss);
		
		ArrayList<Member> searchList = null;
		PageInfo pi = Pagination.getPageInfo(currentPage, searchListCount);
		
		searchList = maService.selectList(pi, ss);
		
		if(searchList != null) {
			mv.addObject("list", searchList)
			.addObject("pi", pi)
			.addObject("searchSelect", searchSelect)
			.addObject("word", word)
			.setViewName("management");
		} else {
			throw new NoticeException("회원 검색 조회에 실패하였습니다.");
		}
		
		return mv;
}
	
}

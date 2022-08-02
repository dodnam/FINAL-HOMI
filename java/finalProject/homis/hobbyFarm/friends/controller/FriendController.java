package finalProject.homis.hobbyFarm.friends.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.exception.FriendsException;
import finalProject.homis.hobbyFarm.friends.model.service.FriendService;
import finalProject.homis.hobbyFarm.friends.model.vo.Friends;
import finalProject.homis.hobbyFarm.friends.model.vo.Pagination;
import finalProject.homis.hobbyFarm.friends.model.vo.Report;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.service.MessageService;

@SessionAttributes("loginUser")
@Controller
public class FriendController {
	@Autowired
	private FriendService fService;
	@Autowired
	private MessageService msgService;
	
	@RequestMapping("friend.fo")
	public ModelAndView friendList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv, HttpSession session) {		
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = fService.getListCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Member> list = fService.selectList(pi, id); 
		if(list != null) {
	         mv.addObject("list", list);
	         mv.addObject("pi", pi);
	         mv.setViewName("friendList");
	      } else {
	         throw new FriendsException("ģ�� ��ü ��ȸ�� �����Ͽ����ϴ�.");
	      }
		
		return mv;
	}
	
	@RequestMapping("search.fo")
	public ModelAndView friendSearch(@RequestParam(value="page", required=false) Integer page, ModelAndView mv, HttpSession session) {
		
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		
		if(page != null) {
			currentPage = page;
		}
		int listCount = fService.getSearchList(id);
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Member> list = fService.searchList(pi, id);
		
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("friendSearch");
		} else {
			throw new FriendsException("ȸ�� ��ü ��ȸ�� �����Ͽ����ϴ�.");
		}

		return mv;
	}
	
	@RequestMapping("userInfo.fo")
	   public ModelAndView userInfo(@RequestParam(value="userId", required=false) String userId , 
	                         @RequestParam(value="nickName", required=false) String nickName,
	                        @RequestParam("page") Integer currentPage, 
	                        ModelAndView mv, HttpSession session) {

	      // �г������� ���̵� ã��
	      if(userId == null && nickName != null) {
	         userId = msgService.findNick(nickName).getUserId();
	      }
	      
	      Member m = fService.selectUser(userId);
	      System.out.println("�ɹ��� ���� �� �ñ�" + m);
	      ArrayList<Conclusion> cList = fService.selectClass(userId);
	      
	      for(Conclusion i : cList)
	    	  i.setStartDate(i.getStartDate().substring(0,10)) ;
	      if(m != null) {
	         mv.addObject("member", m);
	         mv.addObject("cList", cList);
	         mv.addObject("page", currentPage);
	         mv.setViewName("miniMyPage");
	      } else {
	         throw new FriendsException("ȸ������ ��ȸ ����");
	      }
	      
	      return mv;
	   }
	
	@RequestMapping("friendSearch.fo")
	public ModelAndView friendSearch(@RequestParam(value="page", required=false) Integer page, @RequestParam("searchValue") String searchValue, ModelAndView mv) {
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int searchListFCount = 0;
		if(searchValue != null) {
			searchListFCount = fService.getSearchListFCount(searchValue);
		}
		
		ArrayList<Member> searchFList = null;
		PageInfo pi = Pagination.getPageInfo(currentPage, searchListFCount);
		
		if(searchValue != null) {
			searchFList = fService.selectFList(pi, searchValue);
		}
		if(searchFList != null) {
			mv.addObject("list", searchFList).addObject("pi", pi).setViewName("friendSearch");
		} else {
	         throw new FriendsException("ģ�� �˻��� �����Ͽ����ϴ�.");
	    }
		
		return mv;
	}
	
	@RequestMapping("request.fo")
	@ResponseBody
	public String friendRequest(HttpSession session, @RequestParam("userId") String userId) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		 
		Friends f = new Friends();
		f.setFdFrom(id);
		f.setFdTo(userId);
		
		int result = fService.fRequest(f);
		if(result > 0) {
			return "success";
		} else {
			throw new FriendsException("ģ�� ��û ����");
		}
		
	}
	
	@RequestMapping("reSet.fo")
	@ResponseBody
	public String friendReSet(HttpSession session, @RequestParam("userId") String userId) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		 
		Friends f = new Friends();
		f.setFdFrom(id);
		f.setFdTo(userId);
		
		int result = fService.fReSet(f);
		if(result > 0) {
			return "success";
		} else {
			throw new FriendsException("ģ�� ��û ����");
		}
		
	}
	
	@RequestMapping("requestpage.fo")
	public ModelAndView friendRequestPage(@RequestParam(value="page", required=false) Integer page, ModelAndView mv, HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = fService.getListCountR(id);
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);	
		
		ArrayList<Member> list = fService.fRequestPage(pi, id);
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("friendRequest");
		} else {
			throw new FriendsException("ģ�� ��û ����");
		}
		
		return mv;
	}
	
	@RequestMapping("reSetRequest.fo")
	@ResponseBody
	public String friendReSetRequest(HttpSession session, @RequestParam("userId") String userId) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		 
		Friends f = new Friends();
		f.setFdFrom(userId);
		f.setFdTo(id);
		
		int result = fService.fReSetRequest(f);
		if(result > 0) {
			return "success";
		} else {
			throw new FriendsException("���� ����");
		}
		
	}
	
	
	@RequestMapping("consend.fo")
	@ResponseBody
	public String friendConsend(HttpSession session, @RequestParam("userId") String userId) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		 
		Friends f = new Friends();
		f.setFdFrom(userId);
		f.setFdTo(id);
		
		int result = fService.fConsend(f);
		if(result > 0) {
			return "success";
		} else {
			throw new FriendsException("���� ����");
		}
		
	}
	
	@RequestMapping("report.fo")
	public ModelAndView rePort(@RequestParam("userId") String userId , @RequestParam("page") Integer currentPage, ModelAndView mv) {
		
		Member m = fService.selectUser(userId);
		
		if(m != null) {
			mv.addObject("member", m);
			mv.addObject("page", currentPage);
			mv.addObject("loginUser");
			mv.setViewName("report");
		} else {
			throw new FriendsException("ȸ������ ��ȸ ����");
		}
		
		return mv;
	}
	
	@RequestMapping("reportGo.fo")
	public ModelAndView reportGo(HttpSession session, @RequestParam("userId") String userId, @RequestParam("url") String url, @RequestParam("reason") String reason,  ModelAndView mv) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		Report r = new Report();
		r.setRpSender(id);
		r.setRpReason("#null");
		r.setRpReciever(userId);
		r.setRpDetail(reason);
		r.setRpUrl(url);
		
		int result = fService.reportGo(r);
		
		if(result > 0) {
			mv.setViewName("rportClose");
		} else {
			throw new FriendsException("�Ű� ����");
		}
		return mv;
	}
	
	@RequestMapping("reportList.fo")
	public ModelAndView reportList(HttpSession session, @RequestParam(value="page", required=false) Integer page, ModelAndView mv) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = fService.getRCount();
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);	
		
		ArrayList<Report> list = fService.rePortPage(pi);
		if(list != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.setViewName("reportList");
		} else {
			throw new FriendsException("�Ű���Ʈ ��û ����");
		}
		
		return mv;
	}
	
	@RequestMapping("checkOn.fo")
	@ResponseBody
	public String checkOn(HttpSession session, @RequestParam("rpNo") String rpNo, @RequestParam("rpReciever") String rpReciever, @RequestParam("rpCount") int rpCount) {
		int result = fService.pRequest(rpNo);
		
		if(result > 0) {
			Report r = new Report();
			r.setRpReciever(rpReciever);
			r.setRpCount(rpCount+1);
			fService.rpCount(r);
			return "success";
		} else {
			throw new FriendsException("ģ�� ��û ����");
		}
	}
	
	@RequestMapping("deleteFrd.fo")
	public void deleteFriends(HttpServletResponse response,
							  String[] userId,
							  HttpSession session) throws IOException {
		String id = ((Member)session.getAttribute("loginUser")).getUserId() ;
		ArrayList<Friends> fArr = new ArrayList<Friends>() ;
		for(int i=0; i<userId.length; i++) {
			Friends f = new Friends() ;
			f.setFdFrom(id) ;
			f.setFdTo(userId[i]) ;
			fArr.add(f) ;
		}
		
		int result = fService.deleteFrd(fArr) ;
		
		if(result>0) {
			response.getWriter().print(true) ;
		} else {
			response.getWriter().print(false) ;
		}
	}
	
	@RequestMapping("reportreset.fo")
	@ResponseBody
	public String reportreset(HttpSession session, @RequestParam("rpNo") Integer rpNo) {
		int result = fService.reportreset(rpNo);
		
		if(result > 0) {
			return "success";
		} else {
			throw new FriendsException("�Ű� ���� ����");
		}
	}
	
}

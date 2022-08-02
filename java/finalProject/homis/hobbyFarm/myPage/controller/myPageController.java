
package finalProject.homis.hobbyFarm.myPage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.Pagination;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.service.FriendService;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.lecture.model.exception.LectureBoardException;
import finalProject.homis.hobbyFarm.lecture.model.service.LectureBoardService;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.member.model.service.MemberService;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.service.MessageService;
import finalProject.homis.hobbyFarm.myPage.model.exception.myPageException;
import finalProject.homis.hobbyFarm.myPage.model.service.myPageService;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;
import finalProject.homis.hobbyFarm.myPage.model.vo.myPageCount;

@SessionAttributes("loginUser")
@Controller
public class myPageController {
	@Autowired
	private MemberService mService;
	@Autowired
	private LectureBoardService lbService;
	@Autowired
	private myPageService mpService;
	@Autowired
	private MessageService msgService;
	@Autowired
	private FriendService fService;
	
	/* 강사 페이지 */
	@RequestMapping("teacherPage.mp")
	public ModelAndView teacherPage(String msg, ModelAndView mv, HttpSession session, HttpServletRequest request) {
		// 가져와야 할 것
				/*
					1. 운영중인 텃밭 수
					2. 참여중인 모임 수 GFAPP_VIEW 에서 where user_id = #{id}로 찾아와서 count 하기
					3. 기간 끝난 텃밭, 모임 수 GFVIEW 에서 ENDDATE가 있는데 거기서 오늘날짜랑 비교해서 넣기
					
					5. 내 일일 시간표(페이징)
						* 뷰를 어떻게 짜지!
						* 강사 로그인 아이디, 날짜로 우선 뽑고
						* 시간에 따라 칸을 먼저 나누고
						* 각 칸(시간)에 정보 집어넣기?
				*/
		if(msg != null) {
			msg = "VIP 업그레이드 완료!";
		} else {
			msg = null;
		}
		mv.addObject("msg", msg);
		
		myPageCount mpc = new myPageCount();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		String nick = loginUser.getNickName(); // 로그인 닉네임(강사 닉네임)
		String id = loginUser.getUserId(); // 로그인 아이디
		
		int runningFarm = mpService.getRunningFarm(nick); // 1. 운영중인 텃밭 수
		mpc.setMyLectureCount(runningFarm);
		int runningGroup = mpService.getRunningGroup(id); // 2. 참여중인 모임 수
		mpc.setMyFarmCount(runningGroup);
//		int finished = mpService.getFinished(id); // 3. 기간 끝난 텃밭 + 모임 수
		mv.addObject("loginUser", loginUser);
		mv.addObject("mpc", mpc);
		mv.setViewName("teacherPage");
		return mv;
	}
	@RequestMapping("lectureList.mp") // 내 강의 리스트 frame
	public ModelAndView lecturePage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
		String id = ((Member)session.getAttribute("loginUser")).getNickName(); // 로그인 닉네임(강사 닉네임)
		
		Search search = new Search("", "writer", id, 0);
		int listCount = lbService.selectLBCount(search);
		
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		mv.addObject("pi",pi);
		
		ArrayList<LectureBoard> lbList = lbService.selectLBPage(pi,search);
		if(lbList != null) {
			mv.addObject("lbList", lbList);
		} else {
			throw new LectureBoardException("lbList 가져오기 실패");
		}
		mv.setViewName("lectureListFrame");
		return mv;
	}
	
	@RequestMapping("timeLine.mp")
	public ModelAndView timeLine(@RequestParam(value="day", required=false) String day, ModelAndView mv, HttpSession session) {
		SimpleDateFormat sysdate = new SimpleDateFormat ( "yyyyMMdd"); //현재 날짜 뽑기
		Date time = new Date();
		String date = sysdate.format(time); // date에 "yyyyMMdd" 담김
		// 날짜 가져오기
		if(day != null) {
			date = day; // day가 null이 아니면 date에 값 넣음
		}
		Timeline tl = new Timeline();
		tl.setDate(date); // 날짜 설정
		String id = ((Member)session.getAttribute("loginUser")).getUserId(); // 로그인 아이디(강사 아이디) 가져옴
		tl.setUserId(id); // 강사 아이디 설정
		
		ArrayList<Timeline> timeline = mpService.getTimeline(tl); // id와 date를 담은 객체를 보내 timeline에 필요한 정보 받아옴
		
		if(timeline != null) {
			mv.addObject("date", date); //내가 선택했던 날짜 그대로 다시 돌려보내야하기 때문에 보냄
			mv.addObject("timeline", timeline);
			mv.setViewName("timeLine");
		} else {
			throw new myPageException("시간표 열람 실패!");
		}
		return mv;
	}
	
	/* 강사 페이지 끝 */
	
	/* 학생 페이지 */
	@RequestMapping("studentPage.mp")
	public ModelAndView studentPage(ModelAndView mv, HttpSession session, HttpServletRequest request) {
		myPageCount mpc = new myPageCount();
		String nick = ((Member)session.getAttribute("loginUser")).getNickName(); // 로그인 닉네임(학생 닉네임)
		String id = ((Member)session.getAttribute("loginUser")).getUserId(); // 로그인 아이디
		
		int runningGroup = mpService.getListCount(id);  // 1. 참여중인 모임 수
		mpc.setMyFarmCount(runningGroup);
		int runningLecture = mpService.getRunningLecture(id); // 2. 참여중인 강의 수
		mpc.setStLectureCount(runningLecture);
		int finished = mpService.getFinished(id); // 3. 기간 끝난 텃밭 + 모임 수
		mpc.setFinishedLectureCount(finished);
		mv.addObject("mpc", mpc);
		mv.setViewName("studentPage");
		return mv;
	}
	@RequestMapping("myFarm.mp")
	public ModelAndView myFarm(ModelAndView mv, HttpSession session,
								@RequestParam(value="page", required=false) Integer page) {
		/* 현재 페이지 설정 */
		int currentPage = 1;
		if(page != null) {
			currentPage = page;
		}
		/* 학생 아이디에 맞는 게시글 갯수 가져오기 */
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int listCount = mpService.getListCount(id);
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<GroupFarmBoard> gfList = mpService.myFarm(pi, id);
		if(gfList != null) {
			mv.addObject("gfList", gfList);
			mv.addObject("pi", pi);
			mv.setViewName("myFarmList");
		} else {
			throw new myPageException("gfList 가져오기 실패");
		}
		
		return mv;
	}
	@RequestMapping("myLectureSt.mp")
	public ModelAndView myLectureSt(ModelAndView mv, HttpSession session) {
		// 학생 아이디를 보내서 학생이 속하고있는 강의 리스트 가져오기
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		ArrayList<LectureBoard> lbList = mpService.myLectureSt(id);
		if(lbList != null) {
			mv.addObject("lbList", lbList);
			mv.setViewName("myLectureList");
		} else {
			throw new myPageException("lbList 가져오기 실패");
		}
		return mv;
	}
	@RequestMapping("myFinishedLectureSt.mp")
	public ModelAndView myFinishedLectureList(ModelAndView mv, HttpSession session) {
		// 학생 아이디를 보내서 학생이 끝낸 강의 리스트 가져오기
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		ArrayList<LectureBoard> lbList = mpService.myFinishedLectureList(id);
		if(lbList != null) {
			mv.addObject("lbList", lbList);
			mv.setViewName("myFinishedLectureList");
		} else {
			throw new myPageException("lbList 가져오기 실패");
		}
		return mv;
	}
	/* 학생 페이지 끝 */
	
	/* 결제 관련 */
	@RequestMapping("payView.mp")
	public String payView() {
		return "upgradeGradeForm";
	}
	@RequestMapping("upgradeVip.mp")
	public void upgradeVip(HttpSession session, Model model, SessionStatus status) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId(); // 로그인 아이디
		int result = mpService.upgradeVip(id);
		if(result > 0) {
		} else {
			throw new myPageException("회원 업그레이드 실패");
		}
	}
	@RequestMapping("goUp.mp")
	public String goUp(HttpSession session, Model model) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId(); // 로그인 아이디
		Member m = new Member() ;
		m.setUserId(id);
		Member loginUser = mService.memberLogin(m);
		model.addAttribute("loginUser", loginUser);
		
		return "redirect:teacherPage.mp";
	}
	/* 결제 관련 끝 */
}

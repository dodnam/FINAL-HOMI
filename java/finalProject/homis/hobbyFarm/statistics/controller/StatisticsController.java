package finalProject.homis.hobbyFarm.statistics.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.sun.net.httpserver.Authenticator.Success;

import finalProject.homis.hobbyFarm.member.model.service.MemberService;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.statistics.model.service.StatisticsService;
import finalProject.homis.hobbyFarm.statistics.model.vo.Statistics;

@Controller
public class StatisticsController {
	
	@Autowired
	private StatisticsService sService;
	
	@Autowired
	private MemberService mService;
	
	
	@RequestMapping("statistics.st")
	public ModelAndView mainStatistics(ModelAndView mv,  Member m, HttpSession session) throws JsonIOException, IOException {
		
		int memberCount = sService.getMemberCount(); // 회원 수
		int lectureCount = sService.getLectureCount(); // 강의 수
		int teacherCount = sService.getTeacherCount(); // 강사 수
		int studentCount = sService.getStudentCount(); // 학생 수
		int visitCount = sService.getVisitCount(); // 방문자 수
		
		// 강의 수
		int sportsCount = sService.getSportsCount(); // 스포츠
		int cookCount = sService.getCookCount(); // 요리
		int pictureCount = sService.getPictureCount(); // 미디어
		int designCount = sService.getDesignCount(); // 디자인
		int musicCount = sService.getMusicCount(); // 음악
		int craftsCount = sService.getCraftsCount(); // 공예
		int artCount = sService.getArtCount(); // 예술
		int beautyCount = sService.getBeautyCount(); // 미용
		int languageCount = sService.getLanguageCount(); // 언어
		int otherCount = sService.getOtherCount(); // 기타
		
		
		// 성사된 강의 수
		
		int sportsCount2 = sService.getSportsCount2(); // 스포츠
		int cookCount2 = sService.getCookCount2(); // 요리
		int pictureCount2 = sService.getPictureCount2(); // 미디어
		int designCount2 = sService.getDesignCount2(); // 디자인
		int musicCount2 = sService.getMusicCount2(); // 음악
		int craftsCount2 = sService.getCraftsCount2(); // 공예
		int artCount2 = sService.getArtCount2(); // 예술
		int beautyCount2 = sService.getBeautyCount2(); // 미용
		int languageCount2 = sService.getLanguageCount2(); // 언어
		int otherCount2 = sService.getOtherCount2(); // 기타
		
		if(memberCount != 0) {
			mv.addObject("memberCount", memberCount);
			mv.addObject("lectureCount", lectureCount);
			mv.addObject("visitCount", visitCount);
			mv.addObject("teacherCount", teacherCount);
			mv.addObject("studentCount", studentCount);
			mv.addObject("artCount", artCount);
			mv.addObject("craftsCount", craftsCount);
			mv.addObject("musicCount", musicCount);
			mv.addObject("designCount", designCount);
			mv.addObject("pictureCount", pictureCount);
			mv.addObject("cookCount", cookCount);
			mv.addObject("sportsCount", sportsCount);
			mv.addObject("beautyCount", beautyCount);
			mv.addObject("languageCount", languageCount);
			mv.addObject("otherCount", otherCount);
			mv.addObject("artCount2", artCount2);
			mv.addObject("craftsCount2", craftsCount2);
			mv.addObject("musicCount2", musicCount2);
			mv.addObject("designCount2", designCount2);
			mv.addObject("pictureCount2", pictureCount2);
			mv.addObject("cookCount2", cookCount2);
			mv.addObject("sportsCount2", sportsCount2);
			mv.addObject("beautyCount2", beautyCount2);
			mv.addObject("languageCount2", languageCount2);
			mv.addObject("otherCount2", otherCount2);
			mv.setViewName("statistics");
		}
		
		return mv;
	}
	
	@RequestMapping("tableList.st")
	public void tableList(HttpServletResponse response, HttpServletRequest request) throws JsonIOException, IOException {
		
		/*String selectYear = request.getParameter("selectYear");
		String selectMonth = request.getParameter("selectMonth");

		String yearMonth = selectYear + selectMonth;*/
		String yearMonth = request.getParameter("sysdate");
		
		ArrayList<Statistics> list = sService.selectList(yearMonth);
		for(int i = 0; i < list.size(); i++) {
			
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(list, response.getWriter());
		
	}
	
	
}

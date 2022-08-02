package finalProject.homis.hobbyFarm;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.lecture.model.service.LectureBoardService;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.Pagination;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private LectureBoardService lbService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
	@RequestMapping("newList.do")
	@ResponseBody
	public ArrayList<LectureBoard> newList(HttpServletResponse response,
										   HttpServletRequest request) throws JsonIOException {
		Search search = new Search("", "", "", 0) ;
		int listCount = lbService.selectLBCount(search) ;
		int currentPage = 1 ;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount) ;
		ArrayList<LectureBoard> list = lbService.selectLBPage(pi,search) ;
		return list ;
	}
	
	@RequestMapping("rstList.do")
	@ResponseBody
	public ArrayList<LectureBoard> rstList(HttpServletResponse response,
										   HttpServletRequest request) throws JsonIOException {
		int listCount = lbService.selectCCCount() ;
		int currentPage = 1 ;
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount) ;
		ArrayList<LectureBoard> list = lbService.selectCCPage(pi) ;

		return list ;
	}
}

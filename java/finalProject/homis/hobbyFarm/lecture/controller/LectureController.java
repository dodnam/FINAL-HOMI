package finalProject.homis.hobbyFarm.lecture.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.lecture.model.vo.Search;
import finalProject.homis.hobbyFarm.myPage.model.vo.Timeline;
import finalProject.homis.hobbyFarm.lecture.model.exception.LectureBoardException;
import finalProject.homis.hobbyFarm.lecture.model.service.LectureBoardService;
import finalProject.homis.hobbyFarm.lecture.model.vo.Conclusion;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.lecture.model.vo.Pagination;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import oracle.sql.TIMESTAMPLTZ;

@Controller
public class LectureController implements Comparator<ArrayList<String>>{
	
	
	@Autowired
	private LectureBoardService lbService;
	
	@Override
	public int compare(ArrayList<String> o1, ArrayList<String> o2) {
		if(o1.get(0) == o2.get(0)) {
			return o1.get(1).compareTo(o2.get(1));
		} else {
			return o1.get(0).compareTo(o2.get(0));
		}
	}
	
	
	
	@RequestMapping("insertLecture.lec")
	public String insertLec(@ModelAttribute LectureBoard lb,HttpServletRequest request, @RequestParam("thumbnailImg") MultipartFile thumbnail, @ModelAttribute Member loginUser,
						  @RequestParam("allDate") ArrayList<String> allDate) {
		
		ArrayList<String> sidoArr = new ArrayList<String>();
		for(int i = 0; ;i++) {
			if(!request.getParameter("sido"+i).equals("")) {
				sidoArr.add(request.getParameter("sido"+i));
			} else {
				break;
			}
		}
		
		ArrayList<String> gugunArr = new ArrayList<String>();
		for(int i = 0; ;i++) {
			if(!request.getParameter("gugun"+i).equals("")) {
				gugunArr.add(request.getParameter("gugun"+i));
			} else {
				break;
			}
		}
		
		ArrayList<String[]> dongArr = new ArrayList<String[]>();
		for(int i = 0; ; i++) {
			if(request.getParameterValues("dong"+i) != null) {
				dongArr.add(request.getParameterValues("dong"+i));
			} else {
				break;
			}
		}
		
		ArrayList<ArrayList<String>> APList = new ArrayList<>();
		for(int i = 0; i < sidoArr.size(); i++) {
			ArrayList<String> nowArr = new ArrayList<>();
			nowArr.add(sidoArr.get(i));
			nowArr.add(gugunArr.get(i));
			for(int j = 0; j < dongArr.get(i).length; j++) {
				nowArr.add(dongArr.get(i)[j]);
			}
			APList.add(nowArr);
		}
		
		String ablePlace = "";
		for(int i = 1; i < APList.size(); i++) {
			Collections.sort(APList, new Comparator<ArrayList<String>>() {
				@Override
				public int compare(ArrayList<String> o1, ArrayList<String> o2) {
					if(o1.get(0).equals(o2.get(0))) {
						return o1.get(1).compareTo(o2.get(1));
					} else {
						return o1.get(0).compareTo(o2.get(0));
					}
				}
			});
		}
		
		
		for(int i = 0; i < APList.size(); i++) {
			for(int j = 0; j < APList.get(i).size(); j++) {
				if(i != 0 && j == 0) {
					ablePlace += "/"+APList.get(i).get(j)+",";
				} else if(j == ( APList.get(i).size()-1 )){
					ablePlace += APList.get(i).get(j);
				} else {
					ablePlace += APList.get(i).get(j)+",";
				}
			}
		}
		
		String ableTime = "";
		ArrayList<ArrayList<String>> time = new ArrayList<ArrayList<String>>();
		int j = -1;
		int k = 0;
		for(int i = 0; i < allDate.size(); i++) {
			if(allDate.get(i).length() > 2) {
				j++;
				k = 0;
				time.add(new ArrayList<String>());
				time.get(j).add(allDate.get(i));
				k++;
			} else {
				time.get(j).add(allDate.get(i));
				k++;
			}
		}
		
		for(int i = 0; i < time.size(); i++) {
			for(int l = 0; l < time.get(i).size(); l++) {
				if(i==0 && l==0) {
					ableTime += time.get(i).get(l)+",";
				} else if(l==0){
					ableTime += "::"+time.get(i).get(l)+",";
				} else if(time.get(i).size() - 1 == l){
					ableTime += time.get(i).get(l);
				} else {
					ableTime += time.get(i).get(l)+",";
				}
			}
		}
		
		lb.setAblePlace(ablePlace);
		lb.setAbleTime(ableTime);
		
		
		
		//??????????????? ???????????? ????????? ??????
		Image img = new Image();
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		if(thumbnail != null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);
			
			if(renameFileName != null) {
				img.setOriginName(thumbnail.getOriginalFilename());
				img.setChangeName(renameFileName);
				img.setImgSrc(savePath);
				img.setFileLevel(0);//0??? ?????????
				img.setWriter(lb.getWriter());
			}
		}
		
		int result = lbService.insertBoard(lb);
		int result2 = lbService.insertLecture(lb);
		int result3 = lbService.insertImg(img);
		return "redirect:/list.lec";
	}
	
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		//??? ?????? contextPath??? ???????????? ????????? ?????? ????????? (webapp ????????? resources??? ??????)
		
		String savePath = root +"\\uploadFiles";
		
		File folder = new File(savePath);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date(System.currentTimeMillis()))
								+"."+originFileName.substring(originFileName.lastIndexOf(".")+1);
		
		String  renamePath = folder +"\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return renameFileName;
	}
	
	@RequestMapping("write.lec")
	public ModelAndView write() {
		ModelAndView mv = new ModelAndView();
		ArrayList<Hobby> hList = lbService.selectHobbyList();
		if(hList != null) {
			mv.addObject("hList", hList);
			mv.setViewName("lectureWriteView");
		} else {
			throw new LectureBoardException("?????? ????????? ???????????? ??????");
		}
		return mv;
	}
	
	@RequestMapping("list.lec")
	public ModelAndView list( @RequestParam(value="sido", required=false) String sido, @RequestParam(value="gugun", required=false) String gugun,
							  @RequestParam(value="searchTag", required=false) String searchTag, @RequestParam(value="searchValue", required=false) String searchValue,
							  @RequestParam(value="hobbyNo", required=false) String hobbyNo, HttpServletRequest request) {
		int searchCate = 0;
		String searchWhere = "";
		if(sido == null) {
			sido = "";
		} else {
			searchWhere = sido;
		}
		if(gugun == null) {
			gugun = "";
		} else {
			searchWhere = gugun;
		}
		if(searchTag == null)
			searchTag = "";
		if(searchValue == null)
			searchValue = "";
		if(hobbyNo != null && !hobbyNo.equals("")) {
			searchCate = Integer.parseInt(hobbyNo);
		}
		if(gugun.equals(""))
			searchWhere = sido;
		// ?????????????????? list????????? ??????????????? ??????????????? ??????????????? ""??????
		Search search = new Search(searchWhere, searchTag, searchValue, searchCate);
		int listCount = lbService.selectLBCount(search);
		
		// count??? ????????? ????????? ??? select?????????
		ModelAndView mv = new ModelAndView();
		mv.setViewName("lectureListView");
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		mv.addObject("pi",pi);
		
		ArrayList<LectureBoard> lbList = lbService.selectLBPage(pi,search);
		if(lbList != null) {
			mv.addObject("lbList", lbList);
		} else {
			throw new LectureBoardException("lbList ???????????? ??????");
		}
		
		// ?????? ????????? ????????????
		ArrayList<Hobby> hList = lbService.selectHobbyList();
		if(hList != null) {
			mv.addObject("hList", hList);
		} else {
			throw new LectureBoardException("?????? ????????? ???????????? ??????");
		}
		
		//ArrayList<LectureBoard> lbList = lbService.selectLB();
		
		
		mv.addObject("sido",sido);
		mv.addObject("gugun",gugun);
		mv.addObject("searchTag",searchTag);
		mv.addObject("searchValue",searchValue);
		mv.addObject("hobbyNo",hobbyNo);
		mv.addObject("currentPage", currentPage);
		mv.addObject("applySuccess", request.getParameter("applySuccess"));
		return mv;
	}
		
	@RequestMapping("adminCateView.lec")
	public ModelAndView adminCateView() {
		ModelAndView mv = new ModelAndView();
		ArrayList<Hobby> hList = lbService.selectHobbyList();
		if(hList != null) {
			mv.addObject("hList", hList);
			mv.setViewName("adminCategory");
		} else {
			throw new LectureBoardException("?????? ????????? ???????????? ??????");
		}
		return mv;
	}
		
	@RequestMapping("adminCate.lec")
	public String cateInsert(@RequestParam(value="hobbyNo", required=false) String hobbyNoS, 
							 @RequestParam("hobbyName") String hobbyName, @RequestParam("delYN") String delYN,
							 HttpServletRequest request) {
		int hobbyNo = 0;
		
		if(!hobbyNoS.equals("")) {
			hobbyNo = Integer.parseInt(hobbyNoS);
		}
		if(delYN.equals("Y")) {
			try {
				int result = lbService.deleteCategory(hobbyNo);
				if(result < 0) {
					throw new LectureBoardException("?????? ????????? ??????????????????.");
				}
			} catch (Exception e) {
				request.getSession().setAttribute("cantDel", "cantDel");
				return "redirect:/adminCateView.lec";
			}
		} else {
			if(hobbyNo == 0) {
				int result = lbService.insertCategory(hobbyName);
				if(result < 0) {
					throw new LectureBoardException("?????? ????????? ??????????????????.");
				}
			} else {
				Hobby hobby = new Hobby(hobbyNo, hobbyName);
				int result = lbService.updateCategory(hobby);
				if(result < 0) {
					throw new LectureBoardException("?????? ??????????????? ??????????????????.");
				}
			}
		}
		return "redirect:/adminCateView.lec";
	}
	
	@RequestMapping("detail.lec")
	public ModelAndView detailView(@RequestParam("postNo") String postNoS, @RequestParam(value="sido", required=false) String sido, @RequestParam(value="gugun", required=false) String gugun,
			  @RequestParam(value="searchTag", required=false) String searchTag, @RequestParam(value="searchValue", required=false) String searchValue,
			  @RequestParam(value="hobbyNo", required=false) String hobbyNo, @RequestParam(value="currentPage", required=false) String currentPageS, HttpServletRequest request) {
		int postNo = Integer.parseInt(postNoS);
		ModelAndView mv = new ModelAndView();
		LectureBoard lb = lbService.selectLB(postNo);
		if(lb != null) {
			mv.addObject("lb", lb);
		}
		
		ArrayList<Reply> rList = lbService.selectRList(postNo);
		if(rList != null) {
			mv.addObject("rList", rList);
		}
		// ?????? ??????????????? ????????????
		// ????????? ???,???,??? ???????????? (???,??????,???,??????) ??? ??????????????? ????????????
		// ????????? ArrayList<String[]>??? ?????????
		
		String ablePlace1[] = lb.getAblePlace().split("/");
		ArrayList<String[]> ablePlace = new ArrayList<>();
		for(int i = 0; i < ablePlace1.length; i++) {
			ablePlace.add(ablePlace1[i].split(","));
		}
		mv.addObject("ablePlace", ablePlace);
		
		
		// ????????? 03.21(???) 19:00 ~ 21:00 ???????????? ????????? ????????? ?????????
		ArrayList<String> ableTime = new ArrayList<>();
		if(lb.getAbleTime().equals("AllDone")) {
			ableTime.add("AllDone");
		} else {
			String ableTime1[] = lb.getAbleTime().split("::");
			ArrayList<String[]> ableTime0 = new ArrayList<>();
			for(int i = 0; i < ableTime1.length; i++) {
				ableTime0.add(ableTime1[i].split(","));
			}
			
			for(int i = 0; i < ableTime0.size(); i++) {
				for(int j = 1; j < ableTime0.get(i).length; j++) {
					String[] able = ableTime0.get(i);
					ableTime.add(able[0]+" "+able[j]+":00 ~ "+(Integer.parseInt(able[j])+lb.getOtTime())+":00");
				}
			}
		}
		
		mv.addObject("ableTime", ableTime);
		mv.addObject("sido", sido);
		mv.addObject("gugun", gugun);
		mv.addObject("searchTag", searchTag);
		mv.addObject("searchValue", searchValue);
		mv.addObject("hobbyNo", hobbyNo);
		int currentPage = 1;
		if(currentPageS != null) {
			currentPage = Integer.parseInt(currentPageS);
		}
		mv.addObject("currentPage", currentPage);
		mv.setViewName("lectureDetailView");
		// ?????? ???????????? ???????????? ???????????? ????????? 1 ??????, ???????????? 0 ?????? ?????? (?????? postno????????????)
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		int checkBeforeApply = 0;
		if(loginUser != null) {
			Conclusion c = new Conclusion();
			c.setPostNo(postNo);
			c.setProposer(loginUser.getUserId());
			checkBeforeApply = lbService.checkBeforeConclusion(c);
		}
		mv.addObject("checkBeforeApply", checkBeforeApply);
		
		return mv;
	}
	
	//?????? ??????
	@RequestMapping("insertReply.lec")
	public void insertReply(@RequestParam("postNo") String postNoS,@RequestParam("rWriter") String rWriter,
						    @RequestParam("replyContent") String replyContent, javax.servlet.http.HttpServletResponse response) throws UnsupportedEncodingException {
		int postNo = Integer.parseInt(postNoS);
		
		Reply r = new Reply();
		r.setRefPno(postNo);
		r.setrWriter(rWriter);
		r.setrContent(replyContent);
		int result = lbService.insertReply(r);
		if(result < 1) {
			throw new LectureBoardException("?????? ?????? ??????");
		}
		
		ArrayList<Reply> rList = lbService.selectRList(postNo);
		for(Reply r1 : rList) {
			r1.setrNickName(URLEncoder.encode(r1.getrNickName(), "UTF-8")); // ????????? ?????????
			r1.setrContent(URLEncoder.encode(r1.getrContent(), "UTF-8"));
		}
		
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(rList, response.getWriter());
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("applyLecture.lec")
	public String applyLecture(@RequestParam("postNo") String postNoS,@RequestParam("selectSido") String sido,
							 @RequestParam("selectGugun") String gugun,@RequestParam("selectDong") String dong,
							 @RequestParam("ableTime") String ableTime, @RequestParam("userId") String proposer,
							 @RequestParam(value="searchSido", required=false) String searchSido,
							 @RequestParam(value="searchGugun", required=false) String searchGugun,
							 @RequestParam(value="searchTag", required=false) String searchTag,
							 @RequestParam(value="searchValue", required=false) String searchValue,
							 @RequestParam(value="searchHobbyNo", required=false) String searchHobbyNo,
							 @RequestParam(value="currentPage", required=false) String currentPageS,
							 HttpServletRequest request
			) {
		int postNo = Integer.parseInt(postNoS);
		LectureBoard lb = lbService.selectLB(postNo);
		//CONCLUSION TABLE INSERT
			//?????? ????????? cal??????
			GregorianCalendar cal = new GregorianCalendar();
			// albeTime?????? ???????????????
			int ableMonth = Integer.parseInt(ableTime.substring(0, 2));
			// ableTime?????? ???????????????
			int ableDay = Integer.parseInt(ableTime.split("/")[1].substring(0, 2));
			// ableTime?????? ?????? ????????????
			int ableHrs = Integer.parseInt(ableTime.split(":")[0].substring(ableTime.split(":")[0].length() - 2 , ableTime.split(":")[0].length()));
			String ableHrsS = null;
			if(ableHrs < 10) {
				ableHrsS = "0"+ableHrs;
			} else {
				ableHrsS = String.valueOf(ableHrs);
			}
			//cal??? ???????????? ????????? ?????? ????????? ???????????? ??????
			cal.set(cal.get(Calendar.YEAR), ableMonth-1, ableDay, ableHrs, 00);
			
			//??????????????? ??????
			java.sql.Date startDate = new java.sql.Date(cal.getTimeInMillis());
			
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH");
			
			//cal??? ??????????????? * 7??? ????????? ???????????? finishDate??????
			cal.add(Calendar.DATE, lb.getOtAllTime() * 7);
			
			//cal??? ???????????? finishDate??????
			java.sql.Date finishDate = new java.sql.Date(cal.getTimeInMillis());
			String startDateS = fm.format(startDate);
			String finishDateS = fm.format(finishDate);
			Conclusion c = new Conclusion(postNo, proposer, startDateS, finishDateS);
			//c??? startDateS??? finishDateS??? set???
			int conResult = lbService.insertConclusion(c);
			
		//LECTUREBOARD UPDATE ?????? ?????? ???????????? ?????? ????????? ????????? ??????
			String compareTime = ableTime.substring(0, 2)+"/"+ableTime.split("/")[1].substring(0, 2)+ableTime.substring(5, 8);
			ArrayList<ArrayList> time = new ArrayList<>();
			// time??? lb.ableTime??? ????????? ???????????? ??????
			
			
			for(int i = 0; i < lb.getAbleTime().split("::").length; i++) {
				ArrayList<String> exList = new ArrayList<>();
				Collections.addAll(exList, lb.getAbleTime().split("::")[i].split(","));
				time.add(exList);
			}
			// time?????? ?????? ????????? ??? ?????????
			for(int i = 0; i < time.size(); i++) {
				if(time.get(i).get(0).equals(compareTime)) {
					for(int j = 0; j < time.get(i).size(); j++) {
						if(time.get(i).get(j).equals(ableHrsS)) {
							// ????????? ????????? ??????
							time.get(i).remove(String.valueOf(ableHrsS));
							// ???????????? ????????? ???????????? ????????? ????????? ????????????????????? > ????????? ?????? ??????????????? ??????????????????
							// ????????? ????????? ??????
							if(time.get(i).size() <= 1) {
								time.remove(i);
								break;
							}
						}
					}
				}
			}
			
			//ableTime??? ??????????????? ????????? ??????????????? ????????? ?????????
			ableTime = "";
			for(int i = 0; i < time.size(); i++) {
				for(int l = 0; l < time.get(i).size(); l++) {
					if(i==0 && l==0) {
						ableTime += time.get(i).get(l)+",";
					} else if(l==0){
						ableTime += "::"+time.get(i).get(l)+",";
					} else if(time.get(i).size() - 1 == l){
						ableTime += time.get(i).get(l);
					} else {
						ableTime += time.get(i).get(l)+",";
					}
				}
			}
			if(ableTime.equals("")) {
				ableTime = "AllDone";
				lbService.deleteBoard(postNo);
			}
			lb.setAbleTime(ableTime);
		//lb??? ????????? ????????? ?????? ????????? ???????????? ??????????????? ??????????????????
			int lbupdateResult = lbService.updateLecture(lb);
			
		// detail??? ?????? ???????????? ??????
			ModelAndView mv = detailView(postNoS, searchSido, searchGugun, searchTag, searchValue, searchHobbyNo, currentPageS, request);
			lb = lbService.selectLB(postNo);
			
			mv = list(searchSido, searchGugun, searchTag, searchValue, searchHobbyNo, request);
		// timetable??? insert??????....
			//concNo??? ??????????????? ????????? conclusion??? select?????? ????????????.
			c = lbService.selectConclusion(c);
			//cal??? ?????? startDate ??????
			cal.set(cal.get(Calendar.YEAR), ableMonth-1, ableDay, ableHrs, 00);
			//fm??? ????????? ??????????????????
			fm = new SimpleDateFormat("yyyyMMddHH");
			Timeline timeline = new Timeline();
			java.sql.Date timelineDate = new java.sql.Date(cal.getTimeInMillis());
			//otAllTime?????? ????????????.
			for(int i = 0; i < lb.getOtAllTime(); i++) {
				timelineDate = new java.sql.Date(cal.getTimeInMillis());
				timeline.setUserId(lb.getWriter());
				timeline.setPostNo(postNo);
				timeline.setConcNo(c.getConcNo());
				timeline.setTimeline(fm.format(timelineDate));
				int result = lbService.insertTimeline(timeline);
				cal.add(Calendar.DATE, 7);
			}
		//?sido="+searchSido+"&gugun="+searchGugun+"&searchTag="+searchTag+"&searchValue="+searchValue+"&hobbyNo="+searchHobbyNo+"&request="+request
		request.getSession().setAttribute("applySuccess", "applySuccess");
		//statics??? ?????????
		int applyResult = lbService.updateStatics();
		
		return "redirect:/list.lec";
	}
	
	@RequestMapping("rList.lec")
	public void rList(@RequestParam("postNo") String postNoS, HttpServletResponse response) throws UnsupportedEncodingException {
		int postNo = Integer.parseInt(postNoS);
		ArrayList<Reply> rList = lbService.selectRList(postNo);
		if(rList == null) {
			throw new LectureBoardException("rList.lec?????? ?????????????????? ??????");
		}
		
		for(Reply r1 : rList) {
			r1.setrNickName(URLEncoder.encode(r1.getrNickName(), "UTF-8")); // ????????? ?????????
			r1.setrContent(URLEncoder.encode(r1.getrContent(), "UTF-8"));
		}
		
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(rList, response.getWriter());
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("updateReply.lec")
	@ResponseBody
	public String updateReply(Reply r, @RequestParam("rNo") int rNo, @RequestParam("rContent") String rContent) {
		
		r.setrNo(rNo);
		r.setrContent(rContent);
		int result = lbService.updateReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new LectureBoardException("?????? ?????? ??????!!");
		}
	}
	
	@RequestMapping("deleteReply.lec")
	@ResponseBody
	public String deleteReply(Reply r, @RequestParam("rNo") int rNo) {
		
		r.setrNo(rNo);
	
		int result = lbService.deleteReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new LectureBoardException("?????? ?????? ??????!!");
		}
	}
	
	@RequestMapping("updateView.lec")
	public ModelAndView updateView(@RequestParam("postNo") String postNoS) {
		int postNo = Integer.parseInt(postNoS);
		LectureBoard lb = lbService.selectLB(postNo);
		ModelAndView mv = new ModelAndView();
		if(lb != null) {
			mv.addObject("lb", lb);
		} else {
			throw new LectureBoardException("lb ???????????? ??????");
		}
		
		ArrayList<Hobby> hList = lbService.selectHobbyList();
		if(hList != null) {
			mv.addObject("hList", hList);
		} else {
			throw new LectureBoardException("?????? ????????? ???????????? ??????");
		}
		
		
		mv.setViewName("lectureUpdateView");
		
		return mv;
	}
	
	@RequestMapping("updateLecture.lec")
	public String updateLecture(@ModelAttribute LectureBoard lb,HttpServletRequest request, @RequestParam("thumbnailImg") MultipartFile thumbnail, @ModelAttribute Member loginUser,
			  @RequestParam("allDate") ArrayList<String> allDate, @RequestParam("postNo") String postNoS) {
		int postNo = Integer.parseInt(postNoS);
		lb.setPostNo(postNo);
		ArrayList<String> sidoArr = new ArrayList<String>();
		for(int i = 0; i < 10 ;i++) {
			if(!request.getParameter("sido"+i).equals("")) {
				sidoArr.add(request.getParameter("sido"+i));
			} else {
				break;
			}
		}
		
		ArrayList<String> gugunArr = new ArrayList<String>();
		for(int i = 0; i < 10 ;i++) {
			if(!request.getParameter("gugun"+i).equals("")) {
				gugunArr.add(request.getParameter("gugun"+i));
			} else {
				break;
			}
		}
		
		ArrayList<String[]> dongArr = new ArrayList<String[]>();
		for(int i = 0; i < 10 ; i++) {
			if(request.getParameterValues("dong"+i) != null) {
				dongArr.add(request.getParameterValues("dong"+i));
			} else {
				break;
			}
		}
		
		ArrayList<ArrayList<String>> APList = new ArrayList<>();
		for(int i = 0; i < sidoArr.size(); i++) {
			ArrayList<String> nowArr = new ArrayList<>();
			nowArr.add(sidoArr.get(i));
			nowArr.add(gugunArr.get(i));
			for(int j = 0; j < dongArr.get(i).length; j++) {
				nowArr.add(dongArr.get(i)[j]);
			}
			APList.add(nowArr);
		}
		
		String ablePlace = "";
		for(int i = 1; i < APList.size(); i++) {
			Collections.sort(APList, new Comparator<ArrayList<String>>() {
				@Override
				public int compare(ArrayList<String> o1, ArrayList<String> o2) {
					if(o1.get(0).equals(o2.get(0))) {
						return o1.get(1).compareTo(o2.get(1));
					} else {
						return o1.get(0).compareTo(o2.get(0));
					}
				}
			});
		}
		
		
		for(int i = 0; i < APList.size(); i++) {
			for(int j = 0; j < APList.get(i).size(); j++) {
				if(i != 0 && j == 0) {
					ablePlace += "/"+APList.get(i).get(j)+",";
				} else if(j == ( APList.get(i).size()-1 )){
					ablePlace += APList.get(i).get(j);
				} else {
					ablePlace += APList.get(i).get(j)+",";
				}
			}
		}
		
		String ableTime = "";
		ArrayList<ArrayList<String>> time = new ArrayList<ArrayList<String>>();
		int j = -1;
		int k = 0;
		for(int i = 0; i < allDate.size(); i++) {
			if(allDate.get(i).length() > 2) {
				j++;
				k = 0;
				time.add(new ArrayList<String>());
				time.get(j).add(allDate.get(i));
				k++;
			} else {
				time.get(j).add(allDate.get(i));
				k++;
			}
		}
		
		for(int i = 0; i < time.size(); i++) {
			for(int l = 0; l < time.get(i).size(); l++) {
				if(i==0 && l==0) {
					ableTime += time.get(i).get(l)+",";
				} else if(l==0){
					ableTime += "::"+time.get(i).get(l)+",";
				} else if(time.get(i).size() - 1 == l){
					ableTime += time.get(i).get(l);
				} else {
					ableTime += time.get(i).get(l)+",";
				}
			}
		}
		
		lb.setAblePlace(ablePlace);
		lb.setAbleTime(ableTime);
		
		
		
		//??????????????? ???????????? ????????? ??????
		Image img = new Image();
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		if(thumbnail != null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);
			
			if(renameFileName != null) {
				img.setPostNo(postNo);
				img.setOriginName(thumbnail.getOriginalFilename());
				img.setChangeName(renameFileName);
				img.setImgSrc(savePath);
				img.setFileLevel(0);//0??? ?????????
				img.setWriter(lb.getWriter());
			}
			
			int result3 = lbService.updateImg(img);
		}
		
		int result = lbService.updateBoard(lb);
		int result2 = lbService.updateLecture(lb);
		
		return "redirect:/list.lec";
	}
	
	@RequestMapping("deleteBoard.lec")
	public ModelAndView deleteBoard(@RequestParam("postNo") String postNoS ,HttpServletRequest request) {
		int postNo = Integer.parseInt(postNoS);
		ModelAndView mv = new ModelAndView();
		int deleteResult = lbService.deleteBoard(postNo);
		mv = list(null, null, null, null, null, request);
		return mv;
	}
	
	@RequestMapping("selectHobbyView.lec")
	public ModelAndView selectHobbyView() {
		ModelAndView mv = new ModelAndView();
		ArrayList<Hobby> hList = lbService.selectHobbyList();
		mv.addObject("hList",hList);
		mv.setViewName("hobbySelectView");
		return mv;
	}
	
	@RequestMapping("uploadSummernoteImageFile.lec")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		Image img = new Image();
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		if(file != null && !file.isEmpty()) {
			String renameFileName = saveFile(file, request);
			
			if(renameFileName != null) {
				img.setOriginName(file.getOriginalFilename());
				img.setChangeName(renameFileName);
				img.setImgSrc(savePath);
				img.setFileLevel(0);//0??? ?????????
			}
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		try {
			gson.toJson(img.getChangeName(), response.getWriter());
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
}


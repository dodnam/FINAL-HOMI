package finalProject.homis.hobbyFarm.groupFarm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.common.model.vo.Image;
import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.Reply;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.common.model.vo.Teacher;
import finalProject.homis.hobbyFarm.friends.model.service.FriendService;
import finalProject.homis.hobbyFarm.groupFarm.model.exception.GroupFarmBoardException;
import finalProject.homis.hobbyFarm.groupFarm.model.service.GroupFarmService;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmApplication;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.GroupFarmBoard;
import finalProject.homis.hobbyFarm.groupFarm.model.vo.Pagination;
import finalProject.homis.hobbyFarm.lecture.model.service.LectureBoardService;
import finalProject.homis.hobbyFarm.lecture.model.vo.LectureBoard;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.service.MessageService;
import finalProject.homis.hobbyFarm.message.model.vo.Message;

@Controller
public class GroupFarmController {

	@Autowired
	private GroupFarmService gfService;
	
	@Autowired
	private MessageService msgService;
	
	// ????????? ?????? ??????
	@RequestMapping("blist.gf")
	public ModelAndView boardList(@RequestParam(value="page", required=false) Integer page,
								  // ?????? ?????? ?????????
								  @RequestParam(value="isNeededTeacher", required=false) String isNeededTeacher,
								  // ?????? ??????
								  @RequestParam(value="sido", required=false) String sido, 
								  @RequestParam(value="gugun", required=false) String gugun,
								  @RequestParam(value="hobby", required=false) String hobby,
								  // ????????? ??????
								  @RequestParam(value="searchSelect", required=false) String searchSelect, 
								  @RequestParam(value="searchValue", required=false) String searchValue,
								  SearchSelect ss, ModelAndView mv, HttpServletRequest request) {
		
		// ?????? ?????? : ??????
		if(sido == null || sido == "") { 
			sido = null;
		} else if (gugun == null || gugun == ""){
			gugun = null;
		} else {
			ss.setLocation(sido + " " + gugun);
		}
		
		// ?????? ?????? : ??????
		if(hobby == null || hobby == "") {
			hobby = null;
		} else {
			ss.setHobby(hobby);
		}
		
		// ????????? ??????
		if(searchValue == null || searchValue == "") {
			searchValue = null;
		} else {
			ss.setSearchValue(searchValue);
			
			if(searchSelect.equals("writer")) {
				ss.setWriter(searchValue);
			} else if(searchSelect.equals("title")) {
				ss.setTitle(searchValue);
			} else if(searchSelect.equals("content")) {
				ss.setContent(searchValue);
			}
		}
		
		
		int currentPage = 1;
		
		if(page != null) {
			currentPage = page;
		}
		
		int listCount = gfService.getListCount(ss);

		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Hobby> hlist = gfService.selectHList();
		ArrayList<GroupFarmBoard> blist = gfService.selectList(pi, ss);
		ArrayList<Image> flist = gfService.selectTList(pi, ss);
		
		
		if(blist != null && flist != null) {
			mv.addObject("hlist", hlist)
			  .addObject("blist", blist)
			  .addObject("flist", flist)
			  .addObject("pi", pi)
			  .addObject("selectedSido", sido)
			  .addObject("selectedgugun", gugun)
			  .addObject("selectedHobby", hobby)
			  .addObject("searchValue", searchValue)
			  .addObject("searchSelect", searchSelect);
			
			if(isNeededTeacher != null && isNeededTeacher.equals("Y")) {
				mv.addObject("isNeededTeacher", "Y");
				HttpSession session = request.getSession();
				Member loginUser = (Member)session.getAttribute("loginUser");
				String loginId = loginUser.getUserId();
				
				// ?????? ????????? ????????????
				GroupFarmBoard gf = gfService.selectLastInsertInfo(loginId);
				
				mv.addObject("gf", gf);
			}
			
			mv.setViewName("GroupFarmListView");
		} else {
			throw new GroupFarmBoardException("????????? ?????? ????????? ?????????????????????.");
		}
		
		return mv;
		
	}
	
	@RequestMapping("hList.gf")
	public ModelAndView selectHobbyList(ModelAndView mv) {
		
		ArrayList<Hobby> hlist = gfService.selectHList();
		
		mv.addObject("hlist",hlist)
		  .setViewName("SelectHobby");
		return mv;
	}
	
	// ????????? ?????? ????????? ??????
	@RequestMapping("insertView.gf")
	public ModelAndView insertVeiw(ModelAndView mv) {
		ArrayList<Hobby> hlist = gfService.selectHList();
		
		mv.addObject("hlist", hlist)
		  .setViewName("GroupFarmWriteForm");
		
		return mv;
	}
	
	// ?????? ????????? ??????
	@RequestMapping("fdList.gf")
	public ModelAndView friendsListView(ModelAndView mv, HttpSession session) {
		
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		
		ArrayList<Member> list = gfService.selectFdList(userId);
		
		mv.addObject("list",list).setViewName("FriendsListView");
		
		return mv;
	}
	
	// ????????? ??????
	@RequestMapping("insert.gf")
	public String insertBoard(@ModelAttribute GroupFarmBoard gf, @ModelAttribute Image img,
								@RequestParam("sido") String sido, @RequestParam("gugun") String gugun,
								@RequestParam("hobby") int hobbyNo, @RequestParam("thumbnailImg") MultipartFile thumbnail,
								@RequestParam("inviteFriends") String inviteFriends, HttpSession session, 
								HttpServletRequest request, ModelAndView mv) {
		
		// ?????? ??????
		gf.setLocation(sido + " " + gugun);
		gf.setHobbyNo(hobbyNo);
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		
		// ????????? ????????? saveFile????????? ?????? ???????????? thumbnail ????????????
		if(thumbnail !=null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);

			// ????????? ??????????????????
			if(renameFileName != null) {
				img.setPostNo(gf.getPostNo());
				img.setOriginName(thumbnail.getOriginalFilename());
				img.setChangeName(renameFileName);
				
				img.setImgSrc(savePath);
			}
		}
		
		String msg = "";

		int result = gfService.insertBoard(gf, img);
		int msgResult = 0;
		
		if(result > 0) {
			if(!inviteFriends.equals("")) {
				Message message = new Message();
				String id = ((Member)session.getAttribute("loginUser")).getUserId();
				String nickName = ((Member)session.getAttribute("loginUser")).getNickName();
				GroupFarmBoard newGF = gfService.selectLastInsertInfo(id);
				String url = "\"window.open('bdetail.gf?postNo="+newGF.getPostNo()+"')\"";
				
				String[] invitedFriends = inviteFriends.split(",");
				
				for(int i = 0; i < invitedFriends.length; i++) {
					
					String content = "<span>???????????????, <img id='logo'/>?????????!</span><br>" +
							"<span>" + nickName + " ????????? ???????????? #" + newGF.getTitle() + " ???????????? ?????????????????????.</span><br>" + 
							"<span>" + nickName + " ?????? ???????????? ?????? ?????? ????????? ?????????????????? ?????? ????????? ?????? ?????? ???????????? ?????? ??????????????????!</span><br>" + 
							"<div id='boardBtnWrapper'>" + 
							"<button id='goDetail' onclick=" + url + "'>????????????</button>"+
							"</div>";
					
					message.setMsg_to(invitedFriends[i]); //???????????? id
					message.setMsg_from(id);
					message.setMsg_title(newGF.getTitle() + " ?????????????????????!"); //?????? ??????
					message.setMsg_content(content);
					
					msgResult += msgService.insertMsg(message);
				}
				
				
				if(msgResult == invitedFriends.length) {
					msg = "success";
				} else {
					throw new GroupFarmBoardException("?????? ????????? ????????? ?????????????????????.");
				}
			}
			
			
			if(gf.getOfferYN().equals("Y")) {
				return "redirect:/blist.gf?isNeededTeacher=Y";
			}

		} else {
			throw new GroupFarmBoardException("????????? ????????? ?????????????????????.");
		}
		
		return "redirect:/blist.gf?";
	
	}
	
	// ?????? ??????
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		// ??? ?????? contextPath??? ???????????? ????????? ????????? String ????????? ??????
		// ??????????????? ????????? ??????????????? getServletContext ???????????? ????????? resources??? RealPath??? ????????? ???.
		String root = request.getSession().getServletContext().getRealPath("resources");
		//C:\Workspace\01_finalProject_workspace\0_HobbyFarm\src\main\webapp\resources
		
		String savePath = root + "\\uploadFiles";
		
		File folder = new File(savePath);
		
		// ????????? ????????? ????????? ??????
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// ????????? ?????? ????????? ????????? ?????? ?????? ??????
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename(); 
		String renameFileName
			= sdf.format(new Date(System.currentTimeMillis())) // (?????????)?????? ??????
			+ "." + originFileName.substring(originFileName.lastIndexOf(".") + 1); // ?????? ????????? ???????????? ??????
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath)); // ?????? ???????????? ?????? ??????
		} catch (Exception e) {
			System.out.println("?????? ?????? ??????" + e.getMessage());
			e.printStackTrace();
		}
		
		return renameFileName;
	}
	
	// ?????? ?????? ??????
	public ArrayList<Teacher> RandomTeacherList(ArrayList<Teacher> teacherList){
			
			ArrayList<Teacher> tList = new ArrayList<Teacher>();
			ArrayList<Teacher> randomTList = new ArrayList<Teacher>();
			
			// ArrayList??? Teacher ??????
			for(Teacher t : teacherList){
				tList.add(t);
			}

	        // shuffle ??????
	        Collections.shuffle(tList);
	        // 1??? ????????????
	        
	        switch(tList.size()) {
	        case 0: randomTList.clear(); break;
	        case 1: randomTList.add(tList.get(0)); break;
	        case 2: 
	        	randomTList.add(tList.get(0));
	        	randomTList.add(tList.get(1));
	        	break;
	        default:
	        	randomTList.add(tList.get(0));
	        	randomTList.add(tList.get(1));
	        	randomTList.add(tList.get(2));
	        	break;
	        }
	        
			return randomTList;
	}
	
	@RequestMapping("recommend.gf")
	public ModelAndView recommendView(@RequestParam("postNo") Integer postNo, 
										@RequestParam("location") String location, 
										@RequestParam("hobbyNo") Integer hobbyNo,
										GroupFarmBoard gf, ModelAndView mv) {
		
		gf = gfService.selectBoard(postNo);
		
		ArrayList<Teacher> teacherList = gfService.teacherList(gf);
		// ?????? ?????? ?????? 3??? ?????? -- ?????????
		ArrayList<Teacher> tList = RandomTeacherList(teacherList);
		
		ArrayList<LectureBoard> lecList = new ArrayList<LectureBoard>();

		for(int i=0; i<tList.size(); i++) {
			LectureBoard lec = new LectureBoard();
			lec = gfService.recentLec(tList.get(i).getUserId());
			lecList.add(lec);
		}
		
		//gfService.selectLecture(tList);
		mv.addObject("gf", gf).addObject("tList", tList).addObject("lecList",lecList).setViewName("Recommend");
		
		return mv;
	}
	
	// ????????? ????????????
	@RequestMapping("bdetail.gf")
	public ModelAndView boardDetail(@RequestParam("postNo") Integer postNo, 
									@RequestParam(value="page", required=false) Integer page,
									ModelAndView mv){
		
		GroupFarmBoard gf = gfService.selectBoard(postNo);
		Image img = gfService.selectImage(postNo);
		ArrayList<GroupFarmApplication> gfa = gfService.selectGfaList(postNo);

		if(gf != null) {
			mv.addObject("gf", gf)
			  .addObject("img", img)
			  .addObject("gfaList", gfa);
			  
			  if(page != null) {
				  mv.addObject("page", page);
			  }
			  mv.setViewName("GroupFarmDetailView");
		} else {
			throw new GroupFarmBoardException("????????? ??????????????? ?????????????????????.");
		}
		
		return mv;
	}
	
	// ?????? ?????? ?????? ??????
	@RequestMapping("personnelList.gf")
	public ModelAndView personnelListVeiw(@RequestParam("postNo") Integer postNo,
										  @RequestParam("personnel") Integer personnel,
										  ModelAndView mv) {
		GroupFarmBoard gf = gfService.selectBoard(postNo);
		ArrayList<GroupFarmApplication> gfa = gfService.selectGfaList(postNo);
		
		if(personnel != 0) {
			if(gfa != null) {
				mv.addObject("gf", gf)
				  .addObject("gfaList", gfa)
				  .setViewName("ApplicantListView");
			} else {
				throw new GroupFarmBoardException("?????? ?????? ????????? ?????????????????????.");
			}
		} else {
			mv.addObject("gf", gf).addObject("gfaList", gfa).setViewName("ApplicantListView");
		}
		return mv;
	}
	
	// ?????? ??????
	@RequestMapping("rList.gf")
	public void getReplyList(HttpServletResponse response, @RequestParam("postNo") int postNo) throws JsonIOException, IOException {
		ArrayList<Reply> rList = gfService.selectReplyList(postNo);
		
		for(Reply r : rList) {
			r.setrNickName(URLEncoder.encode(r.getrNickName(), "UTF-8")); // ????????? ?????????
			r.setrContent(URLEncoder.encode(r.getrContent(), "UTF-8")); //?????? ?????????
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(rList, response.getWriter());
	}
	
	// ?????? ??????
	@RequestMapping("addReply.gf")
	@ResponseBody // String?????? ???????????? ?????? view??? ????????? ??? ????????? ???????????????
	public String addReply(Reply r, HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		String rWriter = loginUser.getUserId();
		
		r.setrWriter(rWriter);
		
		int result = gfService.insertReply(r);
		
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("?????? ?????? ??????!!");
		}
		
	}
	
	// ?????? ??????
	@RequestMapping("updateReply.gf")
	@ResponseBody
	public String updateReply(Reply r, @RequestParam("rNo") int rNo, @RequestParam("rContent") String rContent) {
		
		r.setrNo(rNo);
		r.setrContent(rContent);
	
		int result = gfService.updateReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("?????? ?????? ??????!!");
		}
	}
	
	// ?????? ??????
	@RequestMapping("deleteReply.gf")
	@ResponseBody
	public String deleteReply(Reply r, @RequestParam("rNo") int rNo) {
		
		r.setrNo(rNo);
	
		int result = gfService.deleteReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("?????? ?????? ??????!!");
		}
	}

	// ?????? ??????
	@RequestMapping(value = { "/enterGroup.gf", "/requestClass.gf" })
	@ResponseBody
	public String enterGroup(@ModelAttribute GroupFarmApplication gfa, RedirectAttributes redirect,
								@RequestParam(value="page", required=false) Integer page, @RequestParam("postNo") Integer postNo, 
								@RequestParam("userId") String userId, @RequestParam("mKind") Integer mKind,
								@RequestParam(value="nickName", required = false) String nickName) {
		gfa.setPostNo(postNo);
		gfa.setUserId(userId);
		gfa.setmKind(mKind);
		gfa.setNickName(nickName);
		
		int result = gfService.enterGroup(gfa);
		
		redirect.addAttribute("page", page);
		redirect.addAttribute("postNo", postNo);
		
		if(gfa.getmKind() == 1) {
			
			GroupFarmBoard gf = gfService.selectBoard(postNo);
			ArrayList<GroupFarmApplication> gfaList = gfService.selectGfaList(postNo);
			
			if(gfaList.size() == Integer.parseInt(gf.getPersonnel())){
				return "redirect:closeGroup.gf?postNo=" + postNo + "&page=" + page;
			}
		}
		
		if(result > 0) {
			return "redirect:bdetail.gf";
		} else {
			throw new GroupFarmBoardException("?????? ????????? ?????????????????????.");
		}
	}
	
	//?????? ??????
	@RequestMapping(value = { "/exeuntGroup.gf", "/cancleClass.gf" })
	@ResponseBody
	public String exeuntGroup(@ModelAttribute GroupFarmApplication gfa, RedirectAttributes redirect,
								@RequestParam(value="page", required=false) Integer page, @RequestParam("postNo") Integer postNo, 
								@RequestParam("userId") String userId, @RequestParam("mKind") Integer mKind) {
		
		gfa.setPostNo(postNo);
		gfa.setUserId(userId);
		gfa.setmKind(mKind);
		
		int result = gfService.exeuntGroup(gfa);
		
//		redirect.addAttribute("page", page);
//		redirect.addAttribute("postNo", postNo);
		
		if(result > 0) {
//			return "redirect:bdetail.gf";
			return result + "";
		} else {
			throw new GroupFarmBoardException("?????? ????????? ?????????????????????.");
		}
	}
	
	// ?????? ??????
	@RequestMapping("closeGroup.gf")
	public String closeGroup(RedirectAttributes redirect, 
							@RequestParam("postNo") int postNo, @RequestParam(value="page", required=false) Integer page) {
		
		int result = gfService.closeGroup(postNo);
		
		redirect.addAttribute("page", page);
		redirect.addAttribute("postNo", postNo);
		
		if(result > 0) {
			return "redirect:bdetail.gf";
			//return "success";
		} else {
			throw new GroupFarmBoardException("?????? ????????? ?????????????????????.");
		}
		
	}
	
	@RequestMapping("cancleClose.gf")
	@ResponseBody
	public String cancleClose(@RequestParam("postNo") int postNo, @RequestParam(value="page", required=false) Integer page) {
		
		int result = gfService.cancleClose(postNo);
		
		if(result>0) {
			return "success";
		} else {
			return "error";
		}
	}
	
	// ??? ?????? View
	@RequestMapping("modifyView.gf")
	public ModelAndView modifyView(ModelAndView mv, @RequestParam("hobbyNo") Integer hobbyNo,
				@RequestParam("postNo") int postNo, @RequestParam("page") Integer page) {
		
		GroupFarmBoard gf = new GroupFarmBoard();
		gf.setHobbyNo(hobbyNo);
		gf = gfService.selectBoard(postNo);
		Image img = gfService.selectImage(postNo);
		ArrayList<GroupFarmApplication> gfa = gfService.selectGfaList(postNo);
		ArrayList<Hobby> hlist = gfService.selectHList();
		
		String[] location = gf.getLocation().split(" ");
		String sido = location[0];
		String gugun = location[1];
		
		if(gf != null) {
			mv.addObject("gf", gf)
			  .addObject("page", page)
			  .addObject("img", img)
			  .addObject("gfaList", gfa)
			  .addObject("hlist", hlist)
			  .addObject("selectedSido", sido)
			  .addObject("selectedGugun", gugun)
			  .addObject("selectedHobby", gf.getHobbyNo())
			  .setViewName("GroupFarmUpdateForm");
		}
		return mv;
	}
	
	// ??? ?????? 
	@RequestMapping("bmodify.gf")
	public String modifyBoard(@RequestParam("postNo") Integer postNo, @RequestParam("page") Integer page,
								@RequestParam("sido") String sido, @RequestParam("gugun") String gugun,
								@RequestParam("hobby") Integer hobbyNo, HttpServletRequest request,
								@RequestParam("thumbnailImg") MultipartFile thumbnail, ModelAndView mv,
								GroupFarmBoard gf, Image img) {
		
		// ?????? ??????
		gf.setLocation(sido + " " + gugun);
		gf.setHobbyNo(hobbyNo);
		
		img.setPostNo(postNo);
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		// ????????? ????????? saveFile????????? ?????? ???????????? thumbnail ????????????
		if(thumbnail !=null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);

			// ????????? ??????????????????
	         if(renameFileName != null) {
	            img.setPostNo(gf.getPostNo());
	            img.setOriginName(thumbnail.getOriginalFilename());
	            img.setChangeName(renameFileName);
	            
	            img.setImgSrc(savePath);
	            
	            int resultImg = gfService.updateImg(img);
	         }
	      }
	      
	      int resultBoard = gfService.updateBoard(gf);
	      int resultGroup = gfService.updateGroup(gf);
		
		if(resultBoard > 0 && resultGroup > 0) {
			return "redirect:/bdetail.gf?postNo="+postNo+"&page="+page;
		} else {
			throw new GroupFarmBoardException("????????? ????????? ?????????????????????.");
		}
		
	}

	// ??? ??????
	@RequestMapping("bdelete.gf")
	public String deleteBoard(@RequestParam("postNo") Integer postNo) {
		
		int result = gfService.deleteBoard(postNo);
		
		if(result > 0) {
			return "redirect:blist.gf";
			//return "success";
		} else {
			throw new GroupFarmBoardException("?????? ????????? ?????????????????????.");
		}
	}
}

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
	
	// 게시글 목록 조회
	@RequestMapping("blist.gf")
	public ModelAndView boardList(@RequestParam(value="page", required=false) Integer page,
								  // 강사 추천 리스트
								  @RequestParam(value="isNeededTeacher", required=false) String isNeededTeacher,
								  // 필터 검색
								  @RequestParam(value="sido", required=false) String sido, 
								  @RequestParam(value="gugun", required=false) String gugun,
								  @RequestParam(value="hobby", required=false) String hobby,
								  // 게시글 검색
								  @RequestParam(value="searchSelect", required=false) String searchSelect, 
								  @RequestParam(value="searchValue", required=false) String searchValue,
								  SearchSelect ss, ModelAndView mv, HttpServletRequest request) {
		
		// 필터 검색 : 지역
		if(sido == null || sido == "") { 
			sido = null;
		} else if (gugun == null || gugun == ""){
			gugun = null;
		} else {
			ss.setLocation(sido + " " + gugun);
		}
		
		// 필터 검색 : 취미
		if(hobby == null || hobby == "") {
			hobby = null;
		} else {
			ss.setHobby(hobby);
		}
		
		// 게시글 검색
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
				
				// 최근 작성글 갖고오기
				GroupFarmBoard gf = gfService.selectLastInsertInfo(loginId);
				
				mv.addObject("gf", gf);
			}
			
			mv.setViewName("GroupFarmListView");
		} else {
			throw new GroupFarmBoardException("게시글 전체 조회에 실패하였습니다.");
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
	
	// 게시글 작성 페이지 이동
	@RequestMapping("insertView.gf")
	public ModelAndView insertVeiw(ModelAndView mv) {
		ArrayList<Hobby> hlist = gfService.selectHList();
		
		mv.addObject("hlist", hlist)
		  .setViewName("GroupFarmWriteForm");
		
		return mv;
	}
	
	// 친구 리스트 조회
	@RequestMapping("fdList.gf")
	public ModelAndView friendsListView(ModelAndView mv, HttpSession session) {
		
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		
		ArrayList<Member> list = gfService.selectFdList(userId);
		
		mv.addObject("list",list).setViewName("FriendsListView");
		
		return mv;
	}
	
	// 게시글 작성
	@RequestMapping("insert.gf")
	public String insertBoard(@ModelAttribute GroupFarmBoard gf, @ModelAttribute Image img,
								@RequestParam("sido") String sido, @RequestParam("gugun") String gugun,
								@RequestParam("hobby") int hobbyNo, @RequestParam("thumbnailImg") MultipartFile thumbnail,
								@RequestParam("inviteFriends") String inviteFriends, HttpSession session, 
								HttpServletRequest request, ModelAndView mv) {
		
		// 주소 저장
		gf.setLocation(sido + " " + gugun);
		gf.setHobbyNo(hobbyNo);
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		
		// 파일이 있으면 saveFile이라고 하는 메소드에 thumbnail 담아주기
		if(thumbnail !=null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);

			// 파일이 저장되었으면
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
					
					String content = "<span>안녕하세요, <img id='logo'/>입니다!</span><br>" +
							"<span>" + nickName + " 님께서 회원님을 #" + newGF.getTitle() + " 모임으로 초대하셨습니다.</span><br>" + 
							"<span>" + nickName + " 님이 모집하고 계신 모임 텃밭이 궁금하시다면 아래 버튼을 눌러 해당 게시글로 바로 이동해보세요!</span><br>" + 
							"<div id='boardBtnWrapper'>" + 
							"<button id='goDetail' onclick=" + url + "'>보러가기</button>"+
							"</div>";
					
					message.setMsg_to(invitedFriends[i]); //받을사람 id
					message.setMsg_from(id);
					message.setMsg_title(newGF.getTitle() + " 모임초대입니다!"); //쪽지 제목
					message.setMsg_content(content);
					
					msgResult += msgService.insertMsg(message);
				}
				
				
				if(msgResult == invitedFriends.length) {
					msg = "success";
				} else {
					throw new GroupFarmBoardException("초대 메세지 전송에 실패하였습니다.");
				}
			}
			
			
			if(gf.getOfferYN().equals("Y")) {
				return "redirect:/blist.gf?isNeededTeacher=Y";
			}

		} else {
			throw new GroupFarmBoardException("게시글 등록에 실패하였습니다.");
		}
		
		return "redirect:/blist.gf?";
	
	}
	
	// 파일 저장
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		// 웹 서버 contextPath를 불러와서 폴더의 경로를 String 변수에 저장
		// 리퀘스트의 세션을 만들어주고 getServletContext 메소드를 통해서 resources의 RealPath를 갖고올 것.
		String root = request.getSession().getServletContext().getRealPath("resources");
		//C:\Workspace\01_finalProject_workspace\0_HobbyFarm\src\main\webapp\resources
		
		String savePath = root + "\\uploadFiles";
		
		File folder = new File(savePath);
		
		// 폴더가 없으면 폴더를 생성
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// 업로드 하는 날짜로 이미지 파일 이름 변경
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename(); 
		String renameFileName
			= sdf.format(new Date(System.currentTimeMillis())) // (시스템)현재 시간
			+ "." + originFileName.substring(originFileName.lastIndexOf(".") + 1); // 기존 파일의 확장자명 추가
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath)); // 바뀐 이름으로 파일 저장
		} catch (Exception e) {
			System.out.println("파일 전송 에러" + e.getMessage());
			e.printStackTrace();
		}
		
		return renameFileName;
	}
	
	// 추천 강사 뽑기
	public ArrayList<Teacher> RandomTeacherList(ArrayList<Teacher> teacherList){
			
			ArrayList<Teacher> tList = new ArrayList<Teacher>();
			ArrayList<Teacher> randomTList = new ArrayList<Teacher>();
			
			// ArrayList에 Teacher 추가
			for(Teacher t : teacherList){
				tList.add(t);
			}

	        // shuffle 이용
	        Collections.shuffle(tList);
	        // 1개 추출하고
	        
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
		// 추천 강사 랜덤 3명 추출 -- 메소드
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
	
	// 게시글 상세보기
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
			throw new GroupFarmBoardException("게시글 상세보기에 실패하였습니다.");
		}
		
		return mv;
	}
	
	// 참여 인원 목록 조회
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
				throw new GroupFarmBoardException("참여 인원 조회에 실패하였습니다.");
			}
		} else {
			mv.addObject("gf", gf).addObject("gfaList", gfa).setViewName("ApplicantListView");
		}
		return mv;
	}
	
	// 댓글 목록
	@RequestMapping("rList.gf")
	public void getReplyList(HttpServletResponse response, @RequestParam("postNo") int postNo) throws JsonIOException, IOException {
		ArrayList<Reply> rList = gfService.selectReplyList(postNo);
		
		for(Reply r : rList) {
			r.setrNickName(URLEncoder.encode(r.getrNickName(), "UTF-8")); // 닉네임 인코딩
			r.setrContent(URLEncoder.encode(r.getrContent(), "UTF-8")); //내용 인코딩
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		gson.toJson(rList, response.getWriter());
	}
	
	// 댓글 등록
	@RequestMapping("addReply.gf")
	@ResponseBody // String으로 보내주는 값을 view가 아니라 그 자체로 인식하도록
	public String addReply(Reply r, HttpSession session) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		String rWriter = loginUser.getUserId();
		
		r.setrWriter(rWriter);
		
		int result = gfService.insertReply(r);
		
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("댓글 등록 실패!!");
		}
		
	}
	
	// 댓글 수정
	@RequestMapping("updateReply.gf")
	@ResponseBody
	public String updateReply(Reply r, @RequestParam("rNo") int rNo, @RequestParam("rContent") String rContent) {
		
		r.setrNo(rNo);
		r.setrContent(rContent);
	
		int result = gfService.updateReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("댓글 수정 실패!!");
		}
	}
	
	// 댓글 삭제
	@RequestMapping("deleteReply.gf")
	@ResponseBody
	public String deleteReply(Reply r, @RequestParam("rNo") int rNo) {
		
		r.setrNo(rNo);
	
		int result = gfService.deleteReply(r);
		if(result > 0) {
			return "success";
		} else {
			throw new GroupFarmBoardException("댓글 삭제 실패!!");
		}
	}

	// 모임 신청
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
			throw new GroupFarmBoardException("모임 신청에 실패하였습니다.");
		}
	}
	
	//모임 탈퇴
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
			throw new GroupFarmBoardException("모임 탈퇴에 실패하였습니다.");
		}
	}
	
	// 모임 마감
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
			throw new GroupFarmBoardException("모임 마감에 실패하였습니다.");
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
	
	// 글 수정 View
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
	
	// 글 수정 
	@RequestMapping("bmodify.gf")
	public String modifyBoard(@RequestParam("postNo") Integer postNo, @RequestParam("page") Integer page,
								@RequestParam("sido") String sido, @RequestParam("gugun") String gugun,
								@RequestParam("hobby") Integer hobbyNo, HttpServletRequest request,
								@RequestParam("thumbnailImg") MultipartFile thumbnail, ModelAndView mv,
								GroupFarmBoard gf, Image img) {
		
		// 주소 저장
		gf.setLocation(sido + " " + gugun);
		gf.setHobbyNo(hobbyNo);
		
		img.setPostNo(postNo);
		
		String root = request.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\uploadFiles";
		
		// 파일이 있으면 saveFile이라고 하는 메소드에 thumbnail 담아주기
		if(thumbnail !=null && !thumbnail.isEmpty()) {
			String renameFileName = saveFile(thumbnail, request);

			// 파일이 저장되었으면
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
			throw new GroupFarmBoardException("게시글 수정에 실패하였습니다.");
		}
		
	}

	// 글 삭제
	@RequestMapping("bdelete.gf")
	public String deleteBoard(@RequestParam("postNo") Integer postNo) {
		
		int result = gfService.deleteBoard(postNo);
		
		if(result > 0) {
			return "redirect:blist.gf";
			//return "success";
		} else {
			throw new GroupFarmBoardException("모임 마감에 실패하였습니다.");
		}
	}
}

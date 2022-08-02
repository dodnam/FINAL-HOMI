package finalProject.homis.hobbyFarm.member.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.TempKey ;
import finalProject.homis.hobbyFarm.common.model.vo.Hobby;
import finalProject.homis.hobbyFarm.groupFarm.model.service.GroupFarmService;
import finalProject.homis.hobbyFarm.member.model.exception.MemberException;
import finalProject.homis.hobbyFarm.member.model.service.MemberService;
import finalProject.homis.hobbyFarm.member.model.vo.Member;

@SessionAttributes("loginUser")
@Controller
public class MemberController {
	
	@Autowired
	private JavaMailSender mailSender ;
	
	@Autowired
	private MemberService mService ;
	
	@Autowired
	private GroupFarmService gfService ;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder ;
	
//	로그인&로그아웃 Start ----------------------------------------------------------------------------------------------------------------
	@RequestMapping("loginView.me") // VIEW : 로그인 화면
	public String memberLoginView() {
		return "loginView" ;
	}
	
	@RequestMapping(value="login.me", method=RequestMethod.POST) // AJAX : 일반 로그인
	public void memberLogin(@RequestParam("userId") String userId,
							@RequestParam("userPwd") String userPwd,
							HttpServletResponse response,
							Model model) throws IOException {
		Member m = new Member() ;
		m.setUserId(userId) ;
		m.setUserPwd(userPwd);
		Member loginUser = mService.memberLogin(m) ;
		
		if(loginUser != null) {
			boolean passMatch = passEncoder.matches(userPwd, loginUser.getUserPwd()) ;
			if(passMatch) {
				String sysdate = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime()) ;
				mService.countVisit(sysdate) ;
				model.addAttribute("loginUser", loginUser) ;
				response.getWriter().print(true) ;
			} else {
				response.getWriter().print(false) ;
			}
		} else {
			response.getWriter().print(false) ;
		}
	}
	
	@RequestMapping(value="loginGoogle.me", method=RequestMethod.POST) // 기능 : 구글 로그인
	public String googleLogin(@RequestParam("googleId") String googleId, Model model) {
		Member m = new Member() ;
		m.setUserId(googleId) ;
		Member loginUser = mService.memberLogin(m) ;
		if(loginUser != null) {
			model.addAttribute("loginUser", loginUser) ; 
		} else {
			throw new MemberException("로그인에 실패하였습니다. 존재하지 않는 아이디입니다.") ;	
		} return "redirect:/" ;
	}
	
	@RequestMapping("logout.me") // 기능 : 로그아웃
	public String logout(SessionStatus status) {
		status.setComplete() ;
		return "redirect:/" ;
	}
//	로그인&로그아웃 End ----------------------------------------------------------------------------------------------------------------
	
//	회원가입 Start ----------------------------------------------------------------------------------------------------------------
 //	 - 회원가입 공통 Start
	@RequestMapping("joinViewSelect.me") // VIEW : 회원가입 선택
	public String memberJoinSelectView() {
		return "memberJoinSelectView" ;
	}
	
	public String saveFile(MultipartFile file, HttpServletRequest request) { // 메소드 : 사진 저장
		// 웹 서버 contextPath를 불러와서 폴더의 경로 받아옴(webapp 하위의 resources에 도달)
		String root = request.getSession().getServletContext().getRealPath("resources") ;
		
		String savePath = root + "\\uploadFiles" ;
		
		File folder = new File(savePath) ;
		if(!folder.exists())
			folder.mkdir() ;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
		String originFileName = file.getOriginalFilename() ;
		String renameFileName = sdf.format(new Date(System.currentTimeMillis())) + "." + originFileName.substring(originFileName.lastIndexOf(".") + 1) ;
		
		String renamePath = folder + "\\" + renameFileName ;
		
		try {
			file.transferTo(new File(renamePath)) ;
		} catch (Exception e) {
			e.printStackTrace() ;
		} return renameFileName ;
	}
 //	 - 회원가입 공통 End
	
 //	 - 일반 회원가입 Start
	@RequestMapping("joinNormalView.me") // VIEW : 일반 회원가입
	public String memberJoinNormalView() {
		return "memberJoinNormal" ;
	}
	
	@RequestMapping("join.me") // 기능 : 일반 회원가입
	public String memberJoin(@RequestParam("userId") String userId,
							 @RequestParam("nickName") String nickName,
							 @RequestParam("userName") String userName,
							 @RequestParam("userPwd") String userPwd,
							 @RequestParam("joinEmail") String email,
							 @RequestParam("phone") String phone,
							 @RequestParam("gender") String gender,
							 @RequestParam("year") Integer year,
							 @RequestParam("month") Integer month,
							 @RequestParam("date") Integer date,
							 @RequestParam("post") Integer post,
							 @RequestParam("address1") String address1,
							 @RequestParam("address2") String address2,
							 @RequestParam("mKind") Integer mKind,
							 @RequestParam("uploadFiles") MultipartFile uploadFile,
			  				 HttpServletRequest request) {
		
		Date birthDate = new Date(new GregorianCalendar(year,month-1,date).getTimeInMillis()) ;
		Member m = new Member() ;
		m.setUserId(userId) ;
		m.setNickName(nickName) ;
		m.setUserName(userName) ;
		m.setUserPwd(passEncoder.encode(userPwd)) ;
		m.setEmail(email) ;
		m.setGender(gender) ;
		m.setBirthDate(birthDate) ;
		m.setmKind(mKind) ;
		
		if(!phone.equals("#empty")) {
			m.setPhone(phone) ;
		}
		
		if(!post.equals("#empty")) {
			m.setPostalCode(post) ;
			m.setbAddress(address1) ;
			m.setlAddress(address2) ;
		}
		
		
		if(uploadFile != null && !uploadFile.isEmpty()) {
			String filePath = request.getSession().getServletContext().getRealPath("resources") + "\\uploadFiles" ;
			String renameFileName = saveFile(uploadFile, request) ;
			if(renameFileName != null) {
				m.setOriginName(uploadFile.getOriginalFilename()) ;
				m.setChangeName(renameFileName) ;
				m.setImgSrc(filePath) ;
			}
		}
		
		int result = mService.insertMember(m) ;
		int result2 = mService.insertUserImg(m) ;
		
		if(result > 0 && result2 > 0) {
			if(mKind == 2) {
				int tmpRst = mService.insertTeacher(m.getUserId()) ;
				if(tmpRst < 1)
					throw new MemberException("일반 회원가입에 실패하였습니다. (id:"+m.getUserId()+")") ;
			}
			String sysdate = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime()) ;
			mService.countUser(sysdate) ;
			return "redirect:loginView.me" ;
		} else {
			throw new MemberException("일반 회원가입에 실패하였습니다. (id:"+m.getUserId()+")") ;
		}
	}
	
	@RequestMapping("dupid.me") // AJAX : id 중복검사 
	public void dupIdCheck(HttpServletResponse response, @RequestParam("userId") String userId) throws IOException {
		boolean isUsable = (mService.checkIdDup(userId) < 1) ? true : false ;
		response.getWriter().print(isUsable) ;
	}
	
	@RequestMapping("dupnick.me") // AJAX : 닉네임 중복검사
	public void dupNickCheck(HttpServletResponse response, @RequestParam("nickName") String nickName) throws IOException {
		boolean isUsable = (mService.checkNickDup(nickName) < 1) ? true : false ;
		response.getWriter().print(isUsable) ;
	}
	
	@RequestMapping("emailCheck.me") // AJAX : 이메일 중복 검사(메일 api)
	public void emailCheck(HttpServletResponse response, @RequestParam("email") String email) throws IOException {
		String ranText = "" ;
		boolean isUsable = (mService.checkEmailDup(email) < 1) ? true : false ;
		if(!isUsable) {
			ranText = "#dup!" ;
		} else {
			String from = "homis.hobbyfarm@gmail.com" ; // 보내는 메일
			String to = email ;
			String title = "#취미텃밭 : 이메일 인증" ; // 제목
			ranText = new TempKey().getKey() ;
			StringBuffer content = new StringBuffer() ;
			content.append("<form id=\"mailForm\" style='background: white; width: 80%: height: 80%; text-align: center; margin-left: 2%;'>") ;
			content.append("<div><br><br>") ;
			content.append("<h1>이메일 인증</h1>") ;
			content.append("<p><b>#취미텃밭</b>에서 보내진 메일입니다.<p>") ;
			content.append("<p>본인이 보내신 메일이 아니라면 ["+from+"]로 도용 여부를 알려주세요!<p><br>") ;
			content.append("<div id=\"codeArea\" style='border: 2px solid; text-align:center; width: 40%; margin-left: 30%; margin-right: 30%; border-radius: 50px;'><h2>"+ranText+"</h2></div><br><br><br>") ;
			content.append("</div>") ;
			content.append("</form>") ;
			
			try {
				MimeMessage message = mailSender.createMimeMessage() ;
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8") ;
				
				messageHelper.setFrom(from) ;		// 보내는사람 생략하면 정상작동을 안함
				messageHelper.setTo(to) ;			// 받는사람 이메일
				messageHelper.setSubject(title) ;	// 메일제목은 생략이 가능하다
				messageHelper.setText(content.toString(), true) ; // 메일 내용
				
				mailSender.send(message) ;
			} catch (Exception e) {
				System.out.println(e) ;
			}
		} response.getWriter().print(ranText) ;
	}
 //	 - 일반 회원가입 End
	
 //	 - 구글 회원가입 Start
	@RequestMapping("joinGoogleView.me") // VIEW : 구글 회원가입
	public String memberJoinGoogleView() {
		return "memberJoinGoogle" ;
	}
	
	@RequestMapping("joinGoogle.me") // 기능 : 구글 회원가입
	public String joinGoogle(@RequestParam("googleId") String googleId,
							 @RequestParam("nickName") String nickName,
							 @RequestParam("userName") String userName,
							 @RequestParam("phone") String phone,
							 @RequestParam("gender") String gender,
							 @RequestParam("year") Integer year,
							 @RequestParam("month") Integer month,
							 @RequestParam("date") Integer date,
							 @RequestParam("post") Integer post,
							 @RequestParam("address1") String address1,
							 @RequestParam("address2") String address2,
							 @RequestParam("mKind") Integer mKind,
							 @RequestParam("uploadFiles") MultipartFile uploadFile,
							 HttpServletRequest request) {
		
		Date birthDate = new Date(new GregorianCalendar(year,month-1,date).getTimeInMillis()) ;
		
		Member m = new Member() ;
		m.setUserId(googleId) ;
		m.setNickName(nickName) ;
		m.setUserName(userName) ;
		m.setGender(gender) ;
		m.setEmail(googleId) ;
		m.setBirthDate(birthDate) ;
		m.setmKind(mKind) ;
		
		if(!phone.equals("#empty")) {
			m.setPhone(phone) ;
		}
		
		if(!post.equals("#empty")) {
			m.setPostalCode(post) ;
			m.setbAddress(address1) ;
			m.setlAddress(address2) ;
		}
		
		if(uploadFile != null && !uploadFile.isEmpty()) {
			String filePath = request.getSession().getServletContext().getRealPath("resources") + "\\uploadFiles" ;
			String renameFileName = saveFile(uploadFile, request) ;
			if(renameFileName != null) {
				m.setOriginName(uploadFile.getOriginalFilename()) ;
				m.setChangeName(renameFileName) ;
				m.setImgSrc(filePath) ;
			}
		}
		
		int result1 = mService.insertMember(m) ;
		int result2 = mService.insertUserImg(m) ;
		
		if(result1>0 && result2>0) {
			if(mKind == 2) {
				int tmpRst = mService.insertTeacher(m.getUserId()) ;
				if(tmpRst < 1)
					throw new MemberException("구글 회원가입에 실패하였습니다. (id:"+m.getUserId()+")") ;
			}
			return "redirect:loginView.me" ;
		} else {
			throw new MemberException("일반 회원가입에 실패하였습니다. (id:"+m.getUserId()+")") ;
		}
	}
	
	@RequestMapping("dupGoogle.me") // AJAX : 구글 아이디 중복 검사
	public void dupGoogle(HttpServletResponse response, @RequestParam("googleId") String googleId) throws IOException {
		boolean isUsable = (mService.checkGoogleDup(googleId) < 1) ? true : false ;
		response.getWriter().print(isUsable) ;
	}
 //	 - 구글 회원가입 End
//	회원가입 End ----------------------------------------------------------------------------------------------------------------
	
//	아이디 찾기 Start ----------------------------------------------------------------------------------------------------------------
	@RequestMapping("findId.me") // VIEW : 아이디 찾기
	public String findIdView() {
		return "idFindForm" ;
	}
	
	@RequestMapping("findIdVal.me") // 기능 : 아이디 찾기
	public void findingId(HttpServletResponse response, @RequestParam("userName") String userName, @RequestParam("email") String email) throws IOException {
		int usable = mService.isExist(email) ;
		if(usable == 1) {
			Member m = new Member() ;
			m.setUserName(userName) ;
			m.setEmail(email) ;
			
			Member loginUser = mService.findid(m) ;
			if(m.getUserName().equals(loginUser.getUserName())) {
				response.getWriter().print(loginUser.getUserId()) ;
			} else {
				response.getWriter().print("0") ;
			}
		} else {
			response.getWriter().print("0") ;
		}
	}
//	아이디 찾기 End ----------------------------------------------------------------------------------------------------------------
	
//	비밀번호 찾기 Start ----------------------------------------------------------------------------------------------------------------
	@RequestMapping("findPwd.me") // VIEW : 비밀번호 찾기
	public String findPwdView() {
		return "pwdFindForm" ;
	}
	
	@RequestMapping("findPwdVal.me") // 기능 : 비밀번호 찾기(메일 api)
	public void findingPwd(HttpServletResponse response,
						   @RequestParam("userId") String userId,
						   @RequestParam("userName") String userName,
						   @RequestParam("email") String email) throws IOException {
		int usable = mService.isExist(email) ;
		if(usable == 1) {
			Member m = new Member() ;
			m.setUserId(userId) ;
			m.setUserName(userName) ;
			m.setEmail(email) ;
			
			Member loginUser = mService.findid(m) ;
			if(m.getUserId().equals(loginUser.getUserId()) && m.getUserName().equals(loginUser.getUserName())) {
				String from = "homis.hobbyfarm@gmail.com" ; // 보내는 메일
				String title = "#취미텃밭 : 비밀번호 확인" ;		// 제목
				String ranText = new TempKey().getKey() ;
				StringBuffer content = new StringBuffer() ;
				content.append("<form id=\"mailForm\" style='background: white; width: 80%: height: 80%; text-align: center; margin-left: 2%;'>") ;
				content.append("<div><br><br>") ;
				content.append("<h1>비밀번호 변경</h1>") ;
				content.append("<p><b>#취미텃밭</b>에서 보내진 메일입니다.<p>") ;
				content.append("<p>본인이 보내신 메일이 아니라면 ["+from+"]로 도용 여부를 알려주세요!<p><br>") ;
				content.append("<div id=\"codeArea\" style='border: 2px solid; text-align:center; width: 40%; margin-left: 30%; margin-right: 30%; border-radius: 50px;'><h2>"+ranText+"</h2></div><br><br><br>") ;
				content.append("</div>") ;
				content.append("</form>") ;
				
				mService.updatePwd(m.getUserId(), passEncoder.encode(ranText)) ;
				
				MimeMessage message = mailSender.createMimeMessage() ;
				MimeMessageHelper messageHelper ;
				
				try {
					messageHelper = new MimeMessageHelper(message, true, "UTF-8") ;
					messageHelper.setFrom(from) ;		// 보내는사람 생략하면 정상작동을 안함
					messageHelper.setTo(m.getEmail()) ; // 받는사람 이메일
					messageHelper.setSubject(title) ;	// 메일제목은 생략이 가능하다
					messageHelper.setText(content.toString(), true) ;	// 메일 내용
					mailSender.send(message) ;
					response.getWriter().print("1") ;
				} catch (MessagingException e) {
					e.printStackTrace() ;
				} 
			} else {
				response.getWriter().print("0") ;
			}
		} else {
			response.getWriter().print("0") ;
		}
	}
//	비밀번호 찾기 End ----------------------------------------------------------------------------------------------------------------
	
//	내 정보 보기&수정 Start ----------------------------------------------------------------------------------------------------------------
 //	- 내 정보 보기 Start
	@RequestMapping("myinfoView.me") // VIEW : 내 정보 보기 화면
	public String myInfoView() {
		return "myInfoView" ;
	}
	
	@RequestMapping("infoN.me") // VIEW : 내 정보 보기 - 일반
	public String infoNormal() {
		return "memberDetailNormal" ;
	}
	
	@RequestMapping("infoG.me") // VIEW : 내 정보 보기 - 구글 
	public String infoGoogle() {
		return "memberDetailGoogle" ;
	}
 //	- 내 정보 보기 End
	
 // - 회원 탈퇴 Start
	@RequestMapping("delete.me") // ajax : 회원 삭제
	public void deleteMember(@RequestParam("userId") String userId,
							 HttpServletResponse response) throws IOException {
		int result = mService.deleteMember(userId) ;
		if(result > 0) {
			String sysdate = new SimpleDateFormat("yy/MM/dd").format(Calendar.getInstance().getTime()) ;
			mService.countUser2(sysdate) ;
			response.getWriter().print(true) ;
		} else {
			response.getWriter().print(false) ;
		}
	}
 // - 회원 탈퇴 End
 // - 비밀번호 수정 Start
	@RequestMapping("changePwdView.me") // VIEW : 비밀번호 변경으로 이동
	public String changePwdView() {
		return "memberUpdatePwd" ;
	}
	
	@RequestMapping("changePwd.me") // AJAX : 비밀번호 변경
	public void changePwd(@RequestParam("userPwd") String userPwd,
						  HttpSession session,
						  HttpServletResponse response,
						  SessionStatus status) throws IOException {
		String userId = ((Member)session.getAttribute("loginUser")).getUserId() ;
		int result = mService.updatePwd(userId, passEncoder.encode(userPwd)) ;
		if(result>0) {
			status.setComplete() ;
			response.getWriter().write("true") ;
		} else {
			response.getWriter().write("false") ;
		}
	}
	
	@RequestMapping("checkPwd.me") // AJAX : 비밀번호 비교
	@ResponseBody
	public String checkPwd(HttpSession session, 
						   @RequestParam("userPwd") String userPwd) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId() ;
		String pwd = mService.getPwd(id) ;
		boolean passMatch = passEncoder.matches(userPwd, pwd) ;
		return passMatch+"" ; 	
	}
	
//	@RequestMapping("changePwd.me") // 기능 : 비밀번호 변경
//	public void changePwd(HttpSession session,
//						  @RequestParam("userPwd") String userPwd,
//						  HttpServletResponse response) throws IOException {
//		passEncoder.encode(userPwd) ;
//		String userId = ((Member)session.getAttribute("loginUser")).getUserId() ;
//		int result = mService.updatePwd(userId, passEncoder.encode(userPwd)) ;
//		if(result>0) {
//			response.getWriter().write("true") ;
//		} else {
//			throw new MemberException("비밀번호 수정에 실패했습니다. [아이디:"+userId+"]") ;
//		}
//	}
 // - 비밀번호 수정 End
 // - 회원 수정 Start
	@RequestMapping("updateGView.me") // VIEW : 내 정보 수정 : Google
	public String updateGView() {
		return "memberUpdateGoogle" ;
	}
	
	@RequestMapping("updateNView.me") // VIEW : 내 정보 수정 : Normal
	public ModelAndView updateNView(ModelAndView mv) {
		ArrayList<Hobby> hlist = gfService.selectHList() ;
		mv.addObject("hlist", hlist).setViewName("memberUpdateNormal") ;
		return mv ;
	}
	
	@RequestMapping("updateN.me") // 기능 : 일반 회원수정
	public String memberUpdate(	@RequestParam("userId") String userId,
								@RequestParam("mKind") Integer mKind,
								@RequestParam("nickName") String nickName,
								@RequestParam("userName") String userName,
								@RequestParam("joinEmail") String email,
								@RequestParam("phone") String phone,
								@RequestParam("gender") String gender,
								@RequestParam("year") Integer year,
								@RequestParam("month") Integer month,
								@RequestParam("date") Integer date,
								@RequestParam("post") Integer post,
								@RequestParam("address1") String address1,
								@RequestParam("address2") String address2,
								@RequestParam("introduction") String introduction,
								@RequestParam("sido") String sido,
								@RequestParam("gugun") String gugun,
								@RequestParam("hobby") Integer hobby,
								@RequestParam("imgUsable") String imgUsable,
								@RequestParam("uploadFiles") MultipartFile uploadFile,
								HttpServletRequest request,
								Model model) {
		
		Date birthDate = new Date(new GregorianCalendar(year,month-1,date).getTimeInMillis()) ;
		Member m = new Member() ;
		m.setUserId(userId) ;
		m.setNickName(nickName) ;
		m.setUserName(userName) ;
		m.setEmail(email) ;
		m.setGender(gender) ;
		m.setBirthDate(birthDate) ;
		m.setIntroduction(introduction) ;
		
		if(mKind == 2) {
			String activeLoc = sido + " " + gugun ;
			m.setActiveLoc(activeLoc) ;
			m.setHobbyNo(hobby) ;
			mService.updateTeacher(m) ;
		}
		
		if(!phone.equals("#empty")) {
			m.setPhone(phone) ;
		}
		
		if(!post.equals("#empty")) {
			m.setPostalCode(post) ;
			m.setbAddress(address1) ;
			m.setlAddress(address2) ;
		}
		
		int result1 = mService.updateMember(m) ;
		if(imgUsable.equals("oldImg") && uploadFile != null && !uploadFile.isEmpty()) {
			String filePath = request.getSession().getServletContext().getRealPath("resources") + "\\uploadFiles" ;
			String renameFileName = saveFile(uploadFile, request) ;
			if(renameFileName != null) {
				m.setOriginName(uploadFile.getOriginalFilename()) ;
				m.setChangeName(renameFileName) ;
				m.setImgSrc(filePath) ;
			}
			
			int result2 = mService.updateUserImg(m) ;
			
			if(result1 > 0 && result2 > 0) {
				Member loginUser = mService.memberLogin(m) ;
				model.addAttribute("loginUser", loginUser) ;
				return "redirect:infoN.me" ;
			} else {
				throw new MemberException("일반 회원 수정에 실패하였습니다. (id:"+m.getUserId()+")") ;
			}
		} else {
			if(result1 > 0) {
				Member loginUser = mService.memberLogin(m) ;
				model.addAttribute("loginUser", loginUser) ;
				return "redirect:infoN.me" ;
			} else {
				throw new MemberException("일반 회원 수정에 실패하였습니다. (id:"+m.getUserId()+")") ;
			}
		}
	}
 // - 회원 수정 End
//	내 정보 보기&수정 End ----------------------------------------------------------------------------------------------------------------
}
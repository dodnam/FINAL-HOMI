package finalProject.homis.hobbyFarm.message.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.friends.model.exception.FriendsException;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.message.model.exception.MessageException;
import finalProject.homis.hobbyFarm.message.model.service.MessageService;
import finalProject.homis.hobbyFarm.message.model.vo.Message;
import finalProject.homis.hobbyFarm.message.model.vo.Pagination;

@SessionAttributes("loginUser")
@Controller
public class messageController {
	
	@Autowired
	private MessageService msgService;
	
	@RequestMapping("msgList.msg")
	public ModelAndView msgList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv, HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		if(page != null) { currentPage = page;}
		
		int listCount = msgService.getListCount(id);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Message> list = msgService.selectList(pi, id);
		
		if(list != null) {
			mv.addObject("list", list).addObject("pi", pi).setViewName("getMsg");
		} else {
			throw new MessageException("쪽지함 조회에 실패하였습니다.");
		}
		return mv;
	}
	
	@RequestMapping("sentMsgList.msg")
	public ModelAndView sentMsgList(@RequestParam(value="page", required=false) Integer page, ModelAndView mv, HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int currentPage = 1;
		if(page != null) { currentPage = page;}
		
		int listCount = msgService.sentListCount(id);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		ArrayList<Message> list = msgService.selectsentList(pi, id);
		
		if(list != null) {
			mv.addObject("list", list).addObject("pi", pi).setViewName("sentMsg");
		} else {
			throw new MessageException("쪽지함 조회에 실패하였습니다.");
		}
		return mv;
	}
	
	@RequestMapping("detail.msg")
	public ModelAndView msgDetail(@RequestParam(value="page", required=false) Integer page, int mNo, String msg_from, String msg_to, ModelAndView mv, HttpSession session) {
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		Message message = new Message();
		message.setmNo(mNo);
		
		if(msg_to.equals("false")) { //from 값이 들어왔다면(내가 받은 편지)
			// 내가 받은것만 조회수가 올라야 하기 때문에(읽음 표시)
			message.setMsg_from(msg_from);
			int result = msgService.updateMessageCount(message);
			
			if(result > 0) {
				message = msgService.selectMsg(message);
			} else {
				throw new MessageException("쪽지 조회수 변경에 실패했습니다.");
			}
		}
		if(msg_from.equals("false")) { //to 값이 들어왔다면(내가 보낸 편지)
			message.setMsg_to(msg_to);
			message = msgService.selectMsg(message);
		}
		
		if(message != null) {
			mv.addObject("page", page).addObject("message", message).setViewName("msgDetail");
			return mv;
		} else {
			throw new MessageException("쪽지 조회에 실패했습니다.");
		}
	}
	
	@RequestMapping("deleteGetMsg.msg")
	public String msgGetDelete(@RequestParam("checkVal") String checkVal, @RequestParam("id") String id) {
		String[] msgArr = checkVal.split(",");
		
		int result = 0;
		
		for(int i = 0; i < msgArr.length; i++) {
			int mNo = Integer.parseInt(msgArr[i]);
			result += msgService.deleteGetMsg(mNo);
		}
		
		if(result == msgArr.length) {
			return "redirect:msgList.msg";
		} else {
			throw new MessageException("쪽지 삭제에 실패했습니다.");
		}
	}
	
	@RequestMapping("deleteSentMsg.msg")
	public String msgSentDelete(@RequestParam("checkVal") String checkVal, @RequestParam("id") String id) {
		String[] msgArr = checkVal.split(",");
		
		int result = 0;
		
		for(int i = 0; i < msgArr.length; i++) {
			int mNo = Integer.parseInt(msgArr[i]);
			result += msgService.deleteSentMsg(mNo);
		}
		
		if(result == msgArr.length) {
			return "redirect:sentMsgList.msg";
		} else {
			throw new MessageException("쪽지 삭제에 실패했습니다.");
		}
	}
	
	@RequestMapping("insertForm.msg")
	public ModelAndView msgInsertForm(ModelAndView mv, @RequestParam(value="to", required=false) String to,@RequestParam(value="nick", required=false) String nick, HttpSession session) { 
																	
		Message message = new Message();
		
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		
		/* 친구 목록 불러오기 */
		ArrayList<Member> friendList = msgService.selectFriendsList(id); 
		if(friendList != null) {
	         mv.addObject("friendList", friendList);
	      } else {
	         throw new FriendsException("친구 조회에 실패하였습니다.");
	      }
		/* 친구 목록 불러오기 끝 */
		
		if(nick != null) { //닉네임이 들어왔을 경우 상대방 아이디 넣기
			String[] toArr = nick.split(",");
			String msg_to = "";
			for(int i = 0; i < toArr.length; i++) {
				//상대방 닉네임의 id 가져오기
				Member member = msgService.findNick(toArr[i]);
				String to_id = (msgService.findNick(toArr[i].trim())).getUserId();
				if(i == toArr.length -1) {
					msg_to += to_id;
				} else {
					msg_to += to_id + ",";
				}
			}
			message.setMsg_to(msg_to);
		} else if(to != null) { // 아이디가 들어왔을 경우
			String[] toArr = to.split(",");
			String msg_to = "";
			for(int i = 0; i < toArr.length; i++) {
				if(i == toArr.length -1) {
					msg_to += toArr[i];
				} else {
					msg_to += toArr[i] + ",";
				}
			}
			message.setMsg_to(msg_to);
		}
		
		mv.addObject("message", message).setViewName("sendMsgForm");
		
		return mv;
	}
	
	@RequestMapping("insertMsg.msg")
	public String msgInsert(String msg_to, String msg_title, String msg_content, HttpSession session) {
		
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		int mKind = ((Member)session.getAttribute("loginUser")).getmKind();
		
		String[] toArr = msg_to.split(",");
		
		int result = 0;
		
		for(int i = 0; i < toArr.length; i++) {
			Message message = new Message();
			//상대방 닉네임의 id 가져오기
			String to_id = toArr[i].trim();
			
			if(to_id != null) {
				message.setMsg_to(to_id);
				message.setMsg_from(id);
				message.setMsg_title(msg_title);
				message.setMsg_content(msg_content);
				
				result += msgService.insertMsg(message);
				
			} else {
				throw new MessageException("해당 닉네임을 찾을 수 없습니다.");
			}
		}
		if(result == toArr.length) {
			return "redirect:msgList.msg";
		} else {
			throw new MessageException("쪽지 전송에 실패했습니다.");
		}
		
	}
	
	// 강사 초대 메세지(친구 초대는 GroupFarmController)
	@RequestMapping("invite.msg")
	public String inviteMsg(HttpSession session, 
							@RequestParam("postNo") Integer postNo, 
							@RequestParam("title") String title, 
							@RequestParam("to") String msg_to) {
		Message message = new Message();
		// 로그인 한 유저 아이디 가져오기
		String id = ((Member)session.getAttribute("loginUser")).getUserId();
		String nickName = ((Member)session.getAttribute("loginUser")).getNickName();
		String url = "\"window.open('bdetail.gf?postNo="+postNo+"')\"";
		
		System.out.println(url);
		
		message.setMsg_to(msg_to); //받을사람 id
		message.setMsg_from(id);
		message.setMsg_title("#"+title + " 모임초대입니다!"); //쪽지 제목
		String content = "<span>안녕하세요, <img id='logo'/>입니다!</span><br>" +
						 "<span>" + nickName + " 님께서 회원님을 #" + title + " 모임의 <b>지도강사</b>로 초대하셨습니다.</span><br>" + 
						 "<span>" + nickName + " 님이 모집하고 계신 모임 텃밭이 궁금하시다면 아래 버튼을 눌러 해당 게시글로 바로 이동해보세요!</span><br>" + 
						 "<div id='boardBtnWrapper'>" + 
							 "<button id='goDetail' onclick=" + url + "'>보러가기</button>"+
						 "</div>";
		
		message.setMsg_content(content);
		
		int result = msgService.insertMsg(message);
		
		if(result > 0) {
			return "redirect:msgList.msg";
		} else {
			throw new MessageException("초대 전송에 실패했습니다.");
		}
	}
}

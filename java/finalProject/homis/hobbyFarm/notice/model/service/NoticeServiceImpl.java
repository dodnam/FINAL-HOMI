package finalProject.homis.hobbyFarm.notice.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.notice.model.dao.NoticeDAO;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

@Service("nService")
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDAO nDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getListCount() {
		return nDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectList(PageInfo pi) {
		return nDAO.selectList(sqlSession, pi);
	}

	@Override
	public int insertNotice(Notice n) {
		return nDAO.insertNotice(sqlSession, n);
	}

	@Override
	public Notice selectNotice(int post_no) {
		return nDAO.selectNotice(sqlSession, post_no);
	}

	@Override
	public int addReadCount(int post_no) {
		return nDAO.addReadCount(sqlSession, post_no);
	}

	@Override
	public int updateNotice(Notice n) {
		return nDAO.updateNotice(sqlSession, n);
	}
	
	@Override
	public int deleteNotice(int post_no) {
		return nDAO.deleteNotice(sqlSession, post_no);
	}
	
	@Override
	public int getTitleListCount(String nWord) {
		return nDAO.getTitleListCount(sqlSession, nWord);
	}

	@Override
	public int getContentListCount(String nWord) {
		return nDAO.getContentListCount(sqlSession, nWord);
	}
	
	@Override
	public ArrayList<Notice> selectTitleList(PageInfo pi, String nWord) {
		return nDAO.selectTitleList(sqlSession, pi, nWord);
	}

	@Override
	public ArrayList<Notice> selectContentList(PageInfo pi, String nWord) {
		return nDAO.selectContentList(sqlSession, pi, nWord);
	}

	




}

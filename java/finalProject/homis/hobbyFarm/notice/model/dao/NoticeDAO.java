package finalProject.homis.hobbyFarm.notice.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

@Repository("nDAO")
public class NoticeDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("noticeMapper.getListCount");
	}

	public ArrayList<Notice> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("noticeMapper.selectList", null, rowBounds);
	}

	public int insertNotice(SqlSessionTemplate sqlSession, Notice n) {
		return sqlSession.insert("noticeMapper.insertNotice", n);
	}

	public Notice selectNotice(SqlSessionTemplate sqlSession, int post_no) {
		return sqlSession.selectOne("noticeMapper.selectNotice", post_no);
	}

	public int addReadCount(SqlSessionTemplate sqlSession, int post_no) {
		return sqlSession.update("noticeMapper.addReadCount", post_no);
	}

	public int updateNotice(SqlSessionTemplate sqlSession, Notice n) {
		
		int result1 = sqlSession.update("noticeMapper.updateNotice1", n);
		int result2 = sqlSession.update("noticeMapper.updateNotice2", n);
		
		if(result1 > 0 && result2 > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int deleteNotice(SqlSessionTemplate sqlSession, int post_no) {
		return sqlSession.update("noticeMapper.deleteNotice", post_no);
	}

	public int getTitleListCount(SqlSessionTemplate sqlSession, String nWord) {
		return sqlSession.selectOne("noticeMapper.getTitleListCount", nWord);
	}

	public int getContentListCount(SqlSessionTemplate sqlSession, String nWord) {
		return sqlSession.selectOne("noticeMapper.getContentListCount", nWord);
	}

	public ArrayList<Notice> selectTitleList(SqlSessionTemplate sqlSession, PageInfo pi, String nWord) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("noticeMapper.selectTitleList", nWord, rowBounds);
	}

	public ArrayList<Notice> selectContentList(SqlSessionTemplate sqlSession, PageInfo pi, String nWord) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("noticeMapper.selectContentList", nWord, rowBounds);
	}

	


}
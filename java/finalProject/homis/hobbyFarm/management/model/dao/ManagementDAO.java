package finalProject.homis.hobbyFarm.management.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

@Repository("maDAO")
public class ManagementDAO {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("managementMapper.getListCount");
	}

	public ArrayList<Member> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("managementMapper.selectList", null, rowBounds);
	}

	public int deleteUser(SqlSessionTemplate sqlSession, ArrayList<String> list) {
		return sqlSession.update("managementMapper.deleteUser", list);
	}

	public int getSelectListCount(SqlSessionTemplate sqlSession, SearchSelect ss) {
		return sqlSession.selectOne("managementMapper.selectListCount", ss);
	}

	public ArrayList<Member> selectList(SqlSessionTemplate sqlSession, SearchSelect ss, PageInfo pi) {
		int offset = (pi.getCurrentPage() -1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("managementMapper.selectSearchList", ss, rowBounds);
	}

}

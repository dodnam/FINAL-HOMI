package finalProject.homis.hobbyFarm.management.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.management.model.dao.ManagementDAO;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

@Service("maService")
public class ManagementServiceImpl implements ManagementService{
	
	@Autowired
	private ManagementDAO maDAO;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getListCount() {
		return maDAO.getListCount(sqlSession);
	}

	@Override
	public ArrayList<Member> selectList(PageInfo pi) {
		return maDAO.selectList(sqlSession, pi);
	}

	@Override
	public int deleteUser(ArrayList<String> list) {
		return maDAO.deleteUser(sqlSession, list);
	}

	@Override
	public int getSelectListCount(SearchSelect ss) {
		return maDAO.getSelectListCount(sqlSession, ss);
	}

	@Override
	public ArrayList<Member> selectList(PageInfo pi, SearchSelect ss) {
		return maDAO.selectList(sqlSession, ss, pi);
	}


}

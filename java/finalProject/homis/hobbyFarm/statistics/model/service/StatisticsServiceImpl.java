package finalProject.homis.hobbyFarm.statistics.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.homis.hobbyFarm.statistics.model.dao.StatisticsDAO;
import finalProject.homis.hobbyFarm.statistics.model.vo.Statistics;

@Service("sService")
public class StatisticsServiceImpl implements StatisticsService{
	
	@Autowired
	private StatisticsDAO sDAO;

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int getMemberCount() {
		return sDAO.getMemberCount(sqlSession);
	}

	@Override
	public int getLectureCount() {
		return sDAO.getLectureCount(sqlSession);
	}

	@Override
	public int getVisitCount() {
		return sDAO.getVisitCount(sqlSession);
	}

	@Override
	public int getTeacherCount() {
		return sDAO.getTeacherCount(sqlSession);
	}

	@Override
	public int getStudentCount() {
		return sDAO.getStudentCount(sqlSession);
	}

	@Override
	public ArrayList<Statistics> selectList(String yearMonth) {
		return sDAO.selectList(sqlSession, yearMonth);
	}

	@Override
	public int getSportsCount() {
		return sDAO.getSportsCount(sqlSession);
	}

	@Override
	public int getCookCount() {
		return sDAO.getCookCount(sqlSession);
	}

	@Override
	public int getPictureCount() {
		return sDAO.getPictureCount(sqlSession);
	}

	@Override
	public int getDesignCount() {
		return sDAO.getDesignCount(sqlSession);
	}

	@Override
	public int getMusicCount() {
		return sDAO.getMusicCount(sqlSession);
	}

	@Override
	public int getCraftsCount() {
		return sDAO.getCraftsCount(sqlSession);
	}

	@Override
	public int getArtCount() {
		return sDAO.getArtCount(sqlSession);
	}

	@Override
	public int getBeautyCount() {
		return sDAO.getBeautyCount(sqlSession);
	}

	@Override
	public int getLanguageCount() {
		return sDAO.getLanguageCount(sqlSession);
	}

	@Override
	public int getOtherCount() {
		return sDAO.getOtherCount(sqlSession);
	}

	@Override
	public int getSportsCount2() {
		return sDAO.getSportsCount2(sqlSession);
	}

	@Override
	public int getCookCount2() {
		return sDAO.getCookCount2(sqlSession);
	}

	@Override
	public int getPictureCount2() {
		return sDAO.getPictureCount2(sqlSession);
	}

	@Override
	public int getDesignCount2() {
		return sDAO.getDesignCount2(sqlSession);
	}

	@Override
	public int getMusicCount2() {
		return sDAO.getMusicCount2(sqlSession);
	}

	@Override
	public int getCraftsCount2() {
		return sDAO.getCraftsCount2(sqlSession);
	}

	@Override
	public int getArtCount2() {
		return sDAO.getArtCount2(sqlSession);
	}

	@Override
	public int getBeautyCount2() {
		return sDAO.getBeautyCount2(sqlSession);
	}

	@Override
	public int getLanguageCount2() {
		return sDAO.getLanguageCount2(sqlSession);
	}

	@Override
	public int getOtherCount2() {
		return sDAO.getOtherCount2(sqlSession);
	}
	
	

	
}

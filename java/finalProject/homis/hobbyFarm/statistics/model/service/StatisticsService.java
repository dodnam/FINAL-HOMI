package finalProject.homis.hobbyFarm.statistics.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import finalProject.homis.hobbyFarm.statistics.model.vo.Statistics;

public interface StatisticsService {

	int getMemberCount();

	int getLectureCount();

	int getVisitCount();

	int getTeacherCount();

	int getStudentCount();

	

	ArrayList<Statistics> selectList(String yearMonth);

	int getSportsCount();

	int getCookCount();

	int getPictureCount();

	int getDesignCount();

	int getMusicCount();

	int getCraftsCount();

	int getArtCount();

	int getBeautyCount();

	int getLanguageCount();

	int getOtherCount();
	

	int getSportsCount2();

	int getCookCount2();

	int getPictureCount2();

	int getDesignCount2();

	int getMusicCount2();

	int getCraftsCount2();

	int getArtCount2();

	int getBeautyCount2();

	int getLanguageCount2();

	int getOtherCount2();



}

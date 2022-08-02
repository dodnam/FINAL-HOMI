package finalProject.homis.hobbyFarm.notice.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

public interface NoticeService {

	int getListCount();

	ArrayList<Notice> selectList(PageInfo pi);

	int insertNotice(Notice n);

	Notice selectNotice(int post_no);

	int addReadCount(int post_no);

	int updateNotice(Notice n);

	int deleteNotice(int post_no);

	int getTitleListCount(String nWord);

	ArrayList<Notice> selectTitleList(PageInfo pi, String nWord);

	int getContentListCount(String nWord);

	ArrayList<Notice> selectContentList(PageInfo pi, String nWord);

}
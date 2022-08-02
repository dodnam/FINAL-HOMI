package finalProject.homis.hobbyFarm.management.model.service;

import java.util.ArrayList;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;
import finalProject.homis.hobbyFarm.common.model.vo.SearchSelect;
import finalProject.homis.hobbyFarm.member.model.vo.Member;
import finalProject.homis.hobbyFarm.notice.model.vo.Notice;

public interface ManagementService {

	int getListCount();

	ArrayList<Member> selectList(PageInfo pi);

	int deleteUser(ArrayList<String> list);

	int getSelectListCount(SearchSelect ss);

	ArrayList<Member> selectList(PageInfo pi, SearchSelect ss);



}

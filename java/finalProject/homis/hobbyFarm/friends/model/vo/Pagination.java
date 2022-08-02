package finalProject.homis.hobbyFarm.friends.model.vo;

import finalProject.homis.hobbyFarm.common.model.vo.PageInfo;

public class Pagination {
	   public static PageInfo getPageInfo(int currentPage, int listCount) {
	      
	      PageInfo pi = null;
	      
	      int pageLimit = 5; // �븳 �럹�씠吏��뿉�꽌 蹂댁씪 �럹�씠吏� �닔
	      int maxPage;      // �쟾泥� �럹�씠吏� 以� 留덉�留� �럹�씠吏�
	      int startPage;      // �쁽�옱 �럹�씠吏��뿉�꽌 蹂댁씪 �럹�씠吏� 踰꾪듉�쓽 �떆�옉 �럹�씠吏�
	      int endPage;      // �쁽�옱 �럹�씠吏��뿉�꽌 蹂댁씪 �럹�씠吏� 踰꾪듉�쓽 留덉�留� �럹�씠吏�
	      int boardLimit = 10; // �븳 �럹�씠吏��뿉 蹂댁씪 寃뚯떆湲� �닔
	      
	      maxPage = (int)((double)listCount/boardLimit + 0.9);
	      startPage = (((int)((double)currentPage/pageLimit + 0.9)) - 1) * pageLimit + 1;
	      endPage = startPage + pageLimit - 1;
	      
	      if(maxPage < endPage) {
	         endPage = maxPage;
	      }
	      
	      pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
	      
	      return pi;
	      
	   }
	}
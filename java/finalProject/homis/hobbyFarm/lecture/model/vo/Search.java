package finalProject.homis.hobbyFarm.lecture.model.vo;

public class Search {
	private String searchWhere;
	private String searchTag;
	private String searchValue;
	private int searchCate;
	
	public Search() {}

	public Search(String searchWhere, String searchTag, String searchValue, int searchCate) {
		super();
		this.searchWhere = searchWhere;
		this.searchTag = searchTag;
		this.searchValue = searchValue;
		this.searchCate = searchCate;
	}

	public String getSearchWhere() {
		return searchWhere;
	}

	public void setSearchWhere(String searchWhere) {
		this.searchWhere = searchWhere;
	}

	public String getSearchTag() {
		return searchTag;
	}

	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getSearchCate() {
		return searchCate;
	}

	public void setSearchCate(int searchCate) {
		this.searchCate = searchCate;
	}

	@Override
	public String toString() {
		return "Search [searchWhere=" + searchWhere + ", searchTag=" + searchTag + ", searchValue=" + searchValue
				+ ", searchCate=" + searchCate + "]";
	}
	
	
	
}

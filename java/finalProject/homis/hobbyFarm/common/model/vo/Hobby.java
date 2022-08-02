package finalProject.homis.hobbyFarm.common.model.vo;

public class Hobby {

	public int hobbyNo;
	public String hobbyName;
	
	public Hobby() {}

	public Hobby(int hobbyNo, String hobbyName) {
		super();
		this.hobbyNo = hobbyNo;
		this.hobbyName = hobbyName;
	}

	public int getHobbyNo() {
		return hobbyNo;
	}

	public void setHobbyNo(int hobbyNo) {
		this.hobbyNo = hobbyNo;
	}

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	@Override
	public String toString() {
		return "Hobby [hobbyNo=" + hobbyNo + ", hobbyName=" + hobbyName + "]";
	}
	
	
}

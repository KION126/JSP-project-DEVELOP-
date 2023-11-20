package Enrol;

public class EnrolDTO {
	private int enrolID;
    private String userID;
    private int classID;

    public EnrolDTO(int enrolID, String userID, int classID) {
		super();
		this.enrolID = enrolID;
		this.userID = userID;
		this.classID = classID;
	}

    public EnrolDTO(String userID) {
		super();
		this.userID = userID;
	}

	public int getEnrolID() {
		return enrolID;
	}

	public void setEnrolID(int enrolID) {
		this.enrolID = enrolID;
	}

	public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}

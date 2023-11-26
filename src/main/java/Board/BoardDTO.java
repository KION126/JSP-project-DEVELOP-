package Board;

public class BoardDTO {
	int boardType;
	int boardID;
	String userID;
	String userName;
	int classID;
	String boardDate;
	String boardTitle;
	String boardContent;
	int boardHit;
	String boardFile;
	String boardRealFile;
	
	public BoardDTO(int boardID, String userID, String boardDate, String boardTitle, int boardHit) {
		super();
		this.boardID = boardID;
		this.userID = userID;
		this.boardDate = boardDate;
		this.boardTitle = boardTitle;
		this.boardHit = boardHit;
	}
	
	public BoardDTO(int boardType, String userID, String boardDate, String boardTitle, String boardContent, int boardHit, String boardFile, String boardRealFile) {
		super();
		this.boardType = boardType;
		this.userID = userID;
		this.boardDate = boardDate;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardHit = boardHit;
		this.boardFile = boardFile;
		this.boardRealFile = boardRealFile;
	}	

	public BoardDTO(int boardType, int boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile) {
		super();
		this.boardType = boardType;
		this.boardID = boardID;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardFile = boardFile;
		this.boardRealFile = boardRealFile;
	}

	public BoardDTO(int boardType, String userID, int classID, String boardTitle, String boardContent, int boardHit, String boardFile, String boardRealFile) {
		super();
		this.boardType = boardType;
		this.userID = userID;
		this.classID = classID;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardHit = boardHit;
		this.boardFile = boardFile;
		this.boardRealFile = boardRealFile;
	}

	public int getboardID() {
		return boardID;
	}
	public void setboardID(int boardID) {
		this.boardID = boardID;
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
	public String getboardDate() {
		return boardDate;
	}
	public void setboardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getboardTitle() {
		return boardTitle;
	}
	public void setboardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getboardContent() {
		return boardContent;
	}
	public void setboardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getboardHit() {
		return boardHit;
	}
	public void setboardHit(int boardHit) {
		this.boardHit = boardHit;
	}
	public String getboardFile() {
		return boardFile;
	}
	public void setboardFile(String boardFile) {
		this.boardFile = boardFile;
	}
	public String getboardRealFile() {
		return boardRealFile;
	}
	public void setboardRealFile(String boardRealFile) {
		this.boardRealFile = boardRealFile;
	}
	public int getBoardType() {
		return boardType;
	}
	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}
}

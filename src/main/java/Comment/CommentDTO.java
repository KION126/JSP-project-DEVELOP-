package Comment;

import User.UserDAO;

public class CommentDTO {
	int commentID;
	int boardID;
	String userID;
	String userName;
	String commentContent;
	String commentDate;

	public CommentDTO(String userID, String userName, String commentContent, String commentDate) {
		super();
		this.userID = userID;
		UserDAO DAO = new UserDAO();
		this.userName = DAO.getUserName(userID);
		this.commentContent = commentContent;
		this.commentDate = commentDate;
	}
	
	public CommentDTO(int boardID, String userID, String userName, String commentContent) {
		super();
		this.boardID = boardID;
		this.userID = userID;
		this.userName = userName;
		this.commentContent = commentContent;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public int getBoardID() {
		return boardID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	
	
}

package Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Board.BoardDTO;
import util.DatabaseUtill;

public class CommentDAO {
	public int insertData(CommentDTO dto) {
        String sql = "INSERT INTO comment (boardID, userID, userName, commentContent, commentDate) VALUES (?, ?, ?, ?, sysdate())";
        Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
        	pstmt.setInt(1, dto.getBoardID());
        	pstmt.setString(2, dto.getUserID());
        	pstmt.setString(3, dto.getUserName());
        	pstmt.setString(4, dto.getCommentContent().replace("\r\n", "<br>"));
        	return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
        return -1;
    }
	public int updateData(CommentDTO dto) {
	    String sql = "UPDATE comment SET commentContent=?, commentDate=sysdate() WHERE commentID=?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);

        	pstmt.setString(1, dto.getCommentContent());
        	pstmt.setInt(2, dto.getCommentID());

	        return pstmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // close connections
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return -1;
	}
	public int deleteComment(int commentID) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = DatabaseUtill.getConnection();
	        conn.setAutoCommit(false);  // 트랜잭션 시작

	        String sql = "DELETE FROM comment WHERE commentID = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, commentID);

	        int result = pstmt.executeUpdate();

	        if (result > 0) {
	            conn.commit();  // 트랜잭션 커밋
	        } else {
	            conn.rollback();  // 트랜잭션 롤백
	        }

	        return result;
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) conn.rollback();  // 예외 발생 시 롤백
	        } catch (Exception rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return -1;
	}
	public List<CommentDTO> getLists(int boardID, int startRow, int endRow) {
	    List<CommentDTO> comment = new ArrayList<>();
	    String sql = "SELECT * FROM comment WHERE boardID = ? ORDER BY commentID DESC LIMIT ?, ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardID);
	        pstmt.setInt(2, startRow);
	        pstmt.setInt(3, endRow);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	CommentDTO commentInfo = new CommentDTO(
	        			rs.getInt("commentID"),
	                    rs.getString("userID"),
	                    rs.getString("userName"),
	                    rs.getString("commentContent".replaceAll("<br>", "\n")),
	                    rs.getString("commentDate")
	            );
	            comment.add(commentInfo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 리소스 닫기
	    }
	    return comment;
	}
	public boolean isCommentExists(int boardID, String userID, String commentContent) {
        String sql = "SELECT COUNT(*) FROM comment WHERE boardID = ? AND userID = ? AND commentContent = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardID);
            pstmt.setString(2, userID);
            pstmt.setString(3, commentContent);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 리소스 닫기
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
	public int getTotalRecords(int boardID) {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM comment WHERE boardID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 리소스 닫기
        }

        return totalRecords;
    }
}

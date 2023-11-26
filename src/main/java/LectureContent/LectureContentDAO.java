package LectureContent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Board.BoardDTO;
import util.DatabaseUtill;

public class LectureContentDAO {
	// LectureContentDAO의 insertData 메서드 수정
	public int insertData(LectureContentDTO dto) {
	    String sql = "INSERT INTO LectureContent (lectureID, week, title, file, realFile, startDate, endDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet generatedKeys = null;
	    
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	        
	        pstmt.setInt(1, dto.getLectureID());
	        pstmt.setInt(2, dto.getWeek());
	        pstmt.setString(3, dto.getTitle());
	        pstmt.setString(4, dto.getFile());
	        pstmt.setString(5, dto.getRealFile());
	        pstmt.setString(6, dto.getStartDate());
	        pstmt.setString(7, dto.getEndDate());

	        int affectedRows = pstmt.executeUpdate();
	        
	        if (affectedRows == 0) {
	            throw new SQLException("Inserting lecture content failed, no rows affected.");
	        }

	        // 삽입된 contentID 가져오기
	        generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Inserting lecture content failed, no ID obtained.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (generatedKeys != null) generatedKeys.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
	        try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
	    }
	    return -1;
	}

	
	public List<LectureContentDTO> getLists(int lectureID) {
	    List<LectureContentDTO> lectureContents = new ArrayList<>();
	    String sql = "SELECT * FROM LectureContent WHERE lectureID = ? ORDER BY week";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, lectureID);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	        	LectureContentDTO lectureContentInfo = new LectureContentDTO(
	        			rs.getInt("contentID"),
	        			rs.getInt("lectureID"),
	                    rs.getInt("week"),
	                    rs.getString("title"),
	                    rs.getString("file"),
	                    rs.getString("realFile"),
	                    rs.getString("startDate"),
	                    rs.getString("endDate")
	            );
	        	lectureContents.add(lectureContentInfo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
	    }
	    return lectureContents;
	}
	
	public int getContentID(LectureContentDTO dto) {
        String sql = "INSERT INTO LectureContent (lectureID, week, title, file, realFile, srartDate, endDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
        	pstmt.setInt(1, dto.getLectureID());
        	pstmt.setInt(2, dto.getWeek());
        	pstmt.setString(3, dto.getTitle());
        	pstmt.setString(4, dto.getFile());
        	pstmt.setString(5, dto.getRealFile());
        	pstmt.setString(6, dto.getStartDate());
        	pstmt.setString(7, dto.getEndDate());
        	return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
        return -1;
    }
	public String getFile(int contentID) {
	    String sql = "SELECT file FROM LectureContent WHERE contentID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("file");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
	    }
	    return "";
	}
	
	public String getRealFile(int contentID) {
	    String sql = "SELECT realFile FROM LectureContent WHERE contentID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("realFile");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
	    }
	    return "";
	}
	
	public String getContentTitle(int contentID) {
	    String sql = "SELECT title FROM LectureContent WHERE contentID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("title");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
	    }
	    return "";
	}
	public int deleteContent(int contentID) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = DatabaseUtill.getConnection();
	        conn.setAutoCommit(false);  // 트랜잭션 시작

	        String sql = "DELETE FROM lectureContent WHERE contentID = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, contentID);

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
}

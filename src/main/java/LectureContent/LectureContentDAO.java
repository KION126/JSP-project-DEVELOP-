package LectureContent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Board.BoardDTO;
import util.DatabaseUtill;

public class LectureContentDAO {
	public int insertData(LectureContentDTO dto) {
        String sql = "INSERT INTO LectureContent (lectureID, week, title, date, video, file, realFile) VALUES (?, ?, ?, sysdate(), ?, ?, ?)";
        Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
        	pstmt.setInt(1, dto.getLectureID());
        	pstmt.setInt(2, dto.getWeek());
        	pstmt.setString(3, dto.getTitle());
        	pstmt.setString(4, dto.getVideo());
        	pstmt.setString(5, dto.getFile());
        	pstmt.setString(6, dto.getRealFile());
        	return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
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
	                    rs.getInt("week"),
	                    rs.getString("title"),
	                    rs.getString("date"),
	                    rs.getString("video"),
	                    rs.getString("file"),
	                    rs.getString("realFile")
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
}

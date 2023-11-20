package Enrol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseUtill;

public class EnrolDAO {
	public List<EnrolDTO> getMyClassID(String userID) {
		List<EnrolDTO> myClassIDs = new ArrayList<>();
        String sql = "SELECT classID FROM enrol WHERE userID = ?";
        Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	 EnrolDTO classID = new EnrolDTO(userID);
                 classID.setClassID(rs.getInt("classID"));
                 myClassIDs.add(classID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
			try {if (rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
		}
        return myClassIDs;
    }
	public void enrolClass(String userID, int classId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = DatabaseUtill.getConnection();
        try {
            // 해당 클래스의 정원을 증가시킵니다.
            String updateSql = "UPDATE class SET classPersonnel = classPersonnel + 1 WHERE classId = ?";
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, classId);
            pstmt.executeUpdate();
            pstmt.close();

            // 사용자를 수강 등록합니다.
            String insertSql = "INSERT INTO enrol (userID, classId) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, userID);
            pstmt.setInt(2, classId);
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
    }
	public boolean classEnrolCheck(String userID, int classId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = DatabaseUtill.getConnection();
        try {
            // 사용자가 해당 클래스를 수강했는지 확인합니다.
            String checkSql = "SELECT * FROM enrol WHERE userID = ? AND classId = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, userID);
            pstmt.setInt(2, classId);
            if (pstmt.executeQuery().next()) {
                // 이미 수강한 경우
                return true;
            }
        
            return false;	//수강 안한 경우
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
    }

}

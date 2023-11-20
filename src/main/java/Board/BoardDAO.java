package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseUtill;

public class BoardDAO {
	public List<BoardDTO> getLists(int boardType, int classID, int startRow, int endRow) {
	    List<BoardDTO> notice = new ArrayList<>();
	    String sql = "SELECT * FROM board WHERE boardType = ? AND classID = ? ORDER BY boardID DESC LIMIT ?, ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardType);
	        pstmt.setInt(2, classID);
	        pstmt.setInt(3, startRow);
	        pstmt.setInt(4, endRow);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            BoardDTO noticeInfo = new BoardDTO(
	                    rs.getInt("boardID"),
	                    rs.getString("userID"),
	                    rs.getString("boardDate"),
	                    rs.getString("boardTitle"),
	                    rs.getInt("boardHit")
	            );
	            notice.add(noticeInfo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 리소스 닫기
	    }
	    return notice;
	}
	
	public int insertData(BoardDTO dto) {
        String sql = "INSERT INTO board (boardType, userID, classID, boardDate, boardTitle, boardContent, boardHit, boardFile, boardRealFile) VALUES (?, ?, ?, sysdate(), ?, ?, ?, ?, ?)";
        Connection conn = null;
		PreparedStatement pstmt = null;
        try {
        	conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getBoardType());
        	pstmt.setString(2, dto.getUserID());
        	pstmt.setInt(3, dto.getClassID());
        	pstmt.setString(4, dto.getboardTitle());
        	pstmt.setString(5, dto.getboardContent().replace("\r\n", "<br>"));
        	pstmt.setInt(6, dto.getboardHit());
        	pstmt.setString(7, dto.getboardFile());
        	pstmt.setString(8, dto.getboardRealFile());
        	return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
			try {if (conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
			try {if (pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
		}
        return -1;
    }
	public int updateData(BoardDTO dto) {
	    String sql = "UPDATE board SET boardTitle=?, boardContent=?, boardFile=?, boardRealFile=?, boardDate=sysdate() WHERE boardType=? AND boardID=?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, dto.getboardTitle());
	        pstmt.setString(2, dto.getboardContent().replace("\r\n", "<br>"));
	        pstmt.setString(3, dto.getboardFile());
	        pstmt.setString(4, dto.getboardRealFile());
	        
	        pstmt.setInt(5, dto.getBoardType());
	        pstmt.setInt(6, dto.getboardID());

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
	public int hit(int boardID) {
	    String sql = "UPDATE board SET boardHit = boardHit + 1 WHERE boardID=?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setInt(1, boardID);

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
	public int deleteNotice(int boardID) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = DatabaseUtill.getConnection();
	        conn.setAutoCommit(false);  // 트랜잭션 시작

	        String sql = "DELETE FROM board WHERE boardID = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardID);

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
	
	public List<BoardDTO> getDate(int boardID) {
	    List<BoardDTO> notice = new ArrayList<>();
	    String sql = "SELECT * FROM board WHERE boardID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            BoardDTO noticeInfo = new BoardDTO(
	            	rs.getString("userID"),
	                rs.getString("boardDate"),
	                rs.getString("boardTitle"),
	                rs.getString("boardContent"),
	                rs.getInt("boardHit"),
	                rs.getString("boardFile"),
	                rs.getString("boardRealFile")
	            );
	            notice.add(noticeInfo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 리소스 닫기
	    }
	    return notice;
	}
	
	public String getFile(int boardID) {
	    String sql = "SELECT boardFile FROM board WHERE boardID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("boardFile");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 리소스 닫기
	    }
	    return "";
	}
	
	public String getRealFile(int boardID) {
	    String sql = "SELECT boardRealFile FROM board WHERE boardID = ?";
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardID);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            return rs.getString("boardRealFile");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // 리소스 닫기
	    }
	    return "";
	}
	
	public int getTotalRecords(int boardType, int classID) {
        int totalRecords = 0;
        String sql = "SELECT COUNT(*) FROM board WHERE boardType=? AND classID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtill.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardType);
            pstmt.setInt(2, classID);
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
	
	public int getSearchTotalRecords(int boardType, int classID, String searchOption, String searchKeyword) {
	    int totalRecords = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    // SQL 쿼리를 검색 옵션에 따라 다르게 작성
	    String sql = "SELECT COUNT(*) FROM board WHERE boardType=? AND classID = ? ";
	    if (searchOption.equals("title")) {
	        sql += "AND boardTitle LIKE ?";
	    } else if (searchOption.equals("content")) {
	        sql += "AND boardContent LIKE ?";
	    } else if (searchOption.equals("titleAndContent")) {
	        sql += "AND (boardTitle LIKE ? OR boardContent LIKE ?)";
	    }

	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardType);
	        pstmt.setInt(2, classID);

	        // 검색 옵션에 따라서 ?에 대입할 값이 1개 또는 2개가 됨
	        if (searchOption.equals("title") || searchOption.equals("content")) {
	            pstmt.setString(2, "%" + searchKeyword + "%");
	        } else if (searchOption.equals("titleAndContent")) {
	            pstmt.setString(2, "%" + searchKeyword + "%");
	            pstmt.setString(3, "%" + searchKeyword + "%");
	        }

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            totalRecords = rs.getInt(1);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // close connections
	    }

	    return totalRecords;
	}

	
	public List<BoardDTO> searchNotices(int boardType, int classID, String searchOption, String searchKeyword, int startRow, int endRow) {
	    List<BoardDTO> notice = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    // SQL 쿼리를 검색 옵션에 따라 다르게 작성
	    String sql = "SELECT * FROM notice WHERE boardType=? AND classID = ? AND ";
	    if (searchOption.equals("title")) {
	        sql += "boardTitle LIKE ?";
	    } else if (searchOption.equals("content")) {
	        sql += "boardContent LIKE ?";
	    } else if (searchOption.equals("titleAndContent")) {
	        sql += "(boardTitle LIKE ? OR boardContent LIKE ?)";
	    }

	    sql += " ORDER BY boardID DESC LIMIT ?, ?";

	    try {
	        conn = DatabaseUtill.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardType);
	        pstmt.setInt(2, classID);

	        // 검색 옵션에 따라서 ?에 대입할 값이 1개 또는 2개가 됨
	        if (searchOption.equals("title") || searchOption.equals("content")) {
	            pstmt.setString(2, "%" + searchKeyword + "%");
	            pstmt.setInt(3, startRow);
	            pstmt.setInt(4, endRow);
	        } else if (searchOption.equals("titleAndContent")) {
	            pstmt.setString(2, "%" + searchKeyword + "%");
	            pstmt.setString(3, "%" + searchKeyword + "%");
	            pstmt.setInt(4, startRow);
	            pstmt.setInt(5, endRow);
	        }

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            BoardDTO noticeInfo = new BoardDTO(
	                    rs.getInt("boardID"),
	                    rs.getString("userID"),
	                    rs.getString("boardDate"),
	                    rs.getString("boardTitle"),
	                    rs.getInt("boardHit")
	            );
	            notice.add(noticeInfo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // close connections
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return notice;
	}

}

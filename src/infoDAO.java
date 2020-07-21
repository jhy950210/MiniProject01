import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class infoDAO {
	private Connection conn = null;
	
	public infoDAO() {
		// TODO Auto-generated constructor stub
		// 데이터 연결
		String url = "jdbc:mysql://localhost:3306/minipj";
		String user = "root";
		String password = "123456";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			System.out.println("드라이버 로딩 완료");
			conn = DriverManager.getConnection(url, user, password);
	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("[에러] : " + e.getMessage());
		} 
		
	}
	
	public ArrayList<infoTO> showInfo(String inputId){
		// sido 데이터 검색
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<infoTO> datas =  new ArrayList<infoTO>();

		try {
			String sql = "select searchName, searchTime from memberinfo where id = ? order by searchTime desc limit 10";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String searchName = rs.getString("searchName");
				String searchTime = rs.getString("searchTime");
				
				infoTO data = new infoTO();
				
				data.setSearchName(searchName);
				data.setSearchTime(searchTime);
				
				datas.add(data);
			}
		} catch(SQLException e) {
			System.out.println("[에러] : " + e.getMessage());
		}finally {
			if(pstmt!=null) try {pstmt.close();} catch(SQLException e) {}
			if(conn!=null) try {conn.close();} catch(SQLException e) {}
			if(rs!=null) try {rs.close();} catch(SQLException e) {}
		}
		
		return datas;
	}
}

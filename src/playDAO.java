import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class playDAO {
	private Connection conn = null;
	
	public playDAO() {
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
	
	public ArrayList<playTO> showPlay(String inputPlaceName){
		// sido 데이터 검색
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<playTO> datas =  new ArrayList<playTO>();

		try {
			String sql = "select distinct comName, basisAdr, detailAdr, phone,  placeName, zipcode, placeStatus, closeDate from play where placeName like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputPlaceName +"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String comName = rs.getString("comName");
				String basisAdr = rs.getString("basisAdr");
				String detailAdr = rs.getString("detailAdr");
				String phone = rs.getString("phone");
				String placeName = rs.getString("placeName");
				String zipcode = rs.getString("zipcode");
				String placeStatus = rs.getString("placeStatus");
				String closeDate = rs.getString("closeDate");
				
				
				playTO data = new playTO();
				
				data.setComName(comName);
				data.setBasisAdr(basisAdr);
				data.setDetailAdr(detailAdr);
				data.setPhone(phone);
				data.setPlaceName(placeName);
				data.setZipcode(zipcode);
				data.setPlaceStatus(placeStatus);
				data.setCloseDate(closeDate);
				
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

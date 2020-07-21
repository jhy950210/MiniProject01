import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class PlayTableModel extends AbstractTableModel {
	private ArrayList<playTO> values = new ArrayList<playTO>();
	private String[] names = new String[] {
			"업체명", "기본주소", "상세주소", "전화번호", "영업소명", "우편번호", "영업상태", "폐업일자"
	};
	
	
	public PlayTableModel(String inputPlaceName) {	
		playDAO dao = new playDAO();
		values = dao.showPlay(inputPlaceName);
	}
	
	public PlayTableModel() {	
		values = null;
	}

//	public ZipTableModel(String dongName) {
//		// TODO Auto-generated constructor stub
//		ZipcodeDAO dao = new ZipcodeDAO();
//		values = dao.allZipcode(dongName);
//	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return names[column];
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 8;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		playTO to = values.get(rowIndex);
		String result = "";
		switch(columnIndex) {
		case 0:
			result = to.getComName();
			break;
		case 1:
			result = to.getBasisAdr();
			break;
		case 2:
			result = to.getDetailAdr();
			break;
		case 3:
			result = to.getPhone();
			break;
		case 4:
			result = to.getPlaceName();
			break;
		case 5:
			result = to.getZipcode();
			break;
		case 6:
			result = to.getPlaceStatus();
			break;
		case 7:
			result = to.getCloseDate();
			break;
		
		}
		return result;
	}

}

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class InfoTableModel extends AbstractTableModel {
	private ArrayList<infoTO> values = new ArrayList<infoTO>();
	private String[] names = new String[] {
			"영업소명", "검색날짜" 
	};
	
	
	public InfoTableModel(String inputId) {	
		infoDAO dao = new infoDAO();
		values = dao.showInfo(inputId);
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
		return 2;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		infoTO to = values.get(rowIndex);
		String result = "";
		switch(columnIndex) {
		case 0:
			result = to.getSearchName();
			break;
		case 1:
			result = to.getSearchTime();
			break;

		}
		return result;
	}

}

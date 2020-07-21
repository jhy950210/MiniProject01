import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	
	private String data_1;
	private String data_2;
	private String data_3;
	private String data_4;
	private String data_5;
	private String data_6;
	private String data_7;
	private String data_8;
	
	private String id = null;
	private String name = "";
	private JLabel lbl;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		setTitle("유원지 관리시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\uC601\uC5C5\uC18C\uBA85 \uC785\uB825", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(142, 45, 617, 47);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btn1 = new JButton("찾기");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id==null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인하세요.","경고",JOptionPane.WARNING_MESSAGE);
				}else {
					String str = textField.getText();
					table.setModel(new PlayTableModel(str));
					table.setVisible(true);
				}
			}
		});
		btn1.setBounds(486, 17, 119, 23);
		panel.add(btn1);
		
		textField = new JTextField();
		textField.setBounds(12, 18, 462, 22);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btn2 = new JButton("유원지 추가");
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id == null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if(id.equals("root")) {
					PlayCreate createPlayDia = new PlayCreate();
				
					createPlayDia.setModal(true);
					createPlayDia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					createPlayDia.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainUI.this, "접근 불가입니다.","경고",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btn2.setBounds(324, 16, 137, 23);
		contentPane.add(btn2);
		
		JButton btn3 = new JButton("유원지 수정");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id == null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if(id.equals("root")) {
					data_1 = (String)table.getValueAt(table.getSelectedRow(), 0); 
					data_2 = (String)table.getValueAt(table.getSelectedRow(), 1);
					data_3 = (String)table.getValueAt(table.getSelectedRow(), 2);
					data_4 = (String)table.getValueAt(table.getSelectedRow(), 3);
					data_5 = (String)table.getValueAt(table.getSelectedRow(), 4);
					data_6 = (String)table.getValueAt(table.getSelectedRow(), 5);
					data_7 = (String)table.getValueAt(table.getSelectedRow(), 6);
					data_8 = (String)table.getValueAt(table.getSelectedRow(), 7);
				
				
					PlayUpdate updatePlayDia = new PlayUpdate(data_1, data_2, data_3, data_4, data_5, data_6, data_7, data_8);
				
					updatePlayDia.setModal(true);
					updatePlayDia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					updatePlayDia.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(MainUI.this, "접근 불가입니다.","경고",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btn3.setBounds(473, 16, 137, 23);
		contentPane.add(btn3);
		
		JButton btn4 = new JButton("유원지 삭제");
		btn4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id == null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
				}
				else if(id.equals("root")) {
					
					data_5 = (String)table.getValueAt(table.getSelectedRow(), 4);
					int result = JOptionPane.showConfirmDialog(MainUI.this, "삭제하시겠습니까?", "삭제", JOptionPane.OK_CANCEL_OPTION);
					if(result == JOptionPane.OK_OPTION) {
						String url = "jdbc:mysql://localhost:3306/minipj";
						String user = "root";
						String password = "123456";
					
						Connection conn = null;
						PreparedStatement pstmt = null;
					
						try {
							Class.forName("org.mariadb.jdbc.Driver");
							conn = DriverManager.getConnection(url, user, password);
						
							String sql = "delete from play where placeName = ?";
							pstmt = conn.prepareStatement(sql);
						
							pstmt.setString(1, data_5);
							pstmt.executeUpdate();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							if(pstmt!=null) try {pstmt.close();} catch(SQLException e1) {}
							if(conn!=null) try {conn.close();} catch(SQLException e1) {}
						}
					
					}
				} else {
					JOptionPane.showMessageDialog(MainUI.this, "접근 불가입니다.","경고",JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		btn4.setBounds(622, 16, 137, 23);
		contentPane.add(btn4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 102, 747, 217);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String url = "jdbc:mysql://localhost:3306/minipj";
				String user = "root";
				String password = "123456";
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				
				try {
					Class.forName("org.mariadb.jdbc.Driver");
					conn = DriverManager.getConnection(url, user, password);
					
					String sql = "insert into memberinfo value(?, ?, ?, now())";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, name);
					pstmt.setString(2, id);
					pstmt.setString(3, (String)table.getValueAt(table.getSelectedRow(), 4));
					
					pstmt.executeUpdate();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					if(pstmt!=null) try {pstmt.close();} catch(SQLException e1) {}
					if(conn!=null) try {conn.close();} catch(SQLException e1) {}
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{},
			},
			new String[] {
			}
		));
		
		JButton btn5 = new JButton("로그인");
		btn5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id != null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인 상태입니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					Login login = new Login();
				
					login.setModal(true);
					login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					login.setVisible(true);
				
					name = login.getName();
					id = login.getId();
					
					if(name != null) {
						lbl.setText("사용자 : " + name);
					}
				}
				
			}
		});
		btn5.setBounds(12, 55, 118, 29);
		contentPane.add(btn5);
		
		JButton btn6 = new JButton("회원가입");
		btn6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Membership membership = new Membership();
				
				membership.setModal(true);
				membership.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				membership.setVisible(true);
				
			}
		});
		btn6.setBounds(12, 329, 103, 23);
		contentPane.add(btn6);
		
		JButton btn7 = new JButton("회원정보");
		btn7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id == null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
				}else {
					MemberInfo memberinfo = new MemberInfo(id, name);
					
					memberinfo.setModal(true);
					memberinfo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					memberinfo.setVisible(true);
				}
			}
		});
		btn7.setBounds(127, 329, 108, 23);
		contentPane.add(btn7);
		
		JButton btn8 = new JButton("로그아웃");
		btn8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(id == null) {
					JOptionPane.showMessageDialog(MainUI.this, "로그인 상태가 아닙니다.", "경고", JOptionPane.WARNING_MESSAGE);
				} else {
					int result = JOptionPane.showConfirmDialog(MainUI.this, "로그아웃하시겠습니까?","로그아웃",JOptionPane.OK_CANCEL_OPTION);
					if(result == JOptionPane.OK_OPTION) {
						id = null;
						name = null;
						table.setVisible(false);
						lbl.setText("로그아웃되었습니다.");
					}
				}
			}
		});
		btn8.setBounds(663, 330, 96, 23);
		contentPane.add(btn8);
		
		lbl = new JLabel("로그인 후 이용가능합니다.");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBounds(12, 16, 254, 23);
		contentPane.add(lbl);
	}
}

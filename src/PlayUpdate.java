import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayUpdate extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;


	/**
	 * Create the dialog.
	 */
	public PlayUpdate(String data1, String data2, String data3, String data4, String data5, String data6, String data7, String data8) {
		setTitle("유원지 수정");
		setBounds(100, 100, 450, 326);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(112, 10, 310, 21);
			textField.setText(data1);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(112, 41, 310, 21);
			textField_1.setText(data2);
			contentPanel.add(textField_1);
		}
		{
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(112, 72, 310, 21);
			textField_2.setText(data3);
			contentPanel.add(textField_2);
		}
		{
			textField_3 = new JTextField();
			textField_3.setColumns(10);
			textField_3.setBounds(112, 103, 310, 21);
			textField_3.setText(data4);
			contentPanel.add(textField_3);
		}
		{
			textField_4 = new JTextField();
			textField_4.setColumns(10);
			textField_4.setBounds(112, 134, 310, 21);
			textField_4.setText(data5);
			contentPanel.add(textField_4);
		}
		{
			textField_5 = new JTextField();
			textField_5.setColumns(10);
			textField_5.setBounds(112, 165, 310, 21);
			textField_5.setText(data6);
			contentPanel.add(textField_5);
		}
		{
			textField_6 = new JTextField();
			textField_6.setColumns(10);
			textField_6.setBounds(112, 196, 310, 21);
			textField_6.setText(data7);
			contentPanel.add(textField_6);
		}
		{
			textField_7 = new JTextField();
			textField_7.setColumns(10);
			textField_7.setBounds(112, 227, 310, 21);
			textField_7.setText(data8);
			contentPanel.add(textField_7);
		}
		
		JLabel lblNewLabel = new JLabel("업체명");
		lblNewLabel.setBounds(23, 13, 57, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("기본주소");
		lblNewLabel_1.setBounds(23, 44, 57, 15);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("상세주소");
		lblNewLabel_2.setBounds(23, 75, 57, 15);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("전화번호");
		lblNewLabel_3.setBounds(23, 106, 57, 15);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("영업소명");
		lblNewLabel_4.setBounds(23, 137, 57, 15);
		contentPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("우편번호");
		lblNewLabel_5.setBounds(23, 168, 57, 15);
		contentPanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("영업소상태");
		lblNewLabel_6.setBounds(23, 199, 77, 15);
		contentPanel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("폐업일자");
		lblNewLabel_7.setBounds(23, 230, 57, 15);
		contentPanel.add(lblNewLabel_7);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
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
							
							String sql = "update play set comName = ?, basisAdr = ?, detailAdr = ?, phone = ?, placeName = ?, zipcode = ?, placeStatus = ?, closeDate = ? where placeName = ?";
							pstmt = conn.prepareStatement(sql);
							
							pstmt.setString(1, textField.getText());
							pstmt.setString(2, textField_1.getText());
							pstmt.setString(3, textField_2.getText());
							pstmt.setString(4, textField_3.getText());
							pstmt.setString(5, textField_4.getText());
							pstmt.setString(6, textField_5.getText());
							pstmt.setString(7, textField_6.getText());
							pstmt.setString(8, textField_7.getText());
							pstmt.setString(9, data5);
							
							pstmt.executeUpdate();
							
							PlayUpdate.this.dispose();
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
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						PlayUpdate.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

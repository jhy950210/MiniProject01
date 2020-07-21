import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Membership extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Membership dialog = new Membership();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Membership() {
		setTitle("회원가입");
		setBounds(100, 100, 261, 184);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbl1 = new JLabel("이름");
		lbl1.setBounds(12, 10, 96, 23);
		contentPanel.add(lbl1);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(12, 43, 96, 23);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("PASSWORD");
		lblNewLabel_2.setBounds(12, 76, 96, 23);
		contentPanel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(105, 11, 128, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(105, 43, 128, 21);
		contentPanel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(105, 76, 128, 21);
		contentPanel.add(textField_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						if(textField.getText().length() > 8) {
							JOptionPane.showMessageDialog(Membership.this, "이름을 8자 이하로 입력하세요.","경고",JOptionPane.WARNING_MESSAGE);
						} else if( textField_1.getText().length() > 10) {
							JOptionPane.showMessageDialog(Membership.this, "아이디를 10자 이하로 입력하세요.","경고",JOptionPane.WARNING_MESSAGE);
						} else if(textField_2.getText().length() != 4) {
							JOptionPane.showMessageDialog(Membership.this, "비밀번호는 반드시 4자리로 입력하세요.","경고",JOptionPane.WARNING_MESSAGE);
						} else {
							String url = "jdbc:mysql://localhost:3306/minipj";
							String user = "root";
							String password = "123456";
							Connection conn = null;
							PreparedStatement pstmt = null;
							try {
								Class.forName("org.mariadb.jdbc.Driver");
								conn = DriverManager.getConnection(url, user, password);
							
								String sql = "insert into member value(?, ?, ?)";
								pstmt = conn.prepareStatement(sql);
							
								pstmt.setString(1, textField.getText());
								pstmt.setString(2, textField_1.getText());
								pstmt.setString(3, textField_2.getText());
							
								pstmt.executeUpdate();
							
								Membership.this.dispose();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(Membership.this, "이미 있는 아이디입니다.","경고",JOptionPane.WARNING_MESSAGE);
							} finally {
								if(pstmt!=null) try {pstmt.close();} catch(SQLException e1) {}
								if(conn!=null) try {conn.close();} catch(SQLException e1) {}
							}
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
						Membership.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	
	}
}


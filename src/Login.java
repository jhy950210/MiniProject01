import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;
	private String id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("로그인");
		setBounds(100, 100, 337, 173);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(101, 19, 202, 21);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(12, 22, 77, 15);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("PASSWORD");
			lblNewLabel_1.setBounds(12, 58, 77, 15);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 100, 321, 35);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
						ResultSet rs1 = null;
						ResultSet rs2 = null;
						
						try {
							Class.forName("org.mariadb.jdbc.Driver");
							conn = DriverManager.getConnection(url, user, password);
							
							String sql = "select * from member where id = ?";
							pstmt = conn.prepareStatement(sql);
							
							pstmt.setString(1, textField.getText());
							
							rs1 = pstmt.executeQuery();
							if(rs1.next()) {
								if(rs1.getString("password").equals(passwordField.getText())) {
									id = textField.getText();
									String sql2 = "select name from member where id = ?";
									pstmt = conn.prepareStatement(sql2);
									pstmt.setString(1, id);
									rs2 = pstmt.executeQuery();
									while(rs2.next()) {
										name = rs2.getString("name");
									}
									Login.this.dispose();
								}else {
									JOptionPane.showMessageDialog(Login.this, "패스워드가 틀렸습니다.","경고",JOptionPane.WARNING_MESSAGE);
								}
								
							} else {
								JOptionPane.showMessageDialog(Login.this, "없는 아이디입니다.","경고",JOptionPane.WARNING_MESSAGE);
							}
							
							
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							if(pstmt!=null) try {pstmt.close();} catch(SQLException e1) {}
							if(conn!=null) try {conn.close();} catch(SQLException e1) {}
							if(rs1!=null) try {rs1.close();} catch(SQLException e1) {}
							if(rs2!=null) try {rs2.close();} catch(SQLException e1) {}
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
						Login.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		passwordField = new JPasswordField();
		passwordField.setBounds(101, 55, 202, 21);
		contentPanel.add(passwordField);
	}
}

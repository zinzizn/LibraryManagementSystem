import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField userName;
	private JPasswordField password;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login");
		setFont(new Font("Dialog", Font.PLAIN, 18));
		getContentPane().setLayout(null);
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(46, 108, 105, 25);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(46, 189, 105, 25);
		contentPane.add(lblPassword);
		
		userName = new JTextField();
		userName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userName.setBounds(217, 108, 221, 31);
		contentPane.add(userName);
		userName.setColumns(10);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name="Admin";
				String pass="Admin123";
				String username=userName.getText();
				String psw=password.getText();
				
				if(name.equals(username) && pass.equals(psw)) {
					
					Login.this.dispose();
					Category category=new Category();
					category.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(login, "Username or Password do not match!");
					userName.setText("");
					password.setText("");
					userName.requestFocus();
;				}
			}
		});
		login.setBackground(new Color(192, 192, 192));
		login.setFont(new Font("Tahoma", Font.PLAIN, 20));
		login.setBounds(157, 274, 105, 44);
		contentPane.add(login);
		
		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel_1.setBounds(227, 11, 105, 44);
		contentPane.add(lblNewLabel_1);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.this.dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setBackground(new Color(192, 192, 192));
		btnCancel.setBounds(314, 274, 124, 44);
		contentPane.add(btnCancel);
		
		password = new JPasswordField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 18));
		password.setBounds(217, 183, 221, 31);
		contentPane.add(password);
		
		JCheckBox cbxShow = new JCheckBox("Show");
		cbxShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbxShow.isSelected()) {
					password.setEchoChar((char)0);
				}
				else {
					password.setEchoChar('*');
				}
			}
		});
		cbxShow.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cbxShow.setBounds(444, 189, 97, 23);
		contentPane.add(cbxShow);
	}
}

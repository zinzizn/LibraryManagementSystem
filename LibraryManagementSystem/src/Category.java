import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Category extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Category frame = new Category();
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
	public Category() {
		setFont(new Font("Dialog", Font.PLAIN, 20));
		setTitle("Show ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton member = new JButton("Member");
		member.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				MemberReg member=new MemberReg();
				member.setVisible(true);
			}
		});
		member.setFont(new Font("Tahoma", Font.PLAIN, 20));
		member.setBounds(45, 175, 175, 55);
		contentPane.add(member);
		
		JButton rental = new JButton("Return Book");
		rental.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				Return_Book returnBook=new Return_Book();
				returnBook.setVisible(true);
			}
		});
		rental.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rental.setBounds(301, 175, 175, 55);
		contentPane.add(rental);
		
		JButton books = new JButton("Books");
		books.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				Books book=new Books();
				book.setVisible(true);
			}
		});
		books.setFont(new Font("Tahoma", Font.PLAIN, 20));
		books.setBounds(45, 259, 175, 55);
		contentPane.add(books);
		
		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				AddCategory addCategory=new AddCategory();
				addCategory.setVisible(true);
			}
		});
		btnCategory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCategory.setBounds(45, 95, 175, 55);
		contentPane.add(btnCategory);
		
		JButton btnIss = new JButton("Issue Book");
		btnIss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				Issued_Book issue=new Issued_Book();
				issue.setVisible(true);
			}
			
		});
		btnIss.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnIss.setBounds(301, 95, 175, 55);
		contentPane.add(btnIss);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Category.this.dispose();
				Login login=new Login();
				login.setVisible(true);
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLogOut.setBounds(301, 259, 175, 55);
		contentPane.add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("Library Management");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(145, 11, 291, 42);
		contentPane.add(lblNewLabel);
	}
}

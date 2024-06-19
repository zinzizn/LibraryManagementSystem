import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Return_Book extends JFrame {

	private JPanel contentPane;
	private JTextField txtMid;
	private JTable tblReturn;
	private JLabel lblNameOutput,lblBookName,lblReturnDate;
	Database_Connection dbconn=new Database_Connection();
	private JTextField txtDayElap;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Return_Book frame = new Return_Book();
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
	public Return_Book() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					dbconn.Connect();
					loadReturnBook();
					dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel.setBounds(25, 108, 46, 27);
		contentPane.add(lblNewLabel);
		
		txtMid = new JTextField();
		txtMid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					dbconn.Connect();
					int mid=Integer.parseInt(txtMid.getText());
					String sql = "SELECT m.Username, b.BName, l.returnDate, DATEDIFF(NOW(), l.returnDate) AS elap " +
				             "FROM lend_book l " +
				             "JOIN member m ON l.memberid = m.MID " +
				             "JOIN book b ON l.bookid = b.BID and l.memberid="+mid;

					ResultSet result = dbconn.stmt.executeQuery(sql);
					if(result.next()==false) {
						JOptionPane.showMessageDialog(null, "Member ID not found!");
					}
					else {
						String membername=result.getString("Username");
						String bookname=result.getString("BName");
						String returnDate=result.getString("returnDate");
						String elap=result.getString("elap");
						
						lblNameOutput.setText(membername.trim());
						lblBookName.setText(bookname.trim());
						lblReturnDate.setText(returnDate);
						txtDayElap.setText(elap);
						
					}
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		txtMid.setColumns(10);
		txtMid.setBounds(133, 108, 138, 20);
		contentPane.add(txtMid);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id=Integer.parseInt(txtMid.getText());
				String mname=lblNameOutput.getText();
				String bname=lblBookName.getText();
				String returnDate=lblReturnDate.getText();
				int elap=Integer.parseInt(txtDayElap.getText());
				try {
					dbconn.Connect();
					dbconn.ReturnBook(id, mname, bname, returnDate, elap);
					loadReturnBook();
					String sql="Delete from lend_book where memberid="+id;
					dbconn.stmt.execute(sql);
					txtMid.setText("");
					lblNameOutput.setText("");
					lblBookName.setText("");
					lblReturnDate.setText("");
					txtDayElap.setText("");
					
					
					dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnAdd.setBounds(133, 318, 138, 31);
		contentPane.add(btnAdd);
		
		JLabel lblIssuedBooks = new JLabel("Return Books");
		lblIssuedBooks.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblIssuedBooks.setBounds(255, 11, 187, 31);
		contentPane.add(lblIssuedBooks);
		
		JLabel lblMid = new JLabel("Name");
		lblMid.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblMid.setBounds(25, 149, 58, 20);
		contentPane.add(lblMid);
		
		JLabel lblNewLabel_3_1 = new JLabel("Return Date");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_3_1.setBounds(25, 222, 110, 27);
		contentPane.add(lblNewLabel_3_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Return_Book.this.dispose();
				Category cate=new Category();
				cate.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnBack.setBounds(601, 58, 138, 31);
		contentPane.add(btnBack);
		
		JLabel lblBook = new JLabel("Book");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblBook.setBounds(25, 191, 58, 20);
		contentPane.add(lblBook);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(295, 107, 444, 322);
		contentPane.add(scrollPane);
		
		tblReturn = new JTable();
		scrollPane.setViewportView(tblReturn);
		
		 lblNameOutput = new JLabel("");
		lblNameOutput.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNameOutput.setBounds(133, 149, 138, 20);
		contentPane.add(lblNameOutput);
		
		 lblBookName = new JLabel("");
		lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblBookName.setBounds(133, 191, 138, 20);
		contentPane.add(lblBookName);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Day Elap");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_3_1_1.setBounds(25, 260, 110, 27);
		contentPane.add(lblNewLabel_3_1_1);
		
		txtDayElap = new JTextField();
		txtDayElap.setBounds(133, 267, 138, 20);
		contentPane.add(txtDayElap);
		txtDayElap.setColumns(10);
		
		 lblReturnDate = new JLabel("");
		lblReturnDate.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblReturnDate.setBounds(133, 229, 138, 20);
		contentPane.add(lblReturnDate);
		
	
	}
	private void loadReturnBook() throws SQLException {
        String sql = "SELECT * FROM return_book";
        ResultSet result = dbconn.stmt.executeQuery(sql);

        // Count rows in ResultSet
        result.last();
        int rowCount = result.getRow();
        result.beforeFirst();

        // Prepare data array
        String[][] data = new String[rowCount][6];
        int index = 0;

        // Iterate through ResultSet and populate data array
        while (result.next()) {
            data[index][0] = result.getString("RID");
            data[index][1] = result.getString("mid");
            data[index][2] = result.getString("mname");
            data[index][3] = result.getString("bname");
            data[index][4] = result.getString("returnDate");
            data[index][5] = result.getString("elap");
            
            
            index++;
        }

        // Define column names
        String[] columnNames = {"ID", "MID", "Member Name", "Book Name","Return Date","Elap Day"};

        // Set data to table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblReturn.setModel(model);
}
}

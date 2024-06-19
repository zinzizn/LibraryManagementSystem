import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Issued_Book extends JFrame {

	private JPanel contentPane;
	private JTextField txtMID;
	private JTextField txtMName;
	private JTable tblIssuedBook;
	private JComboBox<Books> cboBook;
	private JDateChooser dtcLend,dtcReturn;
	Database_Connection dbconn=new Database_Connection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Issued_Book frame = new Issued_Book();
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
	public Issued_Book() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					dbconn.Connect();
					loadIssuedBook();
					BookCategory();
					dbconn.Disconnect();
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel.setBounds(23, 108, 46, 27);
		contentPane.add(lblNewLabel);
		
		txtMID = new JTextField();
		txtMID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					dbconn.Connect();
					int mid=Integer.parseInt(txtMID.getText());
					String sql = "SELECT * FROM member WHERE MID ="+mid;
					ResultSet result = dbconn.stmt.executeQuery(sql);
					if(result.next()==false) {
						JOptionPane.showMessageDialog(null, "Member ID not found!");
					}
					else {
						String membername=result.getString("Username");
						txtMName.setText(membername.trim());
					}
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		txtMID.setBounds(131, 108, 138, 20);
		contentPane.add(txtMID);
		txtMID.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int mid=Integer.parseInt((txtMID.getText()));
			
			Books book=(Books)cboBook.getSelectedItem();
			Date lendDateObj = dtcLend.getDate();
	        Date returnDateObj = dtcReturn.getDate();
			SimpleDateFormat date_format=new SimpleDateFormat("yyyy-MM-dd");
			String lendDate=date_format.format(lendDateObj);
			String returnDate=date_format.format(returnDateObj);
			
			try {
				dbconn.Connect();
				dbconn.LendBook(mid, book.id, lendDate, returnDate);
				loadIssuedBook();
				txtMID.setText("");
				txtMName.setText("");
				cboBook.getSelectedIndex();
				dtcLend.setDateFormatString("");
				dtcReturn.setDateFormatString("");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		});
		btnAdd.setBounds(131, 318, 138, 31);
		contentPane.add(btnAdd);
		
		JLabel lblIssuedBooks = new JLabel("Issued Books");
		lblIssuedBooks.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblIssuedBooks.setBounds(253, 11, 187, 31);
		contentPane.add(lblIssuedBooks);
		
		JLabel lblMid = new JLabel("Name");
		lblMid.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblMid.setBounds(23, 149, 58, 20);
		contentPane.add(lblMid);
		
		txtMName = new JTextField();
		txtMName.setColumns(10);
		txtMName.setBounds(131, 153, 138, 20);
		contentPane.add(txtMName);
		
		JLabel lblLentDate = new JLabel("Lend Date");
		lblLentDate.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblLentDate.setBounds(23, 226, 110, 27);
		contentPane.add(lblLentDate);
		
		JLabel lblNewLabel_3_1 = new JLabel("Return Date");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_3_1.setBounds(23, 270, 110, 27);
		contentPane.add(lblNewLabel_3_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(286, 100, 451, 296);
		contentPane.add(scrollPane);
		
		tblIssuedBook = new JTable();
		scrollPane.setViewportView(tblIssuedBook);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Issued_Book.this.dispose();
				Category category=new Category();
				category.setVisible(true);
			}
			
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnBack.setBounds(599, 58, 138, 31);
		contentPane.add(btnBack);
		
		JLabel lblBook = new JLabel("Book");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblBook.setBounds(23, 191, 58, 20);
		contentPane.add(lblBook);
		
		 dtcLend = new JDateChooser();
		dtcLend.setBounds(131, 237, 138, 20);
		contentPane.add(dtcLend);
		
		dtcReturn = new JDateChooser();
		dtcReturn.setBounds(131, 277, 138, 20);
		contentPane.add(dtcReturn);
		
		cboBook = new JComboBox();
		cboBook.setBounds(131, 193, 138, 22);
		contentPane.add(cboBook);
	}
	public void BookCategory() throws SQLException {
		String sql="Select * from book";
		cboBook.removeAllItems();
		
		ResultSet result = dbconn.stmt.executeQuery(sql);
		while(result.next()) {
			cboBook.addItem(new Books(result.getInt("BID"),result.getString("BName")));
			
		}
	}
	
	private void loadIssuedBook() throws SQLException {
		String sql = "SELECT l.LID, m.Username, b.BName, l.lendDate, l.returnDate FROM lend_book l " +
                "JOIN member m ON l.memberid = m.MID " +
                "JOIN book b ON l.bookid = b.BID";
        ResultSet result = dbconn.stmt.executeQuery(sql);

        // Count rows in ResultSet
        result.last();
        int rowCount = result.getRow();
        result.beforeFirst();

        // Prepare data array
        String[][] data = new String[rowCount][5];
        int index = 0;

        // Iterate through ResultSet and populate data array
        while (result.next()) {
            data[index][0] = result.getString("LID");
            data[index][1] = result.getString("Username");
            data[index][2] = result.getString("BName");
            data[index][3] = result.getString("lendDate");
            data[index][4]=result.getString("returnDate");
            
            
            
            index++;
        }

        // Define column names
        String[] columnNames = {"ID", "Member Name", "Book Name", "Date","Return Date"};

        // Set data to table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblIssuedBook.setModel(model);
}
}

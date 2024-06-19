import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCategory extends JFrame {

	private JPanel contentPane;
	private JTextField txtCategory;
	private JTable tblCategory;
	Database_Connection dbconn=new Database_Connection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCategory frame = new AddCategory();
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
	public AddCategory() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					dbconn.Connect();
					loadCategoryData();
					dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Category");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(314, 11, 129, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Book Category Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 116, 203, 33);
		contentPane.add(lblNewLabel_1);
		
		txtCategory = new JTextField();
		txtCategory.setBounds(223, 116, 159, 30);
		contentPane.add(txtCategory);
		txtCategory.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dbconn.Connect();
					String category=txtCategory.getText();
					dbconn.SaveCategory(category);
					loadCategoryData();
					txtCategory.setText("");
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAdd.setBounds(161, 205, 95, 43);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 66, 352, 285);
		contentPane.add(scrollPane);
		
		tblCategory = new JTable();
		scrollPane.setViewportView(tblCategory);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCategory.this.dispose();
				Category cate=new Category();
				cate.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBack.setBounds(665, 19, 95, 43);
		contentPane.add(btnBack);
	}
	private void loadCategoryData() throws SQLException {
        String sql = "SELECT * FROM Category";
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
            data[index][0] = result.getString("CID");
            data[index][1] = result.getString("CategoryName");
       
            index++;
        }

        // Define column names
        String[] columnNames = {"ID", "Category Name"};

        // Set data to table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblCategory.setModel(model);
}
}

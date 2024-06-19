import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Books extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private JTextField txtBName;
	private JTextField txtAuthor;
	private JTable tblBook;
	private JComboBox<CategoryItem> cboCategory;
	private JComboBox<CategoryItem> cboSearchCategory;
	Database_Connection dbconn=new Database_Connection();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Books frame = new Books();
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
	public Books() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					dbconn.Connect();
					loadBookData();
					Category();
                    dbconn.Disconnect();
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setFont(new Font("Dialog", Font.PLAIN, 25));
		setTitle("Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Books Registration");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(321, 0, 291, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Search");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(413, 79, 85, 22);
		contentPane.add(lblNewLabel_1);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					dbconn.Connect();
					 String searchText = txtSearch.getText();
			         String sql = "SELECT * FROM Book WHERE BName LIKE '%" + searchText + "%' OR Author LIKE '%" + searchText + "%'";
			        ResultSet result = dbconn.stmt.executeQuery(sql);

			        // Count rows in ResultSet
			        result.last();
			        int rowCount = result.getRow();
			        result.beforeFirst();

			        // Prepare data array
			        String[][] data = new String[rowCount][4];
			        int index = 0;

			        // Iterate through ResultSet and populate data array
			        while (result.next()) {
			            data[index][0] = result.getString("BID");
			            data[index][1] = result.getString("BName");
			            data[index][2] = result.getString("Author");
			            data[index][3] = result.getString("Category");
			           
			            
			            index++;
			        }

			        // Define column names
			        String[] columnNames = {"ID", "Book Name", "Author", "Category"};

			        // Set data to table model
			        DefaultTableModel model = new DefaultTableModel(data, columnNames);
			        tblBook.setModel(model);
			        
			        dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		txtSearch.setBounds(493, 82, 211, 22);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Book Name:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1.setBounds(45, 78, 110, 22);
		contentPane.add(lblNewLabel_1_1);
		
		txtBName = new JTextField();
		txtBName.setBounds(165, 77, 193, 31);
		contentPane.add(txtBName);
		txtBName.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Author      :");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1.setBounds(45, 131, 95, 22);
		contentPane.add(lblNewLabel_1_1_1);
		
		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(165, 130, 193, 31);
		contentPane.add(txtAuthor);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Category   :");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1.setBounds(45, 188, 110, 22);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		 cboCategory = new JComboBox();
		cboCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cboCategory.setBounds(165, 185, 193, 31);
		contentPane.add(cboCategory);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
                    String name = txtBName.getText();
                    String author = txtAuthor.getText();
                    CategoryItem categoryItem = (CategoryItem) cboCategory.getSelectedItem();
                    String category = categoryItem != null ? categoryItem.getCategoryName() : "";
                   
                    dbconn.Connect();
                    dbconn.SaveBook(name, author, category);
                    loadBookData();
                    txtBName.setText("");
                    txtAuthor.setText("");
                   
                    cboCategory.setSelectedIndex(-1);
                    dbconn.Disconnect();;
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAdd.setBounds(45, 240, 139, 46);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblBook.getSelectedRow();
		        if(selectedRow!=-1) {
		        	int Id = Integer.parseInt(tblBook.getValueAt(selectedRow, 0).toString());
		        	CategoryItem categoryItem = (CategoryItem) cboCategory.getSelectedItem();
                    String category = categoryItem != null ? categoryItem.getCategoryName() : "";

		        	try {
						dbconn.Connect();
						boolean b=dbconn.stmt.execute("Update Book set BName='"+txtBName.getText()+"',Author='"+txtAuthor.getText()+"',Category='"+category+"' where BID="+Id);
						if(!b) {
							loadBookData();
							txtBName.setText("");
							txtAuthor.setText("");
							
							cboCategory.setSelectedIndex(-1);
						}
					}
		        	catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdate.setBounds(211, 240, 147, 46);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblBook.getSelectedRow();
                if (selectedRow != -1) {
                    int Id = Integer.parseInt(tblBook.getValueAt(selectedRow, 0).toString());
                    try {
                        dbconn.Connect();
                        int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected row?");
                        if (r == 0) {
                            if (!dbconn.stmt.execute("DELETE FROM Book WHERE BID = " + Id)) {
                                loadBookData();
                                txtBName.setText("");
    							txtAuthor.setText("");
    							
    							cboCategory.setSelectedIndex(-1);
                            }
                        }
                        dbconn.Disconnect();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                   // JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDelete.setBounds(45, 313, 139, 46);
		contentPane.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBName.setText("");
				txtAuthor.setText("");
				cboCategory.setSelectedIndex(0);
				
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClear.setBounds(211, 313, 147, 46);
		contentPane.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(412, 164, 440, 267);
		contentPane.add(scrollPane);
		
		tblBook = new JTable();
		tblBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int Id=Integer.parseInt(tblBook.getValueAt(tblBook.getSelectedRow(),0).toString());
				try {
					dbconn.Connect();
					String sql = "SELECT * FROM book where BID="+Id;
			        ResultSet result = dbconn.stmt.executeQuery(sql);
			        while(result.next()) {
			        	
			        	txtBName.setText(result.getString("BName"));
			        	txtAuthor.setText(result.getString("Author"));
			        	cboCategory.setSelectedItem(result.getString("Category"));
			        	
			        	
			        }
			        dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(tblBook);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Books.this.dispose();
				Category category=new Category();
				category.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setBounds(757, 75, 95, 31);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Category  ");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1_1_1_1_1.setBounds(413, 120, 82, 22);
		contentPane.add(lblNewLabel_1_1_1_1_1);
		
		 cboSearchCategory = new JComboBox();
		cboSearchCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					dbconn.Connect();
					
					CategoryItem categoryItem = (CategoryItem) cboSearchCategory.getSelectedItem();
                    String category = categoryItem != null ? categoryItem.getCategoryName() : "";
					 String sql = "SELECT * FROM Book WHERE Category='" + category + "'";
			        ResultSet result = dbconn.stmt.executeQuery(sql);

			        // Count rows in ResultSet
			        result.last();
			        int rowCount = result.getRow();
			        result.beforeFirst();

			        // Prepare data array
			        String[][] data = new String[rowCount][4];
			        int index = 0;

			        // Iterate through ResultSet and populate data array
			        while (result.next()) {
			            data[index][0] = result.getString("BID");
			            data[index][1] = result.getString("BName");
			            data[index][2] = result.getString("Author");
			            data[index][3] = result.getString("Category");
			            
			            
			            index++;
			        }

			        // Define column names
			        String[] columnNames = {"ID", "Book Name", "Author", "Category"};

			        // Set data to table model
			        DefaultTableModel model = new DefaultTableModel(data, columnNames);
			        tblBook.setModel(model);
			        
			        dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		cboSearchCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cboSearchCategory.setBounds(493, 122, 211, 22);
		contentPane.add(cboSearchCategory);
		
		
	}
	
	
	
	private void loadBookData() throws SQLException {
        String sql = "SELECT * FROM Book";
        ResultSet result = dbconn.stmt.executeQuery(sql);

        // Count rows in ResultSet
        result.last();
        int rowCount = result.getRow();
        result.beforeFirst();

        // Prepare data array
        String[][] data = new String[rowCount][4];
        int index = 0;

        // Iterate through ResultSet and populate data array
        while (result.next()) {
            data[index][0] = result.getString("BID");
            data[index][1] = result.getString("BName");
            data[index][2] = result.getString("Author");
            data[index][3] = result.getString("Category");
            
            
            index++;
        }

        // Define column names
        String[] columnNames = {"ID", "Book Name", "Author", "Category"};

        // Set data to table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblBook.setModel(model);
}

	public void Category() throws SQLException {
		String sql="Select * from Category";
		cboCategory.removeAllItems();
		cboSearchCategory.removeAllItems();
		ResultSet result = dbconn.stmt.executeQuery(sql);
		while(result.next()) {
			cboCategory.addItem(new CategoryItem(result.getInt("CID"),result.getString("CategoryName")));
			cboSearchCategory.addItem(new CategoryItem(result.getInt("CID"),result.getString("CategoryName")));
		}
	}
	
	int id;
	String name;
	
	public Books(int i,String n) {
		this.id=i;
		this.name=n;
	}
	public String toString() {
		return name;
	}
	 public String getBookName() {
         return name;
     }
}



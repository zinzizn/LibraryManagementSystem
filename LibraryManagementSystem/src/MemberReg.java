import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MemberReg extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberReg frame = new MemberReg();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Declare attributes
	
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhno;
	private JTextField txtAddr;
	private JTable tblMember;
	
	Database_Connection dbconn=new Database_Connection();
	private JTextField txtSearch;
	
	/**
	 * Create the frame.
	 */
	public MemberReg() {
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					dbconn.Connect();
                    loadMemberData();
                    dbconn.Disconnect();
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setFont(new Font("Dialog", Font.PLAIN, 25));
		setTitle("Member Registration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 567);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Member Registration");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(275, 11, 283, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Name       :");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblName.setBounds(10, 110, 127, 34);
		contentPane.add(lblName);
		
		JLabel lblEmail = new JLabel("Email       :");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblEmail.setBounds(10, 163, 100, 34);
		contentPane.add(lblEmail);
		
		JLabel lblPhoneNo = new JLabel("Phone No :");
		lblPhoneNo.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPhoneNo.setBounds(10, 208, 127, 34);
		contentPane.add(lblPhoneNo);
		
		JLabel lblAddress = new JLabel("Address    :");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblAddress.setBounds(10, 263, 127, 34);
		contentPane.add(lblAddress);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtName.setColumns(10);
		txtName.setBounds(144, 114, 192, 26);
		contentPane.add(txtName);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtEmail.setColumns(10);
		txtEmail.setBounds(144, 167, 192, 26);
		contentPane.add(txtEmail);
		
		txtPhno = new JTextField();
		txtPhno.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtPhno.setColumns(10);
		txtPhno.setBounds(144, 217, 192, 26);
		contentPane.add(txtPhno);
		
		txtAddr = new JTextField();
		txtAddr.setFont(new Font("Tahoma", Font.PLAIN, 19));
		txtAddr.setColumns(10);
		txtAddr.setBounds(144, 267, 192, 26);
		contentPane.add(txtAddr);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(381, 167, 452, 308);
		contentPane.add(scrollPane);
		
		tblMember = new JTable();
		tblMember.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int Id=Integer.parseInt(tblMember.getValueAt(tblMember.getSelectedRow(),0).toString());
				try {
					dbconn.Connect();
					String sql = "SELECT * FROM Member where MID="+Id;
			        ResultSet result = dbconn.stmt.executeQuery(sql);
			        while(result.next()) {
			        	
			        	txtName.setText(result.getString("Username"));
			        	txtEmail.setText(result.getString("Email"));
			        	txtPhno.setText(result.getString("Phno"));
			        	txtAddr.setText(result.getString("Address"));
			        	
			        }
			        dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(tblMember);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
                    String name = txtName.getText();
                    String email = txtEmail.getText();
                    String phno = txtPhno.getText();
                    String addr = txtAddr.getText();
                    

                    dbconn.Connect();
                    dbconn.SaveMember(name, email, phno, addr);
                    loadMemberData();
                    DataClear();
                    dbconn.Disconnect();;
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnAdd.setBounds(7, 323, 127, 34);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Id=Integer.parseInt(tblMember.getValueAt(tblMember.getSelectedRow(),0).toString());
				
				try {
					dbconn.Connect();
				boolean b=dbconn.stmt.execute("Update Member set Username='"+txtName.getText()+"',Email='"+txtEmail.getText()+"',Phno='"+txtPhno.getText()+"',Address='"+txtAddr.getText()+"' where MID="+Id);
					if(!b) {
						loadMemberData();
						DataClear();
					}
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnUpdate.setBounds(189, 323, 139, 34);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int Id=Integer.parseInt(tblMember.getValueAt(tblMember.getSelectedRow(),0).toString());
				try {
					dbconn.Connect();
					int r=JOptionPane.showConfirmDialog(null, "Are you sure delete the selected row?");
					if(r==0) {
						if (!dbconn.stmt.execute("DELETE FROM Member WHERE MID = " + Id)) {
							loadMemberData();
							DataClear();
							//dbconn.Disconnect();
						}
						
					}else {
						
					}
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnDelete.setBounds(6, 376, 127, 34);
		contentPane.add(btnDelete);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Search");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(382, 118, 100, 26);
		contentPane.add(lblNewLabel_1);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					dbconn.Connect();
					String sql = "SELECT * FROM Member Where Username like '%"+txtSearch.getText()+"%'";
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
			            data[index][0] = result.getString("MID");
			            data[index][1] = result.getString("Username");
			            data[index][2] = result.getString("Email");
			            data[index][3] = result.getString("Phno");
			            data[index][4] = result.getString("Address");
			            
			            index++;
			        }

			        // Define column names
			        String[] columnNames = {"ID", "Name", "Email", "Phone No", "Address"};

			        // Set data to table model
			        DefaultTableModel model = new DefaultTableModel(data, columnNames);
			        tblMember.setModel(model);
			        
			        dbconn.Disconnect();
				} 
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		txtSearch.setBounds(451, 118, 283, 26);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataClear();
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnClear.setBounds(187, 376, 139, 34);
		contentPane.add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			MemberReg.this.dispose();
			Category category=new Category();
			category.setVisible(true);
			}
			
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBack.setBounds(744, 117, 89, 26);
		contentPane.add(btnBack);
			
	}

	private void loadMemberData() throws SQLException {
        String sql = "SELECT * FROM Member";
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
            data[index][0] = result.getString("MID");
            data[index][1] = result.getString("Username");
            data[index][2] = result.getString("Email");
            data[index][3] = result.getString("Phno");
            data[index][4] = result.getString("Address");
            
            index++;
        }

        // Define column names
        String[] columnNames = {"ID", "Name", "Email", "Phone No", "Address"};

        // Set data to table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblMember.setModel(model);
    }
	public void DataClear() {
		txtName.setText("");
		txtEmail.setText("");
		txtPhno.setText("");
		txtAddr.setText("");
	}
}

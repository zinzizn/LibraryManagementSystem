import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Resultset;

public class Database_Connection {
private	String url="jdbc:mysql://localhost:3306/librarymanagement";
private	String username="root";
private	String pass="root";
public Connection conn;
public Statement stmt;

	public void Connect() throws SQLException {
		 conn=DriverManager.getConnection(url, username, pass);
		 
		 if (conn != null) {
	            stmt = conn.createStatement();
	            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        }
	}
	
	public void Disconnect() throws SQLException {
		if (stmt != null) {
            stmt.close();
        }
		if(conn != null) {
			conn.close();
		}
	}

	//To insert Member
	public void SaveMember(String name, String email, String phno, String address) throws SQLException {
		String sql = "INSERT INTO Member (Username, Email, Phno, Address) VALUES (" 
                + "'" + name + "', '" + email + "', '" + phno + "', '" + address + "')";

	    boolean b = stmt.execute(sql);
	    if (!b) {
	        JOptionPane.showMessageDialog(null, "Inserted");
	    } else {
	        JOptionPane.showMessageDialog(null, "Error");
	    }
	}
	
	
	//To Insert Book
	public void SaveBook(String name, String author, String category) throws SQLException {
		String sql = "INSERT INTO Book (BName, Author, Category) VALUES (" 
                + "'" + name + "', '" + author + "', '" + category +  "')";
	    boolean b = stmt.execute(sql);
	    if (!b) {
	        JOptionPane.showMessageDialog(null, "Ins"
	        		+ "erted");
	    } else {
	        JOptionPane.showMessageDialog(null, "Error");
	    }
	}
	
	//To Insert category
	public void SaveCategory(String category) throws SQLException {
		String sql="Insert into category (CategoryName) values("+"'"+category+"')";
		boolean b=stmt.execute(sql);
		if(!b) {
			JOptionPane.showMessageDialog(null, "Inserted");
		}
		else {
	        JOptionPane.showMessageDialog(null, "Error");
	    }
	}
	//To Insert Lend  Book
	public void LendBook(int mid, int bid, String lendDate, String returnDate) throws SQLException {
	    String sql = "INSERT INTO lend_book (memberid, bookid, lendDate, returnDate) VALUES ('" + mid + "', '" + bid + "', '" + lendDate + "', '" + returnDate + "')";
	    boolean b = stmt.execute(sql);
	    if (!b) {
	        JOptionPane.showMessageDialog(null, "Inserted");
	    } else {
	        JOptionPane.showMessageDialog(null, "Error");
	    }
	}
	
	//To Insert Return  Book
		public void ReturnBook(int mid, String mname,String bname, String returnDate, int elap) throws SQLException {
		    String sql = "INSERT INTO return_book (mid, mname, bname, returnDate,elap) VALUES ('" + mid + "', '" + mname + "', '" + bname + "', '" + returnDate + "','"+elap+"')";
		    boolean b = stmt.execute(sql);
		    if (!b) {
		        JOptionPane.showMessageDialog(null, "Return Book Successfully!");
		    } else {
		        JOptionPane.showMessageDialog(null, "Error");
		    }
		}
}



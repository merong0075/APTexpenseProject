package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	
	public static Connection getConnection()  {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/admincostdb", "root","123456");
			System.out.println("¤·¤·¤·");
		} catch (Exception e) {
			return null;
		}
		return con;
	}

}


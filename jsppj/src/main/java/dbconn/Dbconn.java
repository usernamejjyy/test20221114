package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;


public class Dbconn {
	String url ="jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "c##study1";
	String password="1234";
	
	public Connection getConnect() {
		Connection conn = null;
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	try {
		conn = DriverManager.getConnection(url, user, password);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return conn;
	}
}

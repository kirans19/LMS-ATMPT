package database.connection;


import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {

	public static Connection getConnection() {
		Connection connection=null;
		try {
			if(connection==null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_application","root","");
				//System.out.println("Success");
			}
			
		}
		catch(Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return connection;
	}

}

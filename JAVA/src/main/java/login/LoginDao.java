package login;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.connection.DBConnection;
import login.LoginBean;

public class LoginDao {
	
	
	
	
	public String[] validate(LoginBean loginBean) {
		
		String id;
		String status ="";
		String[] res=new String[2];
		Connection con=DBConnection.getConnection();
		String sql = "select user_type,id from user_data where email = ? and password =?";
		PreparedStatement ps;
		try {
		ps = con.prepareStatement(sql);
		ps.setString(1, loginBean.getEmail());
		ps.setString(2, loginBean.getPassword());
		System.out.println(loginBean.getPassword());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			status=rs.getString("user_type");
			id=rs.getString("id");
			res[0]=id;
			res[1]=status;
			
		}
		//System.out.println(id);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(status);
		return res;
	}
	
}

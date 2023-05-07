package registation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.connection.DBConnection;



public class RegistrationDAO {
	
	
	Connection connection=DBConnection.getConnection();
	public void registerUser(Registration register) throws ClassNotFoundException{
			
			
	        String INSERT_USERS_SQL = "INSERT INTO user_data(email,password,first_name,last_name,mobile_no,address,user_type)  VALUES(?, ?, ?, ?, ?, ?, ?)";

	        int result = 0;
	        
	        try {
	        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
	        preparedStatement.setString(1,register.getEmail());
	        preparedStatement.setString(2,register.getPassword());
	        preparedStatement.setString(3,register.getFirst_name());
	        preparedStatement.setString(4,register.getLast_name());
	        preparedStatement.setString(5,register.getMobile_no());
	        preparedStatement.setString(6,register.getAddress());
	        preparedStatement.setString(7,register.getUser_type());
	           
	        preparedStatement.executeUpdate();
	            
	       
	        }
	        catch(SQLException e) {
	        printSQLException(e);
	        	
	        	
	        }
	       
	}

	
	    private void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}



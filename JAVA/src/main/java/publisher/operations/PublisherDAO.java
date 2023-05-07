package publisher.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import database.connection.DBConnection;

public class PublisherDAO {
	

	Connection connection=DBConnection.getConnection();
	
	public JsonArray myReaders(String id) throws SQLException {
		JsonArray jsonArray=new JsonArray();
		PreparedStatement ps=connection.prepareStatement("SELECT distinct book_catalogue.title,book_borrowlog.id,user_data.first_name,user_data.last_name,user_data.email FROM book_borrowlog RIGHT JOIN user_data on book_borrowlog.id=user_data.id RIGHT JOIN book_catalogue on book_borrowlog.ISBN=book_catalogue.ISBN WHERE book_catalogue.publisherId=?;");		
		ps.setString(1,id);
		ResultSet resultSet=ps.executeQuery();
		while(resultSet.next()) {
			JsonObject obj = new JsonObject();
	        obj.addProperty("title", resultSet.getString("title"));
	        obj.addProperty("id", resultSet.getString("id"));
	        obj.addProperty("first_name", resultSet.getString("first_name"));
	        obj.addProperty("last_name", resultSet.getString("last_name"));
	        obj.addProperty("email", resultSet.getString("email"));
	        jsonArray.add(obj);
	        
		}
		return jsonArray;
		
	}
	
	public JsonArray myBooks(String id) throws SQLException {
		JsonArray jsonArray=new JsonArray();
		PreparedStatement ps=connection.prepareStatement("SELECT * FROM book_catalogue WHERE publisherId=?;");
		ps.setString(1, id);
		ResultSet resultSet=ps.executeQuery();
		while(resultSet.next()) {
			JsonObject obj = new JsonObject();
	        obj.addProperty("ISBN", resultSet.getString("ISBN"));
	        obj.addProperty("title", resultSet.getString("title"));
	        obj.addProperty("image", resultSet.getString("image"));
	        obj.addProperty("price", resultSet.getInt("price"));
	        obj.addProperty("edition", resultSet.getString("edition"));
	        obj.addProperty("category", resultSet.getInt("category"));
	        obj.addProperty("authorNo", resultSet.getString("authorNo"));
	        obj.addProperty("bookInfo", resultSet.getString("bookInfo"));
	        obj.addProperty("publishedYear", resultSet.getInt("publishedYear"));
	        obj.addProperty("count", resultSet.getInt("count"));
	        jsonArray.add(obj);
			
		}
		return jsonArray;
		
	}

}

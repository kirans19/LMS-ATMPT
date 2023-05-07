package user.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import database.connection.DBConnection;

public class userDAO {

	
Connection connection=DBConnection.getConnection();

public JsonArray profile(String id)throws SQLException{
	 JsonArray jsonArray = new JsonArray();
	PreparedStatement statement=connection.prepareStatement("select * from user_data where id=?");
	statement.setString(1,id);
	ResultSet result=statement.executeQuery();
	while(result.next()) {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("id",result.getString("id"));
		obj.addProperty("email",result.getString("email"));
		obj.addProperty("first_name",result.getString("first_name"));
		obj.addProperty("last_name",result.getString("last_name"));
		obj.addProperty("mobile_no",result.getString("mobile_no"));
		obj.addProperty("address",result.getString("address"));
		
		 jsonArray.add(obj);
	}
	return jsonArray;
}

public JsonArray history(String id) throws SQLException {
	JsonArray jsonArray=new JsonArray();
	PreparedStatement statement=connection.prepareStatement("SELECT book_borrowlog.ISBN,book_catalogue.title,book_borrowlog.issue_date,book_borrowlog.return_date FROM book_borrowlog INNER JOIN book_catalogue ON book_borrowlog.ISBN=book_catalogue.ISBN where book_borrowlog.id=?  ORDER BY book_borrowlog.issue_date DESC;");
	statement.setString(1,id);
	ResultSet result=statement.executeQuery();
	int i=0;
	while(result.next() && i<10) {
		JsonObject obj = new JsonObject();
		obj.addProperty("ISBN", result.getString("ISBN"));
		obj.addProperty("title", result.getString("title"));
		obj.addProperty("issue_date", result.getString("issue_date"));
//		obj.addProperty("return_date", result.getString("return_date"));
		if(result.getString("return_date")!=null) {
			obj.addProperty("return_date", result.getString("return_date"));
		}
		else {
			obj.addProperty("return_date","Not Returned");
		}
		 jsonArray.add(obj);
		 i++;
	
		
		
	}
	return jsonArray;
	
}

}
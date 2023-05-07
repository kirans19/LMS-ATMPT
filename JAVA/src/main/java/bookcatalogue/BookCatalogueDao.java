package bookcatalogue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import database.connection.DBConnection;

public class BookCatalogueDao {
	
	
	public JsonArray selectAllBooks() {

		    JsonArray jsonArray = new JsonArray();
		
		try (Connection connection = DBConnection.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement("select * from book_catalogue");) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				JsonObject obj = new JsonObject();
		        obj.addProperty("ISBN", resultSet.getString("ISBN"));
		        obj.addProperty("title", resultSet.getString("title"));
		        obj.addProperty("image", resultSet.getString("image"));
		        obj.addProperty("price", resultSet.getInt("price"));
		        obj.addProperty("edition", resultSet.getString("edition"));
		        obj.addProperty("category", resultSet.getInt("category"));
		        obj.addProperty("publisherId", resultSet.getString("publisherId"));
		        obj.addProperty("authorNo", resultSet.getString("authorNo"));
		        obj.addProperty("bookInfo", resultSet.getString("bookInfo"));
		        obj.addProperty("publishedYear", resultSet.getInt("publishedYear"));
		        obj.addProperty("count", resultSet.getInt("count"));
		        jsonArray.add(obj);
			}
		} catch (SQLException e) {
//			printSQLException(e);
		}
		return jsonArray;
	}

	
}

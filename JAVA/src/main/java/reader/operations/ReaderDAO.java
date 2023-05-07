package reader.operations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import database.connection.DBConnection;

public class ReaderDAO {

	Connection connection=DBConnection.getConnection();
	
	public String reserveBook(String ISBN,String id) throws SQLException{
		
	
	
	
	HashMap<String,String> ISBNandPublisherMapper=new HashMap<>();
	String bookPublisherQuery="SELECT ISBN,publisherId FROM book_catalogue";
	PreparedStatement statement = connection.prepareStatement(bookPublisherQuery);
	ResultSet result=statement.executeQuery();
	while(result.next()) {
		ISBNandPublisherMapper.put(result.getString("ISBN"), result.getString("publisherId"));
	}
	
	
	String checkSameBook="SELECT COUNT(*) FROM book_borrowlog WHERE id=? AND ISBN=? AND status='0'";
	PreparedStatement statement_3 = connection.prepareStatement(checkSameBook);
	statement_3.setString(1,id);
	statement_3.setString(2, ISBN);
	ResultSet result_3=statement_3.executeQuery();
	int sameBookCount=0;
	while(result_3.next()) {
		sameBookCount=result_3.getInt("COUNT(*)");
	}
	
	String message = "Publisher cannot avail their Books!";
	if(ISBNandPublisherMapper.containsKey(ISBN)) {
	message = "Book already reserved!!";
	if(!ISBNandPublisherMapper.get(ISBN).equals(id) && sameBookCount==0) {
	
	
	String statusQuery="UPDATE book_catalogue SET count=count-1 WHERE ISBN=?";
	PreparedStatement statement_1 = connection.prepareStatement(statusQuery);
	statement_1.setString(1,ISBN);
	int flag=statement_1.executeUpdate();
	
	
	if(flag>0) {
		String logQuery="INSERT INTO book_borrowlog(id,ISBN,issue_date,due_amount,status) VALUES(?, ?, ?, ?, ?)";
	PreparedStatement statement_2=connection.prepareStatement(logQuery);
	java.util.Date date=new java.util.Date();
	java.sql.Date issueDate=new java.sql.Date(date.getTime());
	statement_2.setString(1,id);
	statement_2.setString(2,ISBN);
	statement_2.setDate(3,issueDate);
	//statement_2.setString(4,"");
	statement_2.setInt(4,0);
	statement_2.setString(5,"0");
	statement_2.executeUpdate();
	message = "Reserved Successfully";
	}
	}
	}
	return message;
}
	
	public JsonArray booksToBeReturned(String id) throws SQLException{
		
		 JsonArray jsonArray = new JsonArray();
		String statusQuery="select book_borrowlog.ISBN,book_catalogue.title,book_catalogue.ISBN,book_catalogue.bookInfo,book_catalogue.image,book_catalogue.authorNo from book_borrowlog,book_catalogue where book_borrowlog.ISBN=book_catalogue.ISBN AND id=? and status=?";
		PreparedStatement statement_3 = connection.prepareStatement(statusQuery);
		statement_3.setString(1,id);
		statement_3.setString(2, "0");
		ResultSet resultSet=statement_3.executeQuery();
		while(resultSet.next()) {
			JsonObject obj = new JsonObject();
	        obj.addProperty("ISBN", resultSet.getString("ISBN"));
	        obj.addProperty("title", resultSet.getString("title"));
	        obj.addProperty("image", resultSet.getString("image"));
	        obj.addProperty("bookInfo", resultSet.getString("bookInfo"));
	        obj.addProperty("authorNo", resultSet.getString("authorNo"));
	      
	        jsonArray.add(obj);
			
		}
		
		return jsonArray;
	}
	
public String returnBook(String ISBN,String id)throws SQLException, ParseException{
		
		String message="";
		String dueDateQuery="SELECT issue_date FROM book_borrowlog where id=? AND ISBN=?";
		java.util.Date date=new java.util.Date();
		java.sql.Date returnDate=new java.sql.Date(date.getTime());
		PreparedStatement statement_2 = connection.prepareStatement(dueDateQuery);
		statement_2.setString(1, id);
		statement_2.setString(2, ISBN);
		ResultSet result_1 = statement_2.executeQuery();
		int dueAmount = 0;
		while(result_1.next()) {
			java.sql.Date dueDate=addDays(result_1.getDate("issue_date"),60);
			if(returnDate.compareTo(result_1.getDate("issue_date"))>=0 && returnDate.compareTo(dueDate)<=0){
				dueAmount=0;
			}else {
				dueAmount=(int) (dateDiff(dueDate,returnDate)*20);
			}
		}
		
		String updateBorrowLog="UPDATE book_borrowlog SET due_amount=?,return_date=?,status='1' WHERE ISBN=? AND id=? AND status='0'";
		PreparedStatement statement_3 = connection.prepareStatement(updateBorrowLog);
		statement_3.setFloat(1,dueAmount);
		statement_3.setString(3,ISBN);
		statement_3.setDate(2,returnDate);
		statement_3.setString(4,id);
		int flag=statement_3.executeUpdate();
		
		if(flag>0) {
			String updateCatalogue="UPDATE book_catalogue SET count=count+1 WHERE ISBN=?";
		PreparedStatement statement_4 = connection.prepareStatement(updateCatalogue);
		statement_4.setString(1,ISBN);
		statement_4.executeUpdate();
		message="Returned Successfully!!";
		}
		
	return message;
	
	}


public int calculateDueAmount(String id)throws SQLException{
	
	
	 int amount=0;
	 String dueAmountQuery="SELECT SUM(due_amount)FROM book_borrowlog WHERE id=?";
	 PreparedStatement statement_1 = connection.prepareStatement(dueAmountQuery);
	 statement_1.setString(1, id);
	 ResultSet result_1 = statement_1.executeQuery();
	 while(result_1.next()) {
		 amount=result_1.getInt(1);
	 }
	
	return amount;
}

public void updateDue(String id) throws SQLException{
	
	String clearDueAmount="UPDATE book_borrowlog SET due_amount=0 WHERE id=?";
	PreparedStatement statement_2 = connection.prepareStatement(clearDueAmount);
	statement_2.setString(1,id);
	statement_2.executeUpdate();
}

	 public Date addDays(Date date, int days) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.DATE, days);
	        return new Date(c.getTimeInMillis());
	    }
	 
	 public long dateDiff(Date firstDate,Date secondDate) throws ParseException {

		        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		        return diff;
	}

	
}


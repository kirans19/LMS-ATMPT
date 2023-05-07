package bookcatalogue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import login.LoginBean;
import reader.operations.ReaderDAO;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Servlet implementation class BookCatalogueServlet
 */
@WebServlet("/BookCatalogue")
public class BookCatalogueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookCatalogueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Set CORS headers
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");

	}
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    
	    
	    
	 
	    
		BookCatalogueDao bookCatalogueDao=new BookCatalogueDao();
		JsonArray bookCatalogue=bookCatalogueDao.selectAllBooks();
		

		
		  JsonObject jsonObject = new JsonObject();
		  jsonObject.add("bookCatalogue", bookCatalogue);
		 
		  response.getWriter().write(jsonObject.toString());
	}
    
//    private static final String ALGORITHM = "AES";
//    private static final String KEY = "00112233445566778899aabbccddeeff"; // Replace with your own secret key
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Set CORS headers
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Retrieve the book catalogue data from the database
//        BookCatalogueDao bookCatalogueDao = new BookCatalogueDao();
//        JsonArray bookCatalogue = bookCatalogueDao.selectAllBooks();
//
//        try {
//            // Encrypt the book catalogue data using AES encryption
//            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(bookCatalogue.toString().getBytes());
//
//            // Convert the encrypted data to a Base64-encoded string for transmission
//            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
//
//            // Create a JSON object with the encrypted data and send it as the response
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("encryptedData", encryptedData);
//            response.getWriter().write(jsonObject.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


	
}

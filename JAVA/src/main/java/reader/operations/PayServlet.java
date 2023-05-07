package reader.operations;

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
import com.google.gson.JsonObject;

import login.LoginBean;

/**
 * Servlet implementation class PayServlet
 */
@WebServlet("/pay")
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Set CORS headers
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");

	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    
//	    InputStream inputStream = request.getInputStream();
//	  	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//	  	StringBuilder requestBody = new StringBuilder();
//	  	String line;
//	  	while ((line = reader.readLine()) != null) {
//	  	    requestBody.append(line);
//	  	}
//	  	System.out.print(requestBody);
//	  	Gson gson = new Gson();
//	  	LoginBean myObject1 = gson.fromJson(requestBody.toString(), LoginBean.class);
//	  	String id = myObject1.getId();
	    
	    HttpSession session = request.getSession(false);
		String id = (String) session.getAttribute("id");
	  	
	  	ReaderDAO operations=new ReaderDAO();
	  	
	  	try {
			operations.updateDue(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	
	  	response.setContentType("application/json");
	    JsonObject responseObject = new JsonObject();
	    responseObject.addProperty("message","Paid Successfully!!");
	    response.getWriter().write(responseObject.toString());
	  	
		
	}

}

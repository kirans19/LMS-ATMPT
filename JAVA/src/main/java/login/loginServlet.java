package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import registation.Registration;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/signin")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	  protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    // Set CORS headers
		    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		    response.setHeader("Access-Control-Allow-Credentials", "true");
		}
	  
    
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");

	  
	  	InputStream inputStream = request.getInputStream();
	  	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	  	StringBuilder requestBody = new StringBuilder();
	  	String line;
	  	while ((line = reader.readLine()) != null) {
	  	    requestBody.append(line);
	  	}
	  	System.out.print(requestBody);
	  	Gson gson = new Gson();
	  	LoginBean myObject = gson.fromJson(requestBody.toString(), LoginBean.class);
	  	String email = myObject.getEmail();
	  	String password=myObject.getPassword();
	  	
	  	LoginBean login=new LoginBean();
	  	login.setEmail(email);
	  	login.setPassword(password);
	  	
	  	LoginDao loginDao=new LoginDao();
	  	String[] res=loginDao.validate(login);
	  	
	  	if(res[1]!="") {
	  	HttpSession session = request.getSession(); 
	  	session.setAttribute("username", email);
	  	session.setAttribute("id",res[0]);
	  	session.setAttribute("user_type",res[1]);
	  	System.out.println(session.getAttribute("username"));
	  	}
	  	

	  	
	  	
	  	response.setContentType("application/json");
	    JsonObject responseObject = new JsonObject();
	    responseObject.addProperty("id", res[0]);
	    responseObject.addProperty("user_type", res[1]);
	    responseObject.addProperty("username", email);
	    response.getWriter().write(responseObject.toString());
		
	}

}

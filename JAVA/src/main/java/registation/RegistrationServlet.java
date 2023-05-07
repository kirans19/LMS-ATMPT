package registation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.JsonObject;





@WebServlet("/signup")
public class RegistrationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Set CORS headers
	    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");

	}
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	  
	  
	  response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");

	  
	  	InputStream inputStream = request.getInputStream();
	  	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	  	StringBuilder requestBody = new StringBuilder();
	  	String line;
	  	while ((line = reader.readLine()) != null) {
	  	    requestBody.append(line);
	  	}
	  	System.out.print(requestBody);
	  	Gson gson = new Gson();
	  	Registration myObject = gson.fromJson(requestBody.toString(), Registration.class);
	  	String email = myObject.getEmail();
	  	String password=myObject.getPassword();
		String first_name=myObject.getFirst_name();
		String last_name=myObject.getLast_name();
		String mobile_no=myObject.getMobile_no();
		String address=myObject.getAddress();
		String user_type=myObject.getUser_type();
		
		
	  	
	  	
	  	System.out.print("Hello"); 
	    
		
		
		 
		
		
	    if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
	      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	      return;
	    }
	    
	    Registration register=new Registration();
	    
	    register.setEmail(email);
		register.setPassword(password);
		register.setFirst_name(first_name);
		register.setLast_name(last_name);
		register.setMobile_no(mobile_no);
		register.setAddress(address);
		register.setUser_type(user_type); 
		System.out.print(register.getAddress());
		
		RegistrationDAO user=new RegistrationDAO();
	    try {
			user.registerUser(register);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if (register == null) {
	      response.setStatus(HttpServletResponse.SC_CONFLICT);
	      return;
	    }
	    
	    response.setContentType("application/json");
	    JsonObject responseObject = new JsonObject();
	    responseObject.addProperty("success", true);
	    response.getWriter().write(responseObject.toString());

	  }
	}
	

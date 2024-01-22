package myDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MyController {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response);
	
}

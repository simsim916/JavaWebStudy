package com.item.tomatofarm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/list")
public class Dispathcher extends HttpServlet{
	private static final long serialVersionUID = 1L;
    String uri;
    ItemService service = new ItemService();
	
    public Dispathcher() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	uri = "view/itemList.jsp";
    	List<ItemDTO> list = service.itemList(request.getParameter("name"));
    	request.setAttribute("list", list);
    	request.getRequestDispatcher(uri).forward(request, response);
    }
}

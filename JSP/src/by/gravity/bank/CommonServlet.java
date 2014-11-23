package by.gravity.bank;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommonServlet extends HttpServlet {

	public void handleError(HttpServletRequest request, HttpServletResponse response,String message) throws ServletException, IOException{
		request.setAttribute("message", message);
		request.getRequestDispatcher("/Error.jsp").forward(request, response);
	}
}

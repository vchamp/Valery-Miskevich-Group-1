package by.gravity.bank;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gravity.bank.constants.Api;
import by.gravity.bank.exceptions.UserExistsException;
import by.gravity.bank.utils.FileUtils;

@WebServlet("/CreateNewUserServlet")
public class CreateNewUserServlet extends LoginServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateNewUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		try {
			FileUtils.createUser(userName);
			FileUtils.addMoney(userName, 100000, "BYR");
			FileUtils.addMoney(userName, 100, "USD");
			FileUtils.addMoney(userName, 100, "EUR");
			super.doPost(request, response);
		} catch (UserExistsException e) {
			handleError(request, response, e.getMessage());
		}

	}

}


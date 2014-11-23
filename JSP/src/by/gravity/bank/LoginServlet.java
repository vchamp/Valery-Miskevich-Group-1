package by.gravity.bank;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gravity.bank.exceptions.UserExistsException;
import by.gravity.bank.utils.FileUtils;

@WebServlet("/LoginServlet")
public class LoginServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("userName");
		boolean result = FileUtils.login(userName);
		if (result) {
			try {
				request.setAttribute("userName", userName);
				request.getSession().setAttribute("userName", userName);
				request.setAttribute("balance", FileUtils.getBalance(userName));
				request.getRequestDispatcher("/UserPage.jsp").forward(request,
						response);
			} catch (UserExistsException e) {
				handleError(request, response, e.getMessage());
			} catch (IOException e) {
				handleError(request, response,
						"Не удалось получить баланс пользователя");
			}
		} else {
			handleError(request, response, "Пользователь ненайден");
		}
	}

}

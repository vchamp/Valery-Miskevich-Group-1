package by.gravity.bank;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gravity.bank.exceptions.NotEnoughMoneyException;
import by.gravity.bank.exceptions.UserExistsException;
import by.gravity.bank.utils.FileUtils;

@WebServlet("/ExchangeServlet")
public class ExchangeServlet extends CommonServlet {
	private static final long serialVersionUID = 1L;
       
    public ExchangeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String amount = request.getParameter("amount");
		String userName = (String) request.getSession().getAttribute("userName");
		
		try {
			FileUtils.exchange(userName, from, to, Double.parseDouble(amount), getRate(from, to));
			request.setAttribute("balance", FileUtils.getBalance(userName));
			request.getRequestDispatcher("/UserPage.jsp").forward(request,
					response);
		} catch (NumberFormatException | UserExistsException
				| NotEnoughMoneyException e) {
			handleError(request, response, e.getMessage());
		}
	
	}
	
	private Double getRate(String from, String to){
		Double rate = null;
        if (from.equals("BYR")) {
            if (to.equals("USD")) {
                rate = 1 / 10700d;
            }
            if (to.equals("EUR")) {
                rate = 1 / 13500d;
            }
        } else if (from.equals("USD")) {
            if (to.equals("BYR")) {
                rate = 10700d;
            } else if (to.equals("EUR")) {
                rate = 10700d / 13500d;
            }
        } else if (from.equals("EUR")) {
            if (to.equals("BYR")) {
                rate = 13500d;
            } else if (to.equals("USD")) {
                rate = 13500d / 10700d;
            }
        }
        
        return rate;

	}

}

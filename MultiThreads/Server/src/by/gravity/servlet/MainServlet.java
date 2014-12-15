package by.gravity.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gravity.constants.Api;
import by.gravity.utils.HibernateUtils;
import by.gravity.utils.StringUtils;

/**
 * Created by ilya.shknaj on 24.10.14.
 */
public class MainServlet extends CommonServlet implements Api {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doGet(req, resp);
        String userName = req.getParameter(USER_NAME);
        try {
            Thread thread =  createUserThread(userName, req, resp);
            thread.start();
            synchronized (thread) {
                thread.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Thread createUserThread(final String userName, final HttpServletRequest req, final HttpServletResponse resp) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                PrintWriter writer = null;
                try {
                    writer = resp.getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String method = req.getParameter(METHOD);
                if (StringUtils.isEmpty(method)) {
                    writer.append("Операция не найдена");
                    return;
                }

                if (StringUtils.isEmpty(userName)) {
                    writer.append("Имя пользователя не может быть пустым");
                    return;
                }

                switch (method) {
                    case METHOD_CREATE_USER:
                        createUser(writer, userName);
                        break;
                    case METHOD_LOGIN_USER:
                        loginUser(writer, userName);
                        break;
                    case METHOD_ADD_MONEY:
                        addMoney(writer, userName, req.getParameter(CURRENCY), req.getParameter(SUM));
                        break;
                    case METHOD_EXCHANGE:
                        exchange(writer, userName, req.getParameter(FROM), req.getParameter(TO), req.getParameter(SUM));
                        break;

                    default:
                        writer.append("Операция не найдена");
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.setName(userName);
        return thread;

    }

    private void createUser(PrintWriter writer, String userName) {
        try {
            boolean result = HibernateUtils.getInstance().createUser(userName);
            if (result) {
                writer.append("OK");
            } else {
                writer.append("Не удалось создать пользователя. Попробуйте повторить попытку позже");
            }
        } catch (Exception e) {
            writer.append(e.getMessage());
        }
    }

    private void loginUser(PrintWriter writer, String userName) {

        String userBalance = HibernateUtils.getInstance().getUserBalance(userName);
        if (userBalance != null) {
            writer.append("OK\n");
            writer.append(userBalance);
        } else {
            writer.append("Пользователь ненайден");
        }

    }

    private void addMoney(PrintWriter writer, String userName, String currency, String summ) {

        if (!isValidCurrency(writer, currency) || !isValidSumm(writer, summ)) {
            return;
        }

        try {
            writer.append(HibernateUtils.getInstance().addMoney(userName, Double.parseDouble(summ),
                    currency));
        } catch (Exception e) {
            writer.append(e.getMessage());
        }

    }

    private void exchange(PrintWriter writer, String userName, String from, String to, String sum) {

        if (!isValidCurrency(writer, from) || !isValidCurrency(writer, to) || !isValidSumm(writer, sum)) {
            return;
        }
        Double rate = null;
        if (from.equals("BYR")) {
            if (to.equals("USD")) {
                rate = 1 / 10750d;
            }
            if (to.equals("EUR")) {
                rate = 1 / 13400d;
            }
        } else if (from.equals("USD")) {
            if (to.equals("BYR")) {
                rate = 10750d;
            } else if (to.equals("EUR")) {
                rate = 10750d / 13400d;
            }
        } else if (from.equals("EUR")) {
            if (to.equals("BYR")) {
                rate = 13400d;
            } else if (to.equals("USD")) {
                rate = 13400d / 10750d;
            }
        }

        try {
            writer.append("Курс обмена " + rate + " \n");
            writer.append(HibernateUtils.getInstance()
                    .exchange(userName, from, to, Double.parseDouble(sum), rate));
        } catch (Exception e) {
            writer.append(e.getMessage());
        }
    }

    private boolean isValidCurrency(PrintWriter writer, String currency) {

        if (StringUtils.isEmpty(currency)) {
            writer.append("Параметр currency не может отсутсвовать");
            return false;
        } else if (currency.equals("BYR") || currency.equals("USD") || currency.equals("EUR")) {
            return true;
        }

        writer.append("Валюта должна быть BYR,USD или EUR");
        return false;

    }

    private boolean isValidSumm(PrintWriter writer, String summ) {

        if (StringUtils.isEmpty(summ)) {
            writer.append("Параметр summ не может отсутсвовать");
            return false;
        } else if (!StringUtils.isNumeric(summ)) {
            writer.append("Параметр summ не является числом");
            return false;
        }

        return true;
    }

}

package by.gravity.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import by.gravity.client.constans.Api;
import by.gravity.client.exception.NotValidCurrencyException;
import by.gravity.client.utils.StringUtils;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class ClientApp {

    private Scanner mScanner;

    private String mUserName;

    public ClientApp() {

        mScanner = new Scanner(System.in);
    }

    public void showMenu() throws IOException {

        System.out.println("\nВыберите пункт меню");
        System.out.println("1)Создать пользователя");
        System.out.println("2)Залогиниться");
        System.out.println("3)Внести деньги на счет");
        System.out.println("4)Обменять");
        System.out.println("10)Выйти");


        String operation = mScanner.next();
        switch (operation) {
            case "1":
                createUser();
                showMenu();
                break;
            case "2":
                login();
                showMenu();
                break;
            case "3":
                try {
                    if (isLogined()) {
                        addMoney();
                        showMenu();
                    } else {
                        showMenu();
                    }
                } catch (NotValidCurrencyException e) {
                    System.out.println(e.getMessage());
                    showMenu();
                }
                break;
            case "4":
                try {
                    if (isLogined()) {
                        exchange();
                        showMenu();
                    } else {
                        showMenu();
                    }

                } catch (NotValidCurrencyException e) {
                    System.out.println(e.getMessage());
                    showMenu();
                }
                break;
            case "10":
                System.exit(0);
            default:
                System.out.println("Пункт меню выбран неверно. Попробуйте еще раз");
                showMenu();
        }

    }

    public String createUser() throws IOException {

        System.out.println("Введите имя пользователя");
        String userName = mScanner.next();
        String url = Api.createUser(userName);
        String response = sendRequest(url);
        System.out.println(response);
        return response;

    }

    public String login() throws IOException {

        System.out.println("Введите имя пользователя");
        String userName = mScanner.next();
        String url = Api.login(userName);
        String response = sendRequest(url);
        if (response.startsWith("OK")) {
            mUserName = userName;
        }
        System.out.println(response);
        return response;

    }

    public String addMoney() throws IOException, NotValidCurrencyException {

        String currency = readCurrency();
        String sum = readSum();
        String url = Api.addMoney(mUserName, currency, sum);
        String response = sendRequest(url);
        System.out.println(response);
        return response;
    }


    public void exchange() throws NotValidCurrencyException, IOException {


        System.out.println("Из какой валюты будем конвертировать");
        String currencyFrom = readCurrency();
        System.out.println("В какую валюту будем конвертировать");
        String currencyTo = readCurrency();
        String sum = readSum();
        String url = Api.exchange(mUserName, currencyFrom, currencyTo, sum);
        String response = sendRequest(url);
        System.out.println(response);

    }

    private boolean isLogined() {

        boolean isLogined = !StringUtils.isEmpty(mUserName);
        if (!isLogined) {
            System.out.println("Сначала необходимо залогиниться");
        }

        return isLogined;
    }


    private String sendRequest(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public String readCurrency() throws NotValidCurrencyException {

        System.out.println("Выберите валюту");
        System.out.println("1)BYR");
        System.out.println("2)USD");
        System.out.println("3)EUR");

        String currency = mScanner.next();
        if (currency.equals("1")) {
            currency = "BYR";
        } else if (currency.equals("2")) {
            currency = "USD";
        } else if (currency.equals("3")) {
            currency = "EUR";
        } else {
            throw new NotValidCurrencyException();
        }
        return currency;
    }

    private String readSum() throws NotValidCurrencyException {

        System.out.println("Введите сумму");
        String sum = mScanner.next();
        if (StringUtils.isNumeric(sum)) {
            return sum;
        } else {
            throw new NotValidCurrencyException();
        }
    }
}

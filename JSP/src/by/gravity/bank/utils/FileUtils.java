package by.gravity.bank.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import by.gravity.bank.exceptions.NotEnoughMoneyException;
import by.gravity.bank.exceptions.UserExistsException;

public class FileUtils {

	private static final String DEFAULT_PATH = System.getProperty("user.dir") + "/../Users/";

    public static boolean createUser(String userName) throws IOException, UserExistsException {

        return newFile(DEFAULT_PATH, userName);
    }

    public static boolean login(String userName) {

        return new File(DEFAULT_PATH, userName + ".txt").exists();
    }

    public static String addMoney(String userName, double sum, String currency) throws UserExistsException, IOException {

        return updateBalance(userName, sum, currency, Operation.ADD);
    }

    public static String subtractMoney(String userName, double sum, String currency) throws UserExistsException, IOException {

        return updateBalance(userName, sum, currency, Operation.SUBTRACT);
    }

    private enum Operation {
        ADD, SUBTRACT
    }

    public static String updateBalance(String userName, double sum, String currency, Operation operation) throws UserExistsException, IOException {

        File file = readUserFile(userName);

        Double balance = null;
        int currencyLineNumber = 0;
        int readLineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line; (line = br.readLine()) != null; ) {
                readLineNumber++;
                if (line.startsWith(currency)) {
                    currencyLineNumber = readLineNumber;
                    int sumIndex = line.indexOf(" ");
                    if (sumIndex != -1) {
                        balance = Double.parseDouble(line.substring(sumIndex + 1));
                        if (operation == Operation.ADD) {
                            balance += sum;
                        } else {
                            balance -= sum;
                        }
                    }

                    break;
                }
            }
            br.close();

        }

        if (balance == null) {
            balance = sum;
        }

        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        String resultBalance = currency + " " + balance;
        if (lines.isEmpty() && currencyLineNumber == 0) {
            lines.add(resultBalance);
        } else {
            if (currencyLineNumber == 0) {
                lines.add(resultBalance);
            } else {
                lines.set(currencyLineNumber - 1, resultBalance);
            }
        }
        Files.write(file.toPath(), lines, Charset.defaultCharset());

        return "Ваш баланс" + lines.toString();

    }

    public static String exchange(String userName, String from, String to, Double sum, Double rate) throws UserExistsException, IOException, NotEnoughMoneyException {
    	rate = round(rate, 2);
        File file = readUserFile(userName);
        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        String fromBalance = null;
        for (String line : lines) {
            int index = 0;
            if (line.startsWith(from)) {
                index = line.indexOf(" ");
                if (index != 0) {
                    fromBalance = line.substring(index);
                }
            }
        }

        if (StringUtils.isEmpty(fromBalance) || StringUtils.isNumeric(fromBalance)) {
            fromBalance = "0";

        }

        Double fromValue = Double.parseDouble(fromBalance);
        if (fromValue < sum) {
            throw new NotEnoughMoneyException(lines.toString());
        }
        subtractMoney(userName, sum, from);
        Double toValue = sum * rate;
        addMoney(userName, toValue, to);

        return "\nВаш баланс" + lines.toString();

    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String getBalance(String userName) throws UserExistsException, IOException {

        File file = readUserFile(userName);
        List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        return "\nВаш баланс" + lines.toString();
    }

    private static boolean newFile(String path, String fileName) throws IOException, UserExistsException {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(path, fileName + ".txt");
        if (file.exists()) {
            throw new UserExistsException();
        }
        return file.createNewFile();
    }

    private static File readUserFile(String userName) throws UserExistsException {

        File file = new File(DEFAULT_PATH, userName + ".txt");
        if (!file.exists()) {
            throw new UserExistsException();
        }
        return file;
    }
}

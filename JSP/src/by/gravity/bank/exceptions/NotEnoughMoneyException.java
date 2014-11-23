package by.gravity.bank.exceptions;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String balance) {

        super("Недостаточно денег. Пополните баланс и попробуйте повторить операцию " + balance);
    }
}


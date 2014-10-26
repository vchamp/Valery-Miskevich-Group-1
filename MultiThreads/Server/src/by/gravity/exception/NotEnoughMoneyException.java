package by.gravity.exception;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String balance) {

        super("Недостаточно денег. Пополните баланс и попробуйте повторить операцию " + balance);
    }
}

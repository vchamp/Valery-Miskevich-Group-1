package by.gravity.client.exception;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class NotValidCurrencyException extends Exception{

    public NotValidCurrencyException() {

        super("Выбрана невалидная валюта. Попробуйте еще раз");
    }
}

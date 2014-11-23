package by.gravity.bank.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException() {

        super("Пользователь с таким именем существует");
    }
}
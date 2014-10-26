package by.gravity.exception;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class UserExistsException extends Exception {

    public UserExistsException() {

        super("Пользователь с таким именем существует");
    }
}

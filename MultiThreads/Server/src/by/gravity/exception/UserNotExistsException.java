package by.gravity.exception;

/**
 * Created by ilya.shknaj on 25.10.14.
 */
public class UserNotExistsException extends Exception {

    public UserNotExistsException() {

        super("Пользователь с таким именем не существует");
    }
}

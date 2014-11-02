package by.gravity.client.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.IOException;

import by.gravity.client.ClientApp;
import by.gravity.client.exception.NotValidCurrencyException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ilya.shknaj on 02.11.14.
 */
public class ClientAppTest {

    @Rule
    public TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    private ClientApp clientApp;

    private String userName;

    @Before
    public void init() throws IOException {

        clientApp = new ClientApp();
    }

    @Test
    public void testUser() throws IOException, NotValidCurrencyException {
        //create user
        userName = "testUser" + System.currentTimeMillis();
        systemInMock.provideText(userName + "\n");
        clientApp.createUser();

        //login
        systemInMock.provideText(userName + "\n");
        assertTrue(clientApp.login().startsWith("OK"));

        //add money
        systemInMock.provideText("1\n100");
        assertEquals("Ваш баланс[BYR 100.0]", clientApp.addMoney());
    }

    @Test(expected = NotValidCurrencyException.class)
    public void testReadCurrency() throws NotValidCurrencyException {

        systemInMock.provideText("4\n");
        clientApp.readCurrency();
    }
}

package by.gravity.cinema;

import by.gravity.cinema.ticketWindow.TicketWindow;

/**
 * Created by ilya.shknaj on 03.03.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        TicketWindow ticketWindow = new OctoberTicketWindow();
        Client client = new Client();
		client.buyTicket(1, System.currentTimeMillis());
    }
}

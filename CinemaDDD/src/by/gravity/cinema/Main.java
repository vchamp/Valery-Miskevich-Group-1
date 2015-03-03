package by.gravity.cinema;

import java.util.Date;

import by.gravity.cinema.bo.Film;
import by.gravity.cinema.ticketWindow.TicketWindow;

/**
 * Created by ilya.shknaj on 03.03.15.
 */
public class Main {

    public static void main(String[] args) {

        int filmId = Integer.parseInt(args[0]);
        Date filmTime = new Date(Long.parseLong(args[1]));
        Film film = new Film(filmId, filmTime);

        TicketWindow ticketWindow = new OctoberTicketWindow();
        ticketWindow.sellTicket(film);
    }
}

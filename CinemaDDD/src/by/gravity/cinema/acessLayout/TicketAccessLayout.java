package by.gravity.cinema.acessLayout;

import by.gravity.cinema.bo.Film;

public interface TicketAccessLayout {

	public int getFreeCountPlaces(Film film);

	public boolean filmExists(Film film);

	public boolean doTransaction(Film film) throws Exception;

}

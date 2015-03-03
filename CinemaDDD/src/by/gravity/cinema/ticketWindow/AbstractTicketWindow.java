package by.gravity.cinema.ticketWindow;

import by.gravity.cinema.acessLayout.TicketAccessLayout;
import by.gravity.cinema.bo.Film;

public abstract class AbstractTicketWindow implements TicketWindow {

	private TicketAccessLayout accessLayout;

	public AbstractTicketWindow(TicketAccessLayout accessLayout) {
		this.accessLayout = accessLayout;
	}

	@Override
	public boolean sellTicket(Film film) {
		if (accessLayout.filmExists(film)) {
			if (accessLayout.getFreeCountPlaces(film) > 0) {
				try {
					return accessLayout.doTransaction(film);
				} catch (Exception e) {
					e.printStackTrace();
					System.out
							.println("Transaction error. Please try again later");
				}
			} else {
				System.out.println("Don't have free places");
				return false;
			}
		}
		System.out.println("Film not exists");
		return false;
	}

}

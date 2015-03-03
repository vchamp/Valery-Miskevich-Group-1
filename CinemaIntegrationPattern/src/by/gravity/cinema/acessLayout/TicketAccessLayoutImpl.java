package by.gravity.cinema.acessLayout;

import by.gravity.cinema.bo.Film;

public final class TicketAccessLayoutImpl implements TicketAccessLayout {

	private static TicketAccessLayoutImpl instance;

	public static TicketAccessLayoutImpl getInstance() {
		if (instance == null) {
			instance = new TicketAccessLayoutImpl();
		}
		return instance;
	}
	
	TicketAccessLayoutImpl() {
		
	}

	@Override
	public int getFreeCountPlaces(Film film) {
		//TODO
		return 1;
	}

	@Override
	public boolean filmExists(Film film) {
		//TODO
		return true;
	}

	@Override
	public boolean doTransaction(Film film) throws Exception {
		//TODO
		return true;
	}

}

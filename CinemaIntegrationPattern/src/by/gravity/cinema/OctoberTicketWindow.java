package by.gravity.cinema;

import by.gravity.cinema.acessLayout.TicketAccessLayoutImpl;
import by.gravity.cinema.ticketWindow.AbstractTicketWindow;

public class OctoberTicketWindow extends AbstractTicketWindow {

	public static OctoberTicketWindow instance;

	public static OctoberTicketWindow getInstance() {
		if (instance == null) {
			instance = new OctoberTicketWindow();
		}
		return instance;
	}
	
	OctoberTicketWindow() {
		super(TicketAccessLayoutImpl.getInstance());
	}
	

}

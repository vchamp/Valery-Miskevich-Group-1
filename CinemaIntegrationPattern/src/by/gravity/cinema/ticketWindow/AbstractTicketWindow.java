package by.gravity.cinema.ticketWindow;

import by.gravity.cinema.acessLayout.TicketAccessLayout;
import by.gravity.cinema.adapter.Message;
import by.gravity.cinema.adapter.MessageImpl;
import by.gravity.cinema.bo.Film;
import by.gravity.cinema.channel.TicketChannelImpl;
import by.gravity.cinema.channel.TicketChannelImpl.OnMessageListener;

public abstract class AbstractTicketWindow implements TicketWindow,
		OnMessageListener {

	private TicketAccessLayout accessLayout;
	
	private Message<Film> messageAdapter;

	public AbstractTicketWindow(TicketAccessLayout accessLayout) {
		this.accessLayout = accessLayout;
		this.messageAdapter = new MessageImpl();
		TicketChannelImpl.getInstance().setSendMessageListener(this);
	}

	@Override
	public boolean sellTicket(Film film) {
		if (accessLayout.filmExists(film)) {
			if (accessLayout.getFreeCountPlaces(film) > 0) {
				try {
					System.out.println("sell ticket");
					accessLayout.doTransaction(film);
					System.out.println("ticket to film" + film.toString() + " successs sold");
					return true;
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

	@Override
	public void onMessage(String message) {
		if (message.startsWith("buy")) {
			String[] messageWords = message.split(" ");
			if (messageWords.length != 3) {
				System.out.println("Unknown format");
			}else {
				Film film =  messageAdapter.transofrm(message);
				sellTicket(film);
			}
		}
	}

}

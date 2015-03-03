package by.gravity.cinema;

import by.gravity.cinema.channel.TicketChannel;
import by.gravity.cinema.channel.TicketChannelImpl;

public class Client {

	public void buyTicket(int filmId, long time) throws Exception {
		TicketChannel channel = TicketChannelImpl.getInstance();
		channel.connectToChannel();
		channel.sendMessage("buy " + filmId + " " + time);
		channel.disconnect();
	}
}

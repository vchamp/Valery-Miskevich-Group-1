package by.gravity.cinema.channel;

import java.io.IOException;

public interface TicketChannel {

	public void connectToChannel() throws IOException;
	
	public void sendMessage(String message) throws Exception;
	
	public void disconnect() throws IOException;
}

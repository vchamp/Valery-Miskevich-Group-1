package by.gravity.cinema.channel;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketChannelImpl implements TicketChannel {

	private static TicketChannelImpl intance;

	private OnMessageListener sendMessageListener;

	private Queue<String> messagesQueue;

	public interface OnMessageListener {
		public void onMessage(String message);
	}

	public static TicketChannelImpl getInstance() {
		if (intance == null) {
			intance = new TicketChannelImpl();
		}
		return intance;
	}

	public TicketChannelImpl() {
		messagesQueue = new LinkedBlockingQueue<>();
	}

	@Override
	public void connectToChannel() throws IOException {
		// TODO implement logic to server connect
	}

	@Override
	public void sendMessage(String message) throws Exception {
		// TODO implement logic for send messages
		messagesQueue.add(message);
		if (sendMessageListener != null) {
			sendMessageListener.onMessage(message);
		}
	}

	@Override
	public void disconnect() throws IOException {
		// TODO implement logic to server connect
	}

	public void setSendMessageListener(OnMessageListener messageListener) {
		this.sendMessageListener = messageListener;
	}

}

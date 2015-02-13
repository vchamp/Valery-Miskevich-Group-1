package by.gravity.blockingQueue;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

	private BlockingQueue<String> mQueue;

	public Consumer(BlockingQueue<String> queue) {
		mQueue = queue;
	}

	@Override
	public void run() {
		while (true) {
			if (mQueue.size() > 0) {
				String product = mQueue.poll();
				System.out.println("Consumer - success poll " + product);
			} else {
				System.out.println("Consumer - queue is empty");
			}
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Consumer - something went wrong");
			}
		}
	}

}

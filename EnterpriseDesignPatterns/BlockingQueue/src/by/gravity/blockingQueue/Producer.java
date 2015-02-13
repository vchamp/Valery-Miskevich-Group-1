package by.gravity.blockingQueue;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

	private BlockingQueue<String> mQueue;
	
	public Producer(BlockingQueue<String> queue){
		this.mQueue = queue;
	}
	
	@Override
	public void run() {
		int productCount = 1;
		while(true){
			String productName = "product " + productCount;
			System.out.println("Producer - try put " + productName);
			try {
				mQueue.put(productName);
				System.out.println("Producer- success put " + productName
						+ " queue size=" + mQueue.size());
				Thread.sleep(1000);
				productCount++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Producer - something went wrong when producer try to put product");
			}
		}
	}


}

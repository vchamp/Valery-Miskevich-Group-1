package by.gravity.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

	public static void main(String[] args) {
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
		
		Producer producer = new Producer(blockingQueue);
		producer.start();
		
		Consumer consumer = new Consumer(blockingQueue);
		consumer.start();
		
	}

}

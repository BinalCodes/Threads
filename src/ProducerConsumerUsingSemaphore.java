import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ProducerConsumerUsingSemaphore  {
	public LinkedList<Integer> sharedList ;
	Semaphore semaphore = new Semaphore(1);
	public ProducerConsumerUsingSemaphore() {
		sharedList = new LinkedList<Integer>();
	}
	public void produce () throws InterruptedException {
		while (true) {
			// starting the producer but we are using semaphore instead of 
			//synchronized for shared access
			System.out.println("Producer thread is waiting to aquire lock");
			semaphore.acquire();
			// acquired lock now we can easily access shared resource
			for(int i = 0 ; i< 10 ; i++) {
				sharedList.add(i);
				System.out.println("Producing "+i);
			}
			// now we can release this lock but lets try to sleep for a bit
			Thread.sleep(1000);
			semaphore.release();
		}
	}
	public void consume() throws InterruptedException {
		while(true) {
			System.out.println("Starting consumer and trying to aquire lock");
			semaphore.acquire();
			for(int i = 0 ; i< 10 ; i++) {
				System.out.println("Removing "+i);
				sharedList.remove();
			}
			Thread.sleep(1000);
			semaphore.release();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ProducerConsumerUsingSemaphore pcObject = new ProducerConsumerUsingSemaphore();
		Runnable producer = () -> {
			try {
			pcObject.produce();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		};
		Thread pThread = new Thread(producer);
		Runnable consumerT = () -> {
			try {
				pcObject.consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		Thread cThread = new Thread(consumerT);
		pThread.start();
		cThread.start();
		
		pThread.join(1000);
		cThread.join();
	}

}

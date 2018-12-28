public class BoundingSemaphore {
	public int bound;
	public int signal;
	public static void main(String[] args) {
		
	}
	BoundingSemaphore ( int bound){
		this.bound = bound;
	}
	public synchronized void acquire() throws InterruptedException {
		while(signal == bound) {
			wait(); //wait for the lock to be released.
		}
		signal ++;
		notifyAll();
	}
	public synchronized void release() throws InterruptedException {
		while (signal == 0) { //if there are no items in the list{
			wait();
		}
		signal --;
		notify();
	}

}

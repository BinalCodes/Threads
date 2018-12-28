import java.util.concurrent.Semaphore;

public class PrintInSequence {
	Semaphore oddSem = new Semaphore(0);
	Semaphore evenSem = new Semaphore(0);
	Semaphore zeroSem = new Semaphore(1);
	public static void main(String[] args) {
		PrintInSequence p = new PrintInSequence();
		Thread t1Thread = new Thread(new Odd(p));
		Thread t2Thread = new Thread(new Even(p));
		Thread t3Thread = new Thread(new Zero(p));
		t1Thread.start();
		t2Thread.start();
		t3Thread.start();

	}
	public void printOdd(int num){
		try {
			oddSem.acquire();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(num);
		evenSem.release();
	}
	public void printEven(int num){
		try {
			evenSem.acquire();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(num);
		zeroSem.release();
	}
	public void printZero(int num){
		try {
			zeroSem.acquire();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(num);
		oddSem.release();
	}

}
class Odd implements Runnable{
	private PrintInSequence p;
	Odd(PrintInSequence p){
		this.p = p;
	}
	public void run() {
		for (int i = 1; i< 100 ; i++) {
			if(i%2 ==1)
				p.printOdd(i);
		}
	}
	
}
class Even implements Runnable{
	private PrintInSequence p;
	Even(PrintInSequence p){
		this.p = p;
	}
	public void run() {
		for (int i = 1; i< 100 ; i++) {
			if(i%2 ==0)
				p.printEven(i);
		}
	}
	
}
class Zero implements Runnable{
	private PrintInSequence p;
	Zero(PrintInSequence p){
		this.p = p;
	}
	public void run() {
			while(true) {
			p.printZero(0);
			}
		}
	
}
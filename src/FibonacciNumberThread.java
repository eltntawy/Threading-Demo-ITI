import java.util.Date;

import javax.swing.JOptionPane;

public class FibonacciNumberThread extends Thread {

	private double index;
	private double ret;

	private Thread calcThread ;
	
	public FibonacciNumberThread(String name, double index,Thread calcThread) {
		super(name);
		this.index = index;
		this.calcThread = calcThread;
	}

	public double calcFibonacci(double i) {

		if (i < 1) {
			return 0;
		}
		if (i == 1) {
			return 1;
		} else if (i == 2) {
			return 1;
		}

		return calcFibonacci(i - 1) + calcFibonacci(i - 2);
	}

	@Override
	public void run() {
		ret = calcFibonacci(index);
		
		synchronized (calcThread) {
			
			calcThread.notify();
		}
	}
	
	public static double doJob (String strRet) {
		
		double d = Double.parseDouble(strRet);
		
		if (d < 1) {
			return 0;
		}
		if (d == 1) {
			return 1;
		} else if (d == 2) {
			return 1;
		}
		
		double index1 = d - 1;
		double index2 = d - 2;

		FibonacciNumberThread fn1 = new FibonacciNumberThread("FibonacciNumberThread1",index1,Thread.currentThread());
		FibonacciNumberThread fn2 = new FibonacciNumberThread("FibonacciNumberThread2",index2,Thread.currentThread());

		
		
		fn1.start();	
		fn2.start();
			
		synchronized (Thread.currentThread()) {
			try {
				Thread.currentThread().wait();
				Thread.currentThread().wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return fn1.getRet() + fn2.getRet();
	}


	public double getRet() {
		return ret;
	}



	public static void main(String[] args) {

		String strRet = JOptionPane.showInputDialog(null,"Please enter index of Fibonacci number ");
		
		
		long begin = new Date().getTime();
		double ret = doJob(strRet);
		long end = new Date().getTime();
		
		System.out.println("running time is : "+ (end -begin) / 1000 + "s");
		
		JOptionPane.showMessageDialog(null, "The Fibonacci number for index "
				+ strRet + " is " + ret);

	}
}

import java.util.Date;

import javax.swing.JOptionPane;


public class FibonacciNumber {

	
	public static double calcFibonacci(double i) {

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
	
	public static void main(String[] args) {
		String strRet = JOptionPane.showInputDialog(null,
				"Please enter index of Fibonacci number ");

		long begin = new Date().getTime();
		double ret = calcFibonacci(Double.parseDouble(strRet));
		long end = new Date().getTime();
		
		System.out.println("running time is : "+ (end -begin) / 1000 + "s");
		JOptionPane.showMessageDialog(null, "The Fibonacci number for index "+ strRet + " is " + ret);
	}
}

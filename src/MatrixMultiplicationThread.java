import java.util.ArrayList;
import java.util.Date;

public class MatrixMultiplicationThread {

	public static void main(String args[]) {

		Matrix m1 = new MatrixThread(400, 400);
		m1.fillMatrix();

		Matrix m2 = new MatrixThread(400, 400);
		m2.fillMatrix();

		long begin = new Date().getTime();
		System.out.println(m1.multiply(m2));
		long end = new Date().getTime();

		System.out.println("running time is : " + (end - begin) + " ms");

		System.out.println("with "+MultiplyThread.getThreadsCount()+" Thread");
	}
}

class MultiplyThread extends Thread {

	private int arr1[];
	private Matrix m2;
	private Thread calcThread;
	private Matrix retMatrix;
	private int i, j;
	private static int threadsCount = 0; 
	
	
	MultiplyThread(String name, int arr1[], Matrix m2, int i, int j,
			Matrix retMatrix, Thread calcThread) {
		super(name);

		this.arr1 = arr1;
		this.m2 = m2;
		this.retMatrix = retMatrix;
		this.i = i;
		this.j = j;

		this.calcThread = calcThread;
	}

	@Override
	public void run() {

		for (int j = 0; j < arr1.length; j++) {
			int temp = 0;

			for (int k = 0; k < arr1.length; k++) {
				temp += arr1[k] * m2.data[k][j];
			}
			retMatrix.data[i][j] = temp;
		}

		/*try {
			sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (i == m2.h-1) {
			synchronized (calcThread) {
				calcThread.notifyAll();
			}
		}

	}
	
	public static int getThreadsCount() {
		return threadsCount;
	}
}

class MatrixThread extends Matrix {

	MatrixThread(int h, int w) {
		super(h, w);
	}

	public Matrix multiply(Matrix matrix) {

		if (this.w == matrix.h && this.h == matrix.w) {

			Matrix ret = new Matrix(h, h);
			ArrayList<MultiplyThread> list = new ArrayList<MultiplyThread>();

			for (int i = 0; i < h; i++) {

				MultiplyThread t = new MultiplyThread("Row # " + i,
						this.getRow(i), matrix, i, i, ret,
						Thread.currentThread());
				list.add(t);
				t.start();

			}

			synchronized (Thread.currentThread()) {
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return ret;

		} else {
			System.err.println("fail to multiply unsatisfied matrices");
			return null;
		}
	}
}

import java.util.Date;


public class MatrixMultiplication {


	
	public static void main (String args [] ) {
		
		Matrix m1 = new Matrix(500,500);
		m1.fillMatrix();
		
		Matrix m2 = new Matrix(500, 500);
		m2.fillMatrix();
		
		long begin = new Date().getTime();
		System.out.println(m1.multiply(m2));
		long end = new Date().getTime();
		
		System.out.println("running time is : "+ (end -begin) / 1000 + "s");
		
	}
}

class Matrix {
	protected int data [][] ;
	protected int h,w;
	
	Matrix (int h,int w) {
		this.h = h;
		this.w = w;
		data = new int [h][w];
	}
	
	public void setElement(int i, int j , int value) {
		data [i][j] = value;
	}
	
	public int [] getRow (int index) {
		return data [index];
	}
	
	public int [] getColumn (int index) {
		int [] ret = new int [h];
		for (int i = 0 ; i < h ; i ++) {
			ret [i] = data[index][i];
		}
		return ret;
	}
	public Matrix multiply(Matrix matrix) {
		
		if(this .w == matrix.h && this.h == matrix.w) {
			
			Matrix ret = new Matrix(h, h);
			
			for(int i = 0 ; i < h ; i++) {
				for (int j = 0 ; j < w ; j++) {
					int temp = 0;
					
					for (int k = 0 ; k < w ; k ++){
						temp += this.data[i][k] * matrix.data[k][j];
					}
					ret.data [i][j]  = temp;
				}
			}
			return ret;
			
		} else {
			System.err.println("fail to multiply unsatisfied matrices");
			return null;
		}
	}
	
	
	
	public void fillMatrix() {
		for(int i = 0 ; i < h ; i++) {
			for (int j = 0 ; j < w ; j++) {
				data[i][j] = 1;
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String ret = "";
		for(int i = 0 ; i < h ; i++) {
			for (int j = 0 ; j < w ; j++) {
				ret+= data[i][j]+" ";
			}
			ret+="\n";
		}
		
		return ret;
	}
	
}
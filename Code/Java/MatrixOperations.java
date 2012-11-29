
public class MatrixOperations {

	public static double[][] elementByElement(double [][] a, double [][] b)
	{

		double [][] c = new double [a.length][a[0].length];

		for (int i = 0; i < a.length; i++)
		{
			for (int k=0; k < a[0].length; k++)
			{
				c[i][k]=a[i][k]*b[i][k];
			}
		}

		return c;
	}

	public static double [][] scalarMultiplication(double [][] a, double s)
	{

		for (int i =0; i < a.length; i++)
		{
			for (int k=0; k <a[0].length; k++)
			{
				a[i][k]=a[i][k]*s;
			}
		}
		return a;
	}

	public static double [][] scalarDivision(double [][] a, double s)
	{

		for (int i =0; i < a.length; i++)
		{
			for (int k=0; k <a[0].length; k++)
			{
				a[i][k]=a[i][k]/s;
			}
		}
		return a;
	}

	public static double[][] addition(double [][] a, double [][] b)
	{

		double [][] c = new double [a.length][a[0].length];

		for (int i = 0; i < a.length; i++)
		{
			for (int k=0; k < a[0].length; k++)
			{
				c[i][k]=a[i][k]+b[i][k];
			}
		}

		return c;
	}

	public static double[][] subtraction(double [][] a, double [][] b)
	{

		double [][] c = new double [a.length][a[0].length];

		for (int i = 0; i < a.length; i++)
		{
			for (int k=0; k < a[0].length; k++)
			{
				c[i][k]=a[i][k]-b[i][k];
			}
		}

		return c;
	}

	public static double [][] transpose(double [][] a)
	{
		double [][] c = new double [a[0].length][a.length];

		for (int i = 0; i < c.length; i++)
		{
			for (int k = 0; k < c[0].length; k++)
			{
				c[i][k]=a[k][i];
			}
		}

		return c;

	}

	public static double [][] multiplication(double[][] a, double [][] b)
	{
		double [][] c = new double [a.length][b[0].length];

		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < b[0].length; j++) {
				for(int k = 0; k < a[0].length; k++){

					c[i][j] += a[i][k]*b[k][j];
				}
			}  
		}

		return c;
	}

	public static double [][] sigmoidFunction(double [][] a)
	{

		for (int i =0; i < a.length; i++)
		{
			for (int k=0; k <a[0].length; k++)
			{
				a[i][k]=1/(1 + Math.pow(Math.E,-a[i][k]));
			}
		}
		return a;
	}

}

import java.util.ArrayList;
import java.util.StringTokenizer;


public class preFlopPotential {
	
	public static int potential(String hand)
	{
		ArrayList<String> handList = readFiles.readLinesAsStrings("pokerPreFlopTable.txt");
		int i = 0;
		int j = 0;
		String temp;
		boolean done = false;
		String compare;
		do
		{
			StringTokenizer data = new StringTokenizer(handList.get(i)); 
			compare = data.nextToken();
			compare = data.nextToken();
			if(compare.compareTo(hand) == 0)
			{
				done = true;				
			}
			i++;
			
		}while(i < 169 && !done);
		
		done = false;
		do
		{
			StringTokenizer data = new StringTokenizer(handList.get(j)); 
			compare = data.nextToken();
			compare = data.nextToken();
			temp =""+ compare.charAt(1) + compare.charAt(0) + compare.charAt(2);
			if(temp.compareTo(hand) == 0)
			{
				done = true;				
			}
			j++;
			
		}while(j < 169 && !done);
		
		if( i <= j)
			return (i);
		else
			return(j);
	}

}

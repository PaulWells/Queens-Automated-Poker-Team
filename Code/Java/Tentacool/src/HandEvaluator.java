//package tools;
/*
 * Aces are 52-49. 
 * 
 * All hands that tie will return the same value
 * 
 * lower numbers are for better hands
 * 
 * Cards range from 1-52 (2,2,2,2,3,3,3,....,A,A,A,A)
 * 
 */
import java.io.*;
// TwoPlusTwo 5-7 step lookup hand evaluator
public class HandEvaluator {

	public static PrintWriter out;
    private static String HAND_RANK_DATA_FILENAME = "HandRanks1.dat";
    private static int HAND_RANK_SIZE = 32487834;
    public static int HR[] = new int[HAND_RANK_SIZE];
    
    public static int lookupHand(int[] cards){
    	int rank = 0;
    	if (cards.length == 5){
    		rank = lookupHand5(cards);
    	}
    	else if(cards.length == 6){
    		rank = lookupHand6(cards);
    	}
    	else{
    		rank = lookupHand7(cards);
    	}
    	
    	return rank;
    }

    public static void test()
    {
    	for(int i = 0; i< 1000; i++)
    	{
    		
    		System.out.println(HR[i]);
    	}
    	   	
    }
    
    public static int lookupHand5(int[] cards) {
    	//loadHandRanks();
    	
        int pCards = 0;
        int p = HR[53 + cards[pCards++]];    
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p];
        
        return p;
    }

    public static int lookupHand6(int[] cards) {
    	//loadHandRanks();
    	
        int pCards = 0;
        int p = HR[53 + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p];
        
        return p;
    }
    
    public static int lookupHand7(int[] cards) {
    	//loadHandRanks();
    	
        int pCards = 0;
        int p = HR[53 + cards[pCards++]];
        
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
        p = HR[p + cards[pCards++]];
//        p = HR[p];
        return p;
    }


    
    /**
     * Converts a little-endian byte array to a Java (big-endian) integer.
     * We need this because the HandRanks.dat file was generated using
     * a little-endian C program and we want to maintain compatibility.
     * @param b
     * @param offset
     * @return
     */
    private static final int littleEndianByteArrayToInt(byte[] b, int offset) {
        return (b[offset + 3] << 24) + ((b[offset + 2] & 0xFF) << 16)
                + ((b[offset + 1] & 0xFF) << 8) + (b[offset] & 0xFF);
    }
    
    public static void loadHandRanks() {
        int tableSize = HAND_RANK_SIZE * 4;
        byte[] b = new byte[tableSize];
        InputStream br = null;
        try {
            br = new BufferedInputStream(new FileInputStream(HAND_RANK_DATA_FILENAME));
            int bytesRead = br.read(b, 0, tableSize);
            if (bytesRead != tableSize) {
        		System.out.println("yeuss");        
            }
        } catch (FileNotFoundException e) {
        		System.out.println("File not found.");        
        } catch (IOException e) {
    		System.out.println("IO exception");        
        } finally {
            try {
                br.close();
            } catch (IOException e) {
        		System.out.println("IO exception closing the file.");        
            }
        }
        for (int i = 0; i < HAND_RANK_SIZE; i++) {
            HR[i] = littleEndianByteArrayToInt(b, i * 4);
        }
    }
    


}



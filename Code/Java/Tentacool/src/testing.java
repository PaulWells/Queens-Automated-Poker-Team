
public class testing {
	
	public static void main(String args[])
	{
	int[] cards = {23, 47, 43, 39, 35};
	//int[] cards2 = {2, 12, 23};
	
	HandEvaluator.loadHandRanks();
	int rank = HandEvaluator.lookupHand(cards);
	System.out.println(rank);
	float rank2 = HandPotential.EffectiveHandStrength(cards);
	
	
	
	System.out.println(rank2);
	}

}

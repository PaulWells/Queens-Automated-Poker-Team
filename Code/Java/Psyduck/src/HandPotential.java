//package tools;
import java.io.*;
import java.util.*;




public class HandPotential	{
	
	public static float getHandStrength(int[] cards)
	{
		
		float blank = EffectiveHandStrength(cards);
		
		return Global.a;
	}
	
	public static float getHandPotential(int[] cards) 
	{
		float blank = EffectiveHandStrength(cards);
		
		return Global.b;
	}

	private static final int ahead = 0;
	private static final int tied = 1;
	private static final int behind = 2;


	public static float EffectiveHandStrength(int[] cards) 
	{
		 int[] CardVector = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,
			29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52};

			float hand_strength;
			float[] HPTot = {0,0,0};
			float[][] HP= { {0,0,0}, {0,0,0}, {0,0,0} };
			int index;
			float PPOT;
			float NPOT;
			float EHS;
			float newRank;
			float newOppRank;
			int oppRank;
			int currentRank;
			int[] opponentHand = new int[5];
			int[] futureOpponentHand = new int[7];
			int[] futureHand = new int[7];
		 
		 		 
		 
		opponentHand[0] = cards[2];
		opponentHand[1] = cards[3];
		opponentHand[2] = cards[4];
		
		futureHand[0] = cards[0];
		futureHand[1] = cards[1];
		futureHand[2] = cards[2];
		futureHand[3] = cards[3];
		futureHand[4] = cards[4];

		futureOpponentHand[0] = cards[2];
		futureOpponentHand[1] = cards[3];
		futureOpponentHand[2] = cards[4];
		
		CardVector[cards[0]-1] = 999;
		CardVector[cards[1]-1] = 999;
		CardVector[cards[2]-1] = 999;
		CardVector[cards[3]-1] = 999;
		CardVector[cards[4]-1] = 999;
		currentRank = HandEvaluator.lookupHand(cards);
//		System.out.println("Hand Rank is:\t"+currentRank);
		
		
		for (int i = 1; i < 53; i++){
			if (CardVector[i-1] < 53){
				for (int j = i+1; j < 53; j++){
					if (CardVector[j-1] < 53){
						
						opponentHand[3] = i;
						opponentHand[4] = j;
						oppRank = HandEvaluator.lookupHand(opponentHand); // get opponents rank for each iteration

						if (currentRank < oppRank){
							index = ahead;
						}
						else if (currentRank == oppRank){
							index = tied;
						}
						else{
							index = behind;
						}
						HPTot[index] += 1;



					// go through every possible remaining board pairing (45 choose 2) = 990
					CardVector[i-1] = 999; // remove opponent cards for loop
					CardVector[j-1] = 999;
				
					
					for (int k = 1; k < 53;k++){
						if (CardVector[k-1] < 53){
							for (int l = k+1; l < 53;l++){
									if (CardVector[l-1] < 53){
										futureOpponentHand[3] = i;
										futureOpponentHand[4] = j;
										futureOpponentHand[5] = k;
										futureOpponentHand[6] = l;
										futureHand[5] = k;
										futureHand[6] = l;
										newRank = HandEvaluator.lookupHand(futureHand);
										newOppRank = HandEvaluator.lookupHand(futureOpponentHand);
//										if (newRank == 0 || newOppRank == 0){
//											System.out.println("Bad eval:\tOpponent rank = " + newOppRank + "\t my rank = " +newRank);
//											for (int q = 0; q < 7; q++){
//												System.out.print("opp: " + futureOpponentHand[q] + "\t");		
//											}
//											System.out.println();
//											for (int r = 0; r < 7; r++){
//												System.out.print("mine: " + futureHand[r] + "\t");		
//											}
//											System.out.println();
//											System.exit(0);
//										}

							if (newRank < newOppRank){
								HP[index][ahead] += 1;
								}
							else if(newRank == newOppRank){
								HP[index][tied] += 1;
								}
							else{
								HP[index][behind] += 1;
								}

							}
						}
					}

					}
					CardVector[i-1] = i; //reset opponent cards for next iter
					CardVector[j-1] = j;
				}
			}
		}
		}

		hand_strength = (HPTot[ahead] + (HPTot[tied]/2)) / (HPTot[ahead] + HPTot[tied] + HPTot[behind]);
		if (HPTot[behind] == 0 && HPTot[tied] == 0){
			// if you flop a royal flush you will get an error when you try to divide by zero
			PPOT = 0;
		}
		else{
			PPOT = (HP[behind][ahead] + (HP[behind][tied]/2) + (HP[tied][ahead])) / (HPTot[behind]*990 + HPTot[tied]*990/2);
		}
		NPOT = (HP[ahead][behind] + (HP[ahead][tied]/2) + (HP[tied][behind]/2)) / (HPTot[ahead]*990 + HPTot[tied]*990/2);
		
//		System.out.println("Relative Hand Strength:\t" + hand_strength);
//		System.out.println("Negative Potential:\t" + NPOT);
//		System.out.println("Positive Potential:\t" + PPOT);
//		System.out.println("HPTot array:\t" + HPTot[0] + "\t"+ HPTot[1] + "\t" + HPTot[2]);
//		System.out.println("HP array[0][0:2]:\t" + HP[0][0] + "\t"+ HP[0][1] + "\t" + HP[0][2]);
//		System.out.println("HP array[1][0:2]:\t" + HP[1][0] + "\t"+ HP[1][1] + "\t" + HP[1][2]);
//		System.out.println("HP array[2][0:2]:\t" + HP[2][0] + "\t"+ HP[2][1] + "\t" + HP[2][2]);
//


//		EHS = hand_strength*(1-NPOT) + (1-hand_strength)*PPOT;
		EHS = hand_strength + (1-hand_strength)*PPOT;
		Global.a = hand_strength;
		Global.b = PPOT;
		return EHS;
	}
}

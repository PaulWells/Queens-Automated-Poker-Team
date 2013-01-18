public class Psyduck 
{
	
	public static char decision (String input)
	{
		char decision;
		
		matchState parser = new matchState();
		parser.updateState(input);
		
		//necessary variables for determining decision
		String hand;
		int actionNumber;
		int[] cards;
		int potSize;
		int stage;
		char oppDecision = 'c';
		
		//variables used for opponent modeling
		double aggressive = 0.5; //dynamic (prob of playing aggressive)
		double passive = 0.5; 	//dynamic (prob of playing passive)
		double raiseChanceA = 0.6; //constant (prob of raising given aggressive)
		double raiseChanceP = 0.4; //constant (prob of raising given passive)
		int oppStyleScore = 0;
		
		//determines probability of opponent being aggressive
		if(oppDecision == 'r')
		{
			aggressive = (raiseChanceA*aggressive) / ( (raiseChanceA * aggressive) + (raiseChanceP * passive) );
		}
		
		//uses aggressive prob to adjust style score
		if(Math.random() > 1-aggressive)
		{
			oppStyleScore--;
		}
		
		else
		{
			oppStyleScore++;
		}
		
		
		//if we are playing tight
		if(oppStyleScore < 0)
		{
			decision = playingTight(hand, actionNumber, cards, potSize, stage);
		}
		
		//if we are playing loose
		if(oppStyleScore >= 0)
		{
			decision = playingLoose(hand, actionNumber, cards, potSize, stage, oppDecision);
		}
		return decision;
	}
	
	//logic for tight player
	//hand is our hand , actionNumber is the number of actions we have completed this round (starts at 0),
	//cards is the int array of all cards ours + table, potSize = pot size in the current round,
	//stage is where we are in the round 0 = preflop, 1= postflop, 2 = turn, 3 = river
	public static char playingTight(String hand, int actionNumber, int[] cards, int potSize, int stage) 
	{
		char decision = 'f';
		
		//preflop logic
		if(stage == 0) //check if we are in the preflop
		{
			int preFlopRank = preFlopPotential.potential(hand);
			
			if(preFlopRank < 89) 
			{
				//hand is not worth playing so do nothing				
			}
			
			if(preFlopRank < 27)
			{
				decision = 'c'; //hand is too weak to raise so we only call			
				
			}
			else
			{
				if(actionNumber == 0)
				{
					decision = 'r'; //raise only once					
				}
				
				else
				{
					decision = 'c'; //call other times					
				}
						
			}
		
		}
		
		//postflop logic
		else
		{
			float EHS = HandPotential.EffectiveHandStrength(cards);
			
			if(EHS > 0.8) //hand is good so raise always
			{
				decision = 'r';
			}
			
			else if(EHS > 0.6) //hand is good so call always
			{
				decision = 'c';
				
			}
			
			else //hand is bad
			{
				float handStrength = HandPotential.getHandStrength(cards);
				float handPotential = HandPotential.getHandPotential(cards);
				
				if((handPotential - handStrength) >= 0.5) //if hand has good potential but is not currently highly ranked
				{
					if(handPotential > (1/potSize)) // check pot odds; if good then call, otherwise fold
					{
						decision = 'c';
					}
					
				}
								
			}
		
		}
		
		return decision;
	}
	
	//logic for loose player
	//hand is our hand , actionNumber is the number of actions we have completed this round (starts at 0), cards is the int array of all cards ours + table
	// potSize = pot size in the current hand, stage is where we are in the round 0 = preflop, 1= postflop, 2 = turn, 3 = river
	//oppDecision is the opponent's decision
	public static char playingLoose(String hand, int actionNumber, int[] cards, int potSize, int stage, char oppDecision)
	{
		char decision = 'f';
		
		//preflop logic
		if(stage == 0) //check if we are in the preflop
		{
			int preFlopRank = preFlopPotential.potential(hand);
			
			if(preFlopRank < 68) //raise indefinitely (great hand)
			{
				decision = 'r';				
			}
			
			else if(preFlopRank < 120)
			{
				if(actionNumber == 0 ) //raise once then call indefinitely (good hand)
				{
					decision = 'r';
				}
				else
				{
					decision = 'c';
				}
				
			}
			else
			{
				//bad hand so fold
			}
			
		}
		
		
		//postflop logic
		else
		{
			if(stage == 1)
			{
				if(actionNumber == 0)
				{
					decision = 'r';				
				}
				else if(oppDecision == 'r')
				{
					decision = 'r';
				}
			}
			else
			{
				float EHS = HandPotential.EffectiveHandStrength(cards);
			
				if(EHS > 0.8) //hand is good so raise always
				{
					decision = 'r';
				}
			
				else if(EHS > 0.6) //hand is good so call always
				{
					decision = 'c';
				
				}
			
				else
				{
					float handStrength = HandPotential.getHandStrength(cards);
					float handPotential = HandPotential.getHandPotential(cards);
				
					if((handPotential - handStrength) >= 0.5) //if hand has good potential but is not currently highly ranked
					{
						if(handPotential > (1/potSize)) //check pot odds; if good then call, otherwise fold
						{
						decision = 'c';
						}
					
					}
				}
								
			}			
			
		}		
		
		return decision;
	}
}

/* Tentacool v1.0
 * Author: Ryan Lowe
 * 
 * This version of Tentacool initializes two values, a and b, between 0 and 1. It takes the handRank
 * (standardized between 0 and 1) and if it lies between 0 and a, the bot folds. If it lies between
 * a and b, the bot calls or checks, and if it lies between b and 1, the bot raises. a and b are 
 * adjusted based on the number of opponent bot raises (weighted more heavily for recent rounds) and
 * check raises. The bot only checks/ calls on the pre-flop.
 * 
 * THIS VERSION USES 0.5 FOR ALL HAND RANK TO SEE IF IT WORKS
 * 
 */

public class Tentacool {
	public char decision(String input)
	{
		matchState parser = new matchState();
		parser.updateState(input);
			
		char decision; //0 is fold, 1 is check/call, 2 is raise
				
		/* required variables at the start of each round (not all currently available):
		handRank - preferably normalized between 0 and 1
		roundNum – current round (0 for preflop, 1 for flop, etc.)
		checkRaise - whether the opponent has check-raised this round
		oppRaiseArray - an array with the number of opponent raises in each round
		
		not currently available:
		nPot, pPot – negative and positive hand potentials
		opponentDecision – the decision the opponent just made this round (n=no decision made)
		opponentCheck – whether the opponent has checked this round, to detect check-raises
		a, b – current hand delimiters, initialized at beginning of hand (0.35, 0.8)
		*/
		
		//list of arbitrary constants:
		double a = 0.35;
		double b = 0.8;
		double aMax = 0.80;
		double bMax = 0.97;
		double raiseconstA=0.2;//0.1
		double raiseconstB=0.15;//0.05
		
		//char opponentDecision=parser.getOpponentLastDecision(); //last decision by opponent (n,f,c,r)
		boolean opponentCheckRaise=parser.getCheckRaise(); 
		int roundNum=parser.getRoundNum();
		//double handRank=parser.getHandRank(); 
		double handRank=0.5;
		double[] opponentRaise=parser.getOppRaiseArray();
		
		double raiseNum=0; 
		
		/* to be included once potential calculations are working
		double ehs=handRank+pPot*(1-handRank);
		double potential=(pPot-nPot)/2;
		*/
		
		//pre-flop code
		if(roundNum==0){
			
			decision='c'; //bot calls on pre-flop no matter what
			//to implement: Stosh's pre-flop table
		}
		//post-flop code
		else{
			for(int i=0; i<=roundNum; i++){
				raiseNum+=opponentRaise[i]/(roundNum+1); //increments raiseNum
				//raiseNum is weighted based on which round the raises were in
			}
			if(opponentCheckRaise==true){ //checks whether the opponent has check raised this round
				a+=(raiseNum+1)*raiseconstA;
				b+=(raiseNum+1)*raiseconstA;
			}
			else{
				a+=raiseNum*raiseconstA;
				b+=raiseNum*raiseconstA;
			}
			
			/* to be included once the last opponent decision is a variable
			if(opponentDecision!='n'){ //if the opponent has made a move this round
				if(opponentDecision=='r'){	//adjustment of a and b based on opponent’s last decision
					if(opponentCheck==true){
						a+=2*raiseconstA;
						b+=2*raiseconstB;
					}
					else{
						a+=raiseconstA;
						b+=raiseconstB;
					}
				}
				
				 //to be included once potential calculations are working
				if(roundNum!=3){	//adjustment of a and b based on hand potential (only if not river)
					a-=potential;	
					b+=potential;
				}
			*/
			
			if(a>aMax)
				a=aMax;
			if(b>bMax)
				b=bMax;
				
			if(handRank<=a) //to use ehs instead of handRank once hand potential calculations working
				decision='f';
			else if(handRank<=b)
				decision='c';
			else
				decision='r';
				
		}
		
		return decision;
	}

}

import java.util.*;

/* the matchState object hold information about the current state of the round.  It gathers information from the betString and then
 * removes that information from the betString.  
 * Raise to call ratio is returned as a negative 1 if the opponent has made no raises or calls yet
 */

public class matchState {
	private boolean ourTurn;
	private boolean endHand;
	private int roundNum;
	private int position;
	private String betString;
	private int hand[] = new int[2];
	private float handStrength;
	private int ihandRank;	//hand rank as an integer (the way it comes out of the hand rank function)
	private float fhandRank;	//hand rank converted to a float between 0 and 1
	private float potOdds;
	private float rcRatio;	//opponent's raise to call ratio
	private int handNum;
	private String betLog;	//substring of betString which holds information about raises and calls in hand so far
	private int opponentPosition;
	private int betAmount = 10;	//the fixed raise amount
	private int[] oppRaiseArray = new int[4];  //holds the number of raises from each round
	private boolean checkRaise;
	private char opponentDecision;
	
	
	public int getRoundNum(){
		return roundNum;
	}
	
	public int getPosition(){
		return position;
	}
	
	public float getPotOdds(){
		return potOdds;
	}
	
	public float getRCRatio(){
		return rcRatio;
	}
	
	public int gethandNum(){
		return handNum;
	}
	
	public boolean getCheckRaise(){
		return checkRaise;
	}
	
	public boolean getOurTurn(){
		return ourTurn;
	}
	
	public int[] getOppRaiseArray(){
		return oppRaiseArray.clone();
	}
	
	private void setbetString(String newString){
		betString = newString;
		return;
	}
	
	
	//removes "MATCHSTATE:" label at beginning of betString
	private void prepareString(){
		betString = betString.substring("MATCHSTATE:".length(), betString.length());
		return;
	}
	
	private void setHand(){
		
		String firstCard;
		String secondCard;
		
		int startLocation = betString.indexOf('|');
		
		firstCard = betString.substring(startLocation+1,startLocation+3);
		secondCard = betString.substring(startLocation+3,startLocation+5);
		
		System.out.println(firstCard);
		System.out.println(secondCard);
		
		hand[0] = convertCard(firstCard);
		hand[1] = convertCard(secondCard);
		
		return;
		
	}
	
	private int convertCard(String card){
		
		char suit = card.charAt(1);
		char rank = card.charAt(0);
		int rankNum;
		int suitNum;
		
		if((int)rank >= 48 && (int)rank <= 57){
			rankNum = (int)rank - 48;
		}
		else if(rank == 'T'){
			rankNum = 10;
		}
		else if(rank == 'J'){
			rankNum = 11;
		}
		else if(rank == 'Q'){
			rankNum = 12;
		}
		else if(rank == 'K'){
			rankNum = 13;
		}
		else{
			rankNum = 14;
		}
		
		if(suit == 's'){
			suitNum = 0;
		}
		else if(suit == 'h'){
			suitNum = 1;
		}
		else if(suit == 'c'){
			suitNum = 2;
		}
		else{
			suitNum = 3;
		}
		
		return ((rankNum - 2) * 4) + suitNum +1;
	}
	
	private void setOurTurn(){
		boolean preflop;
		int position = Character.getNumericValue(betString.charAt(0));
		endHand = false;
	
		if(betString.indexOf("/") == -1){
			preflop = true;
		}
		else{
			preflop = false;
		}
		
		//find roundstring
		String roundstring = null;
		
		int endbetstring = betString.lastIndexOf(":");
		
		if(preflop){
			if (betString.charAt(endbetstring - 1) == ':'){
				roundstring = "";
			}
			else{
				int startbetstring = betString.substring(0,endbetstring).lastIndexOf(":")+1;
				roundstring = betString.substring(startbetstring,endbetstring);
			}
		}
		else{
			
			int startbetstring = betString.substring(0,endbetstring).lastIndexOf("/")+1;
			roundstring = betString.substring(startbetstring,endbetstring);
		}
		
		if(roundstring.length() == 0){
			opponentDecision = 'n';
		}
		else {
			opponentDecision = roundstring.charAt(roundstring.length()-1);
		}
		
		if(preflop){
			if(position == 1 && roundstring.length()%2 == 0 || position == 0 && roundstring.length()%2 == 1){
				ourTurn = true;
			}
			else
				ourTurn = false;
		}
		else{
			if(position == 0 && roundstring.length() %2 == 0 || position == 1 && roundstring.length()%2 == 1)
				ourTurn = true;
			else
				ourTurn = false;
		}
		
		if(roundstring.indexOf("f") != -1){
			endHand = true;
		}
		
		//count number of /
		int count = 0;
		for(int i = 0; i< betString.length();i++){
			if(betString.charAt(i) == '/'){
				count++;
			}
		}
		
		if(count == 6){
			if(roundstring.length() > 1 && roundstring.charAt(roundstring.length()-1) == 'c')
				endHand = true;
		}
		
		if (endHand == true){
			ourTurn = false;
		}
		
		
	}
	
	
	//sets position number (0 or 1) and removes it from string, assumes prepareString has been called
	//also sets opponent's position which is the opposite of our position
	private void setPosition(){
		position = Integer.parseInt(betString.substring(0,1));
		betString = betString.substring(2,betString.length());
		
		if(position ==1)
			opponentPosition = 0;
		else
			opponentPosition = 1;
		
		return;
	}
	
	//sets hand number and removes it from bet string, assumes setPosition has been called
	private void setHandNum(){
		handNum = Integer.parseInt(betString.substring(0,1));
		betString = betString.substring(2,betString.length());
		return;
	}
	
	//gets betlog and removes it from betstring, assumes setHandNum has been run on betstring, betlog includes trailing semi colon
	private void setBetLog(){
		betLog = betString.substring(0,betString.indexOf(":", 0)+1);
		betString = betString.substring(betString.indexOf(":", 0)+1, betString.length());
		return;
	}
	
	private void setRoundNum(){
		int count = 0;
		for (int i = 0; i<betLog.length();i++){
			if(betLog.charAt(i)=='/')
				count++;
		}
		
		roundNum = count +1;
	}
	
	//counts the number of calls and raises the opponent makes returns an array with number of opponent's raises in 0 position, number
	//of opponent's calls in the 1 position, our raises in the 2 position, our calls in the three position
	private int[] countRound(String roundString, boolean isPreFlop){
		int oppcountr=0;	//tally of opponent's raises
		int oppcountc=0;	//tally of opponent's calls
		int ourcountr = 0;	//tally of our raises
		int ourcountc = 0;	//tally of our calls
		
		
		
		if((opponentPosition == 1 && isPreFlop == true) ||(opponentPosition == 0 && isPreFlop == false)){
			//go second
			
			
			
			for(int i=0;i<roundString.length();i=i+1){
				
				if(i%2 ==0){
					if(roundString.charAt(i)=='r')
						oppcountr = oppcountr+1;
					else if(roundString.charAt(i) =='c')
						oppcountc = oppcountc+1;
				}
				else{
					if(roundString.charAt(i)=='r')
						ourcountr = ourcountr+1;
					else if(roundString.charAt(i) =='c')
						ourcountc = ourcountc+1;
				}		
			}
		}
		else{
			
			
			for(int i=0;i<roundString.length();i=i+1){
				if(i%2 ==1){
					if(roundString.charAt(i)=='r')
						oppcountr = oppcountr+1;
					else if(roundString.charAt(i) =='c')
						oppcountc = oppcountc+1;
				}
				else{
					if(roundString.charAt(i)=='r')
						ourcountr = ourcountr+1;
					else if(roundString.charAt(i) =='c')
						ourcountc = ourcountc+1;
				}		
				
			}
		}
		
		int[] countArray = new int[4];
		
		countArray[0] = oppcountr;
		countArray[1] = oppcountc;
		countArray[2] = ourcountr;
		countArray[3] = ourcountc;
		
		return countArray;
	}
	
	//sets opponents raise to call ratio, raises per round assumes setBetLog has been run
	private void setRaiseCallData(){
		int oppcountr=0;	//tally of opponent's raises
		int oppcountc=0;	//tally of opponent's calls
		int ourcountr=0;	//tally of opponent's raises
		int ourcountc=0;	//tally of opponent's calls
		boolean checkRaise = false; 	//is true if the opponent has performed a check raise this round
		
		int[] countArray = new int[4];
		String preflop;
		String roundString; //holds raise and call sequence for one round
		String matchLog = betLog;
		
		if(matchLog.indexOf("/") != -1)
			preflop = matchLog.substring(0, matchLog.indexOf("/"));
		else
			preflop = matchLog.substring(0, matchLog.indexOf(":"));
		
		//cut preflop from matchLog including trailing '/' if there is one
		matchLog = matchLog.substring(0,matchLog.indexOf(preflop)) + matchLog.substring(matchLog.indexOf(preflop)+preflop.length()+1, matchLog.length());
		if(matchLog.length()>0 && matchLog.charAt(0)=='/')
			matchLog = matchLog.substring(1,matchLog.length());
		
		//add preflop data to counts if there is any data in the preflop
		if (preflop.length()>0){
			countArray = countRound(preflop, true);
			oppcountr = oppcountr + countArray[0];
			oppcountc = oppcountc + countArray[1];
			ourcountr = ourcountr + countArray[2];
			ourcountc = ourcountc + countArray[3];
	
			
		}
		
		
		oppRaiseArray[0] = oppcountr;
		
		int n = 2;//counts round 
		//next three rounds if there is any data yet
		while(matchLog.length()>0 && matchLog.charAt(0)!= ':'){
			
			//find betting string for next round and remove it but leave in semicolon if it is the last round
			if(matchLog.indexOf('/') == -1){
				roundString = matchLog.substring(0, matchLog.indexOf(':'));
				matchLog = matchLog.substring(roundString.length(),matchLog.length());
			}
			else{
				roundString = matchLog.substring(0, matchLog.indexOf('/'));
				matchLog = matchLog.substring(roundString.length()+1,matchLog.length());
			}
			
			if (roundString.length()> 0){
				countArray = countRound(roundString, false);
				oppcountr = oppcountr + countArray[0];
				oppcountc = oppcountc + countArray[1];
				ourcountr = ourcountr + countArray[2];
				ourcountc = ourcountc + countArray[3];
			}
			
			oppRaiseArray[n-1] = countArray[0];
			n++;
			
		}
		
		if(oppcountc + oppcountr !=0)
			rcRatio = (float)oppcountr/(float)(oppcountc +oppcountr);
		else
			rcRatio = -1;
		
	}
	
	
	
	//calculates the pot odds, assumes setRCRatio has been called
	private void setPotOddsAndCheckRaise(){
		//1 goes first in preflop, 0 afterwards
		float oppPot; //total money in pot from opponent
		float ourPot; //total money in pot from us
		float oppThisRound = 0 ;	//money in pot from opponent this round
		float ourThisRound = 0 ;	//money in pot from us this round
		char oppLast = ' ';		//the last play the opponent made this round
		boolean isPreFlop = true;
		checkRaise = false;
		int dashIndex = 0;
		
		if(position == 0){
			ourPot = betAmount;
			oppPot = (float) (betAmount/2.0);
		}
		else{
			oppPot = betAmount;
			ourPot = (float) (betAmount/2.0);
		}
			
		
		for(int i=0; i< betLog.length();i++){
			
	
			
			if(betLog.charAt(i)=='/'){
				checkRaise = false;
				oppLast = ' ';
				dashIndex = i;
				
			}
			
			if(betLog.charAt(i) == '/' ||betLog.charAt(i) == ':'){
				isPreFlop = false;
				oppPot = oppPot + oppThisRound;
				ourPot = ourPot + ourThisRound;
				oppThisRound = 0;
				ourThisRound = 0;
			}
				
			if(betLog.charAt(i) == 'c'){
				if(isPreFlop == true && (i-dashIndex)%2 != position)
					ourThisRound = oppThisRound;
				else if(isPreFlop == true && (i-dashIndex)%2 != opponentPosition){
					oppThisRound = ourThisRound;
					oppLast = 'c';
				}
				else if(isPreFlop == false && (i-dashIndex)%2 != position)
					ourThisRound = oppThisRound;
				else{
					oppThisRound = ourThisRound;
					oppLast = 'c';
				}
			}
			else if(betLog.charAt(i) == 'r'){
				if(isPreFlop == true && (i-dashIndex)%2 != position)
					ourThisRound = oppThisRound + betAmount;
				
				else if(isPreFlop == true && (i-dashIndex)%2 != opponentPosition){
					oppThisRound = ourThisRound + betAmount;
					if(oppLast == 'c')
						checkRaise = true;
					oppLast = 'r';
				}
				else if(isPreFlop == false && (i-dashIndex)%2 != position)
					ourThisRound = oppThisRound + betAmount;
				else{
					oppThisRound = ourThisRound + betAmount;
					if(oppLast == 'c')
						checkRaise = true;
					oppLast = 'r';
				}
			}
		}
		
		if(oppPot + ourPot != 0)
			if(oppLast == 'c')
			potOdds = 0/(float)(oppPot+ourPot);
		else if(oppLast =='r')
			potOdds = betAmount/(float)(oppPot+ourPot);
	}
		
	public void updateState(String newstring) { 
		
		
		setbetString(newstring);
		System.out.println(betString);
		prepareString();
		setOurTurn();
		setPosition();
		setHand();
		System.out.println("position: " + position);
		setHandNum();
		System.out.println("handNum: " + handNum);
		setBetLog();
		System.out.println("betLog: " + betLog);
		setRoundNum();
		System.out.println("roundNum: " + roundNum);
		setRaiseCallData();
		System.out.println("raise to call ratio: " + rcRatio);
		for(int i = 1;i<=4;i++)
			System.out.println("Number of Opponent Raises in Round: " + i + " "+ oppRaiseArray[i-1]);
		setPotOddsAndCheckRaise();
		System.out.println("pot odds: " + potOdds);
		System.out.println("checkRaise: " + checkRaise);
		System.out.println("Our Turn?:" + ourTurn);
		System.out.println("first card: " + hand[0]);
		System.out.println("second card: " + hand[1]);
		
	}
	
	/*//initialize an empty matchstate object
	public matchState (){
		//set variables to null or whatever here
	}*/


	public static void main (String[] args) { 
		String sample = "MATCHSTATE:0:0:cc/rcr:|AsAh/8dAs8s";
		
		matchState test = new matchState();
		test.updateState(sample);
	
	}
	
}

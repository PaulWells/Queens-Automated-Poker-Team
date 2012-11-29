/*
 * matchState.hpp
 *
 *  Created on: 2012-03-06
 *      Author: Spock
 */


#include <iostream>
#include <algorithm>
#include <vector>
#include <time.h>
#include <string>
#include <stdio.h>
#include <stdlib.h>
#include <stdlib.h>
#include <sstream>
using namespace std;

class matchState{

	int roundNum;
	int position;
	string betString;
	//int hand[];
	float handStrength;
	int ihandRank;	//hand rank as an integer (the way it comes out of the hand rank function)
	float fhandRank;	//hand rank converted to a float between 0 and 1
	float potOdds;
	float rcRatio;	//opponent's raise to call ratio
	int handNum;
	string betLog;	//substring of betString which holds information about raises and calls in hand so far
	int opponentPosition;
	int betAmount;	//the fixed raise amount
	vector<int> oppRaiseArray;  //holds the number of raises from each round, size 4
	bool checkRaise;

private:

	void setbetString(string newString){
		betString=newString;
		return;
	}


	//removes "MATCHSTATE:" label at beginning of betString
	void prepareString(){
		betString = betString.substr(11, betString.length());
		return;
	}


	//MATCHSTATE:0:2:crr/:9d7s|/5d2cJc/3d

	//sets position number (0 or 1) and removes it from string, assumes prepareString has been called
	//also sets opponent's position which is the opposite of our position
	void setPosition(){
		stringstream ss;

		ss<<betString.substr(0,1);
		ss>>position;
		betString = betString.substr(2,betString.length());

		if(position ==1)
			opponentPosition = 0;
		else
			opponentPosition = 1;

		return;
	}

	//sets hand number and removes it from bet string, assumes setPosition has been called
	void setHandNum(){
		stringstream ss;

		ss<<betString.substr(0,1);
		ss>>handNum;
		betString = betString.substr(2,betString.length());
		return;
	}

	//gets betlog and removes it from betstring, assumes setHandNum has been run on betstring, betlog includes trailing semi colon
	void setBetLog(){
		betLog = betString.substr(0,betString.find_first_of(":")+1);
		betString = betString.substr(betString.find_first_of(":")+1, betString.length());
		return;
	}

	 void setRoundNum(){
		int count = 0;
		for (int i = 0; i<betLog.length();i++){
			if(betLog[i]=='/')
				count++;
		}

		roundNum = count + 1;
	}

	//counts the number of calls and raises the opponent makes returns an array with number of opponent's raises in 0 position, number
	//of opponent's calls in the 1 position, our raises in the 2 position, our calls in the three position
	 vector<int> countRound(string roundString, bool isPreFlop){
		int oppcountr=0;	//tally of opponent's raises
		int oppcountc=0;	//tally of opponent's calls
		int ourcountr = 0;	//tally of our raises
		int ourcountc = 0;	//tally of our calls

		if((opponentPosition == 1 && isPreFlop == true) ||(opponentPosition == 0 && isPreFlop == false)){
			for(int i=0;i<roundString.length();i=i+1){

				if(i%2 ==0){
					if(roundString[i]=='r')
						oppcountr = oppcountr+1;
					else if(roundString[i] =='c')
						oppcountc = oppcountc+1;
				}
				else{
					if(roundString[i]=='r')
						ourcountr = ourcountr+1;
					else if(roundString[i] =='c')
						ourcountc = ourcountc+1;
				}
			}
		}
		else{
			for(int i=0;i<roundString.length();i=i+1){
				if(i%2 ==1){
					if(roundString[i]=='r')
						oppcountr = oppcountr+1;
					else if(roundString[i] =='c')
						oppcountc = oppcountc+1;
				}
				else{
					if(roundString[i]=='r')
						ourcountr = ourcountr+1;
					else if(roundString[i] =='c')
						ourcountc = ourcountc+1;
				}

			}
		}

		vector<int> countArray;

		countArray.push_back(oppcountr);
		countArray.push_back(oppcountc);
		countArray.push_back(ourcountr);
		countArray.push_back(ourcountc);

		return countArray;
	}

	//sets opponents raise to call ratio, raises per round assumes setBetLog has been run
	 void setRaiseCallData(){
		int oppcountr=0;	//tally of opponent's raises
		int oppcountc=0;	//tally of opponent's calls
		int ourcountr=0;	//tally of opponent's raises
		int ourcountc=0;	//tally of opponent's calls
		bool checkRaise = false; 	//is true if the opponent has performed a check raise this round

		vector<int> countArray;
		string preflop;
		string roundString; //holds raise and call sequence for one round
		string matchLog = betLog;

		if(matchLog.find_first_of("/") != -1)
			preflop = matchLog.substr(0, matchLog.find_first_of("/"));
		else
			preflop = matchLog.substr(0, matchLog.find_first_of(":"));

		//cut preflop from matchLog including trailing '/' if there is one
		matchLog = matchLog.substr(0,matchLog.find_first_of(preflop)) + matchLog.substr(matchLog.find_first_of(preflop)+preflop.length()+1, matchLog.length());
		if(matchLog.length()>0 && matchLog[0]=='/')
			matchLog = matchLog.substr(1,matchLog.length());

		//add preflop data to counts if there is any data in the preflop
		if (preflop.length()>0){

			countArray = countRound(preflop, true);
			oppcountr = oppcountr + countArray[0];
			oppcountc = oppcountc + countArray[1];
			ourcountr = ourcountr + countArray[2];
			ourcountc = ourcountc + countArray[3];
		}

		oppRaiseArray[0]=oppcountr;

		int n = 2;//counts round
		//next three rounds if there is any data yet

		while(matchLog.length()>0 && matchLog[0]!= ':'){
			//find betting string for next round and remove it but leave in semicolon if it is the last round
			if(matchLog.find_first_of('/') == -1){
				roundString = matchLog.substr(0, matchLog.find_first_of(':'));
				matchLog = matchLog.substr(roundString.length(),matchLog.length());
			}
			else{
				roundString = matchLog.substr(0, matchLog.find_first_of('/'));
				matchLog = matchLog.substr(roundString.length()+1,matchLog.length());
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
	 void setPotOddsAndCheckRaise(){
		//1 goes first in preflop, 0 afterwards
		float oppPot; //total money in pot from opponent
		float ourPot; //total money in pot from us
		float oppThisRound = 0 ;	//money in pot from opponent this round
		float ourThisRound = 0 ;	//money in pot from us this round
		char oppLast = ' ';		//the last play the opponent made this round
		bool isPreFlop = true;
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



			if(betLog[i]=='/'){
				checkRaise = false;
				oppLast = ' ';
				dashIndex = i;

			}

			if(betLog[i] == '/' ||betLog[i] == ':'){
				isPreFlop = false;
				oppPot = oppPot + oppThisRound;
				ourPot = ourPot + ourThisRound;
				oppThisRound = 0;
				ourThisRound = 0;
			}

			if(betLog[i] == 'c'){
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
			else if(betLog[i] == 'r'){
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

		if(oppPot + ourPot != 0){
			if(oppLast == 'c' || oppLast == ' ')
				potOdds = 0/(float)(oppPot+ourPot);
			else if(oppLast =='r')
				potOdds = betAmount/(float)(oppPot+ourPot);
		}
		


		
	}

public:

	 matchState(){
		roundNum=-1;
		position=-1;
		//string betString;
		//int hand[];
		handStrength=-1.0;
		ihandRank=0;	//hand rank as an integer (the way it comes out of the hand rank function)
		fhandRank=0;	//hand rank converted to a float between 0 and 1
		potOdds=-1.0;
		rcRatio=-1.0;	//opponent's raise to call ratio
		handNum=-1;
		//string betLog;	//substring of betString which holds information about raises and calls in hand so far
		opponentPosition=-1;
		betAmount = 10;	//the fixed raise amount
		oppRaiseArray.assign(4,0);  //holds the number of raises from each round, size 4
		checkRaise=false;
	 }


	 void updateState(string newstring) {

		setbetString(newstring);
		cout<<betString<<endl;
		prepareString();
		setPosition();
		cout<<"position: "<< position<<endl;
		setHandNum();
		cout<<"handNum: " << handNum<<endl;
		setBetLog();
		cout<<"betLog: " << betLog<<endl;
		setRoundNum();
		cout<<"roundNum: " << roundNum<<endl;
		setRaiseCallData();
		cout<<"raise to call ratio: " << rcRatio<<endl;
		for(int i = 1;i<=4;i++)
			cout<<"Number of Opponent Raises in Round: " << i << " "<< oppRaiseArray[i-1]<<endl;
		setPotOddsAndCheckRaise();
		cout<<"pot odds: "<< potOdds<<endl;
		cout<<"checkRaise: " << checkRaise<<endl;


	}

//	//initialize an empty matchstate object
//	matchState (){
//		//set variables to null or whatever here
//	}

};





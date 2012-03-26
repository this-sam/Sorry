/*-----------------------------------------
Programmer: Taylor Krammen
Class: CS 205 Software Engineering
Project: Sorry! Boardgame Implementation
-------------------------------------------*/

/*
This class will implement rules and actions for 
each of the cards to be used in the Sorry! game.
*/

public class SRRule {
	
	public int numMoves; //Initialize numMoves
	
	//Will create and return the number of moves and the type of
	//rule associated with each card.
	
	public String[] types(int cardNum,String type) {
		numMoves = 0;
		if (type == "move") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"move"};
			return rule;
		}else if (type == "start") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"start"};
			return rule;
		}else if (type == "split") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"split"};
			return rule;
		}else if (type == "swap") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"swap"};
			return rule;
		}else if (type == "drawAgain") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"drawAgain"};
			return rule;
		}else if (type == "sorry") {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"sorry"};
			return rule;
		}else {
			String[] rule = {"",""};
			return rule;
		}
	}

}

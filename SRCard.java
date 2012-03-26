/*-----------------------------------------
Programmer: Taylor Krammen
Class: CS 205 Software Engineering
Project: Sorry! Boardgame Implementation
-------------------------------------------*/

/*
This class will create the cards to be used in the game.
Each card will inherit rules related to that particular 
card from the SRRule class, it will also display the text
from each card to the user.
*/

public class SRCard {

	public int cardNum;
	
	public SRCard(int cardNum) {
		this.cardNum = cardNum;
	}
	
	public int getcardNum() {
		return cardNum;
	}
	
	public void setcardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	
	//Depending on what card is drawn, will return the
	//text associated with that card to be displayed
	
	public String getText(int cardNum) {
		if (cardNum == 1)
			return "You drew a 1, you may now move a pawn from Start or move a pawn 1 space forward.";
		else if (cardNum == 2)
			return "You drew a 2, you may now move a pawn from Start or move a pawn 2 spaces forward.  Then please draw another card.";
		else if (cardNum == 4)
			return "You drew a 4, you must now move a pawn 4 spaces backwards.";
		else if (cardNum == 7)
			return "You drew a 7, you may now move a pawn 7 spaces forward, or split the 7 spaces between two pawns";
		else if (cardNum ==10)
			return "You drew a 10, you may now move a pawn 10 spaces forward or 1 space backward.";
		else if (cardNum == 11)
			return "You drew an 11, you may now move a pawn 11 spaces forward, or swap places with one opposing pawn.";
		else if (cardNum == 0)
			return "You drew a Sorry! card, you may now replace any opponents pawn with your own, thus sending the opponents pawn back to Start.";
		else 
			return "You drew a " + cardNum + ", you may now move a pawn " + cardNum + " spaces forward.";
	}	
	
	//Depending on what card is drawn, will limit the available 
	//actions in the game as to correspond with that card

	public SRRule getRules(int cardNum) {
		if (cardNum == 1) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"start");
			return rules;
		}else if (cardNum == 2) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"start");
			return rules;
		}else if (cardNum == 4) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"move");
			return rules;
		}else if (cardNum == 7) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"split");
			return rules;
		}else if (cardNum == 10) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"move");
			return rules;
		}else if (cardNum == 11) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"swap");
			return rules;
		}else if (cardNum == 0) {
			SRRule rules = new SRRule();
			rules.types(cardNum,"sorry");
			return rules;
		}else {
			SRRule rules = new SRRule();
			rules.types(cardNum,"move");
			return rules;
		}

	}

}
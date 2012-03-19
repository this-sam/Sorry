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
	
	public String toString() {
		if (cardNum == 1)
			return "You drew a 1, you may now move a pawn from Start or move a pawn 1 space forward.";
		else if (cardNum == 2)
			return "You drew a 2, you may now move a pawn from Start or move a pawn 2 spaces forward.  Then please draw another card.";
		else if (cardNum == 4)
			return "You drew a 4, you must now move a pawn 4 spaces backwards.";
		else if (cardNum == 7)
			return "You drew a 7, you may now move a pawn 7 spaces forward, or split the 7 spaces between two pawns";
		else if (cardNum == 10)
			return "You drew a 10, you may now move a pawn 10 spaces forward or 1 space backward.";
		else if (cardNum == 11)
			return "You drew an 11, you may now move a pawn 11 spaces forward, or switch places with one opposing pawn.";
		else if (cardNum == 13)
			return "You drew a Sorry! card, you may now replace any opponents pawn with your own, thus sending the opponents pawn back to Start.";
		else 
			return "You drew a " + cardNum + ", you may now move a pawn " + cardNum + " spaces forward.";
	}	
}

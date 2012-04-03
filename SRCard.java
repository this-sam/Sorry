import java.util.Arrays;

/**
* This class will create the cards to be used in the game.  Each card will inherit 
* rules related to that particular card from the SRRule class, it will also display 
* the text from each card to the user.
* 
* @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
*/

public class SRCard {
	
	//The following methods will set both the cardNum and the pictureName.
	public int cardNum;
	private String pictureName;
	
	public SRCard(int cardNum) {
		this.cardNum = cardNum;
		if(cardNum == 0)
			pictureName = "cardSorry.jpg";
		else
			pictureName = "card" + cardNum + ".jpg";
	}
	
	public void setcardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	
	public int getcardNum() {
		return cardNum;
	}
	
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	
	public String getPictureName() {
		return pictureName;
	}
	
	//Will return a string representation of each card
	//Will help when later reffering to pictures for each card in the GUI
	public String toString() {
		if(cardNum == 0)
			return "Sorry!";
		else 
			return "" + cardNum;
	}
	
	/**
	 * This function will return the text build into each card
	 * 
	 * @param
	 * @return string Text
	 */
	public String getText() {
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
	
	/**
	 * Take the rules associated with the card drawn and will display
	 * the game-play choices to the user.
	 * 
	 * @param
	 * @return Rule rule
	 */
	 public String[] getRules() {
		SRRule rules = new SRRule();
		String[] cardRule = rules.types(cardNum);
		return cardRule;
	}
	/*
	//Test function
	public static void main(String[] args){
		SRRule rule = new SRRule();
		//Simple loop to create each of the 13 different cards
		for(int i=0;i<13;i++){
			SRCard card = new SRCard(i);
			System.out.println(card); //Tests the toString function
			System.out.println(card.getText()); //Tests the getText function
			System.out.println(Arrays.toString(card.getRules())); //Tests the getRules function
		}
	}
	*/
	 
}
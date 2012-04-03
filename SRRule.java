import java.util.Arrays;

/**
* This class will implement rules and actions for each of the cards to be used in the Sorry! game.
* It allows for instructions to be passed in a modular way.
* 
* @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
*/

public class SRRule {
	
	//Number of rules to be associated with given rule
	public int numMoves; //Initialize numMoves
	
	/**
	 * This function will create rules whose type will refer to the different 
	 * kinds of events that will occur in the game.
	 * 
	 * @param cardNum
	 * @param type
	 * @return 
	 */
	 public String[] types(int cardNum) {
		numMoves = 0; //Initialize to 0
		if (cardNum == 1) {
			numMoves = cardNum; //The number of moves will correlate to the card number
			String[] rule = {""+numMoves,"move"}; //Create a string array with the number of moves allowed, and the type
			return rule;
		}else if (cardNum == 2) {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"start, draw again"};
			return rule;
		}else if (cardNum == 4) {
			numMoves = cardNum;
			String[] rule = {"-"+numMoves,"move backwards"};
			return rule;
		}else if (cardNum == 7) {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"split"};
			return rule;
		}else if (cardNum == 10) {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"move"};
			return rule;
		}else if (cardNum == 11) {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"swap"};
			return rule;
		}else if (cardNum == 0) {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"sorry"};
			return rule;
		}else {
			numMoves = cardNum;
			String[] rule = {""+numMoves,"move"};
			return rule;
		}
	}
	/*
	//Test function
	public static void main(String[] args){
		SRRule rule = new SRRule();
		//Simple loop to make sure each of the cards is covered
		for(int i=0;i<13;i++){
			rule.numMoves = i;
			System.out.println(Arrays.toString(rule.types(rule.numMoves))); //Tests SRRule's types function
		}
	}
	*/
	
}
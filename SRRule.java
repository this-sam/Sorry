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
	public int numTurns;
	public boolean isSorry;
	public boolean canStart;
	public boolean canSplit;
	public boolean shiftBackwards;
	public boolean canSwitch;
	
	public SRRule(){
		this.numMoves = 0;
		this.numTurns = 1;
		this.isSorry = false;
		this.canStart = false;
		this.canSplit = false;
		this.shiftBackwards = false;
		this.canSwitch = false;
	}
	
	public SRRule(int numMoves){
		this();
		if (numMoves == 7){
			this.canSplit = true;
			this.numMoves = 7;
		}
		else if (this.numMoves == 10){
			this.shiftBackwards = true;
			this.numMoves = 11;
		}
		else{
			this.numMoves = numMoves;
		}
	}
	public SRRule(String ruleStr){
		this();
		
		if (ruleStr == "START"){
			this.canStart = true;
		}
		else if (ruleStr == "SORRY"){
			this.isSorry = true;
		}
		else if (ruleStr == "SWAP"){
			this.canSwitch = true;
		}
		else if (ruleStr == "DRAW"){
			this.numMoves = 2;
		}
		else{
			throw new Error("Uh oh... looks like '"+ruleStr+"' is a new rule...");
		}
	}
//	public SRRule(int numMoves){
//		this.numMoves = numMoves;
//		
//		if (numMoves == 4){
//			this.canSplit = true;
//		}
//		else{
//			this.canSplit = false;
//		}
//		
//		if (numMoves == 1 || numMoves == )
//	}


	
	
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
	 
	
	
}
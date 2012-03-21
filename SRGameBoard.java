	/* GameBoard.java
	 * This class will handle all logic involving the actions, and movements involved with the 
	 * game Sorry! as well as storing all other associated objects and various statistics about 
	 * the game thus far.  This class includes a representation of the track, deck, pawns, and 
	 * records information about the game to be logged as statistics.
	 */

import java.util.Date; //for gameplay length


/**
 * This class will handle all logic involving the actions, and movements
 * involved with the game Sorry! as well as storing all other associated objects and
 * various statistics about the game thus far. This class includes a representation of
 * the track, deck, pawns, and records information about the game to be logged as
 * statistics.
 *
 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
 */
public class SRGameBoard {
	
	//constants
	public static int trackLength = 60;
	public static int[] safetyZoneIndex = {60,66};
	public static int[] safetyZoneEntrance = {2, 33};
	public static int[] startIndex = {4,37};
	public static int slideLength = 4;
	public static int[] slideIndex = {1,9, 16, 24, 31, 39, 46, 54}; 
	
	
	
	//gameplay	
	public SRSquare[] track = new SRSquare[72];	//squares that make up the regular track, safety zones, and home squares
	public SRSquare[] startSquares  = new SRSquare[2];	//indexes into the track representing where players may move their pawns into play
	public SRDeck deck;	//Deck object used in this game
	public SRPawn[] pawns  = new SRPawn[8];	//8 pawns used in the game
	
	
	//statistics
	public Date startTime;
	public int elapsedTime; //elapsed time in seconds
	public int numTurns; 	//turns taken
	public int numBumps; 	//times player bumped another player
	public int numStories;	//successful uses of Sorry! cards
	public String cpuStyle;	//either nice or mean, how the computer was set to play
	public int playerPawnsHome;	//number of pawns player got home
	public int playerDistanceTraveled; //total number of squares traveled by the human player
	public int cpuDistanceTraveled;	//"" "" "" "" computer
	

	public SRGameBoard(){
		//start the track
		for (int i=0;i<track.length;i++){
			//make 'em
			track[i] = new SRSquare();
			//check if this one is slippery
			for (int j=0; j<this.slideIndex.length; j++){
				//take appropriate action if it is
				if (i == j){
					track[j].slideLength = this.slideLength;
				}
			}
		}
		
		//create startSquares
		for (int i=0;i<this.startSquares.length; i++){
			this.startSquares[i] = new SRStartSquare();
		}
		
		//shuffle the deck
		//this.deck.shuffle();
		
		
		//make some pawns
		for (int i=0; i<this.pawns.length;i++){
			if (i<4)
				this.pawns[i] = new SRPawn(0);
			else
				this.pawns[i] = new SRPawn(1);
		}
		
		this.cpuStyle = "easy";
		this.startTime = new Date();
		
		System.out.println("GameBoard initialized.");//for testing
	}
	
	//card methods:
	public SRCard drawCard(){
		return deck.drawCard();
	}
	
	//movement methods:
	

	public int[] findMoves(SRPawn pawn, SRCard card){
		return null;
	}

	/**
	 * This function handles the actual movement of the pawn, and the resultant bumping
	 * and sliding that may occur. If a pawn is moved to a space with another pawn, the
	 * second pawn will be moved back to Start. If the pawn is moved to a space with a
	 * slide, the pawn will be moved a second time the distance of the slide.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 */
	public void movePawn(SRPawn pawn, int location){
		for (int i=0;i<this.pawns.length; i++){
			if (pawns[i].getTrackIndex() == location){
				pawns[i].bump();
			}
		}
		
		//move the pawn and slide too if we need it.
		pawn.setTrackIndex(location+this.track[location].getSlideLength());
	}
	
	public void bumpPawn(SRPawn pawn){
		pawn.setOnHome(true);
	}
	
	public void startPawn(SRPawn pawn){
		pawn.setOnStart(true);
	}
	
	public boolean hasWon(int player){
		return false;
	}
	
	private void endGame(){
		Date endTime = new Date();
		//something like:
		//elapsedTime = endTime - this.startTime
	}
	
	public static void main(String[] args){
		SRGameBoard gb = new SRGameBoard();
	}
}

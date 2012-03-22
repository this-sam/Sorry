	/* GameBoard.java
	 * This class will handle all logic involving the actions, and movements involved with the 
	 * game Sorry! as well as storing all other associated objects and various statistics about 
	 * the game thus far.  This class includes a representation of the track, deck, pawns, and 
	 * records information about the game to be logged as statistics.
	 */

import java.util.Date; //for gameplay length
import java.util.Random;


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
	//debug
	private static boolean debug = true;
	
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
		
		
		//testing:
		if (this.debug){
			System.out.println("GameBoard initialized.");//for testing
		}
		
	}
	
	//card methods:
	public SRCard drawCard(){
		return deck.drawCard();
	}
	
	//movement methods:
	
	/**
	 * This function uses the Pawn object’s current location, as well as the Rules associated
	 * with the Card to determine where on the board the Pawn may move to. These
	 * locations on the board are returned as an array of integers, representing indices
	 * into the GameBoard.track object.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 */
	public int[] findMoves(SRPawn pawn, SRCard card){
		return null;
	}

	/** 
	 * This function handles the actual movement of the pawn, and the resultant bumping
	 * and sliding that may occur. If a pawn is moved to a space with another pawn, the
	 * second pawn will be moved back to Start. If the pawn is moved to a space with a
	 * slide, the pawn will be moved a second time the distance of the slide.
	 * 
	 * Assumes the pawn is on the normal track.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 */
	public void movePawnTo(SRPawn pawn, int location){
		this.movePawnTo(pawn, location, false);

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
	public void movePawnTo(SRPawn pawn, int location, boolean isSafety){
		if (this.debug){
			System.out.println("Moved player"+ pawn.player+" pawn4 "+(location-pawn.getTrackIndex())+
					           " squares from "+pawn.trackIndex+" to "+location+".");
		}
		//make sure they don't go off the board.
		if (isSafety){
			if (location > this.track.length)
				location = location%this.track.length;
		}
		else{
			location %=SRGameBoard.trackLength;
		}
		
		//bump (only bump the pawns of the opposing player)
		for (int i=pawn.player;i<this.pawns.length; i++){
			boolean sameSquare = pawns[i].getTrackIndex() == location;
			//bump the opponent
			if (sameSquare && pawn.player != pawns[i].player){
				pawns[i].bump();
				if (this.debug){
					System.out.println("Bump!");
				}
			}
			//but don't move if you'll land on yourself
			else if (sameSquare){
				location = pawn.trackIndex;
			}
		}

		//move the pawn and slide too if we need it.
		pawn.setTrackIndex(location);
	}
	
	/**
	 * Determines the location that the move will take the pawn to, and calls
	 * the movePawnTo function.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 */
	public void movePawn(SRPawn pawn, int distance){		
		//move the pawn and slide too if we need it.
		this.movePawnTo(pawn, pawn.getTrackIndex()+distance);
	}
	
	/**
	 * Determines the location that the move will take the pawn to, and calls
	 * the movePawnTo function.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 */
	public void movePawn(SRPawn pawn, int distance, boolean isSafety){		
		//move the pawn and slide too if we need it.
		this.movePawnTo(pawn, pawn.getTrackIndex()+distance, isSafety);
	}
	
	/**
	 * This function moves Pawn pawn back onto its start square.
	 * 
	 * @param pawn
	 */
	public void bumpPawn(SRPawn pawn){
		this.movePawnTo(pawn, this.startIndex[pawn.player]);
	}
	
	/**
	 * This function moves Pawn pawn from its start square onto the square directly
	 * adjacent to the start square.
	 * 
	 * @param pawn
	 */
	public void startPawn(SRPawn pawn){
		this.movePawnTo(pawn, this.startIndex[pawn.player]);
	}
	
	/**
	 * This function checks to see whether a player has managed to place all 4 of his/her
	 * pawns on his/her start square and returns True if this is the case. Otherwise it
	 * returns False.
	 * 
	 * @param player
	 * @return
	 */
	public boolean hasWon(int player){
		int playerPawnIndex = player*4; //0 if player 0, 4 if player 1
		
		//loop through pawns, if any not on home the player hasn't won.
		for (int i=playerPawnIndex; i<playerPawnIndex+4; i++){
			if (this.pawns[i].onHome == false){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Performs after-game clean up and statistics prep.
	 */
	private void endGame(){
		Date endTime = new Date();
		//something like:
		//elapsedTime = endTime - this.startTime
	}
	
	public static void main(String[] args){
		SRGameBoard gb = new SRGameBoard();
		Random rand = new Random();
		//simulate a bunch of random moves
		for (int i=0;i<1000;i++){
			boolean special = false;
			for (int j=0;j<8;j++){
				if (j==7){
					special=true;
				}
				gb.movePawn(gb.pawns[j], rand.nextInt(12), special);
			}
		}
			
	}
}

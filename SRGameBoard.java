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
	//debug
	private static boolean debug = true;
	
	//constants
	public static final int trackLength = 56;
	public static final int safetyLength = 5;
	public static final int[] safetyZoneIndex = {60,66};
	public static final int[] safetyZoneEntrance = {2, 29};
	public static final int[] startIndex = {4,32};
	public static final int slideLength = 4;
	public static final int[] slideIndex = {1,9, 15, 23, 29, 37, 43, 51}; 
	
	
	
	//gameplay	
	public SRSquare[] track = new SRSquare[66];	//squares that make up the regular track, safety zones, and home squares
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
			for (int j=0; j<SRGameBoard.slideIndex.length; j++){
				//take appropriate action if it is
				if (i == j){
					track[j].slideLength = SRGameBoard.slideLength;
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
			this.pawns[i].setID(i%4);
		}
		
		this.cpuStyle = "easy";
		this.startTime = new Date();
		
		
		//testing:
		if (SRGameBoard.debug){
			System.out.println("GameBoard initialized.");//for testing
		}
		
	}
	//getters
	public SRPawn getPlayerPawn(int player, int number){
		return this.pawns[(player*4+number)];
	}
	
	public SRSquare getSquareAt(int index){
		if (index < this.track.length){
			return this.track[index];
		}
		else{
			return new SRSquare(-1, -1, false);
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
		int numMoves = card.getcardNum();
		
		int [] locations = new int [numMoves]; //for now traeat card number like number of moves
		int playerEntrance = SRGameBoard.safetyZoneEntrance[pawn.getPlayer()];
		
		
		//if this pawn is moving from the normal board:
		
			
			//check if it won't pass a safety zone first (bigger case)
			boolean willPassSafety = (pawn.trackIndex < SRGameBoard.trackLength && 
					  				  pawn.getTrackIndex() <= playerEntrance && 
					                  playerEntrance < playerEntrance+6);             
			if (!willPassSafety){
				for (int i=0; i<numMoves; i++){
					locations[i] = pawn.trackIndex+i+1;
				}
				return locations;
			}
			else{
				int [] returns = {pawn.trackIndex};
				return returns;
			}
		
		//LOGIC SHOULD ALL BE WITHIN GET MOVES AND RETURN ABSOLUTE INTS --> WAAAAY EASIER
		//moving inside safety zone
		//if (pawn.safetyIndex >= 0){
			//if positive
			//if negative
		//}
		//moving into safety zone
		//else{
			//if positive
			//if negative
		//}
		
	}

	/* 
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
	
	public void movePawnTo(SRPawn pawn, int location){
		this.movePawnTo(pawn, location, false);

	} 
	Don't think isSafety is necessary.  -SB
	*/
	
	/**
	 * This function handles the actual movement of the pawn, and the resultant bumping
	 * and sliding that may occur. If a pawn is moved to a space with another pawn, the
	 * second pawn will be moved back to Start. If the pawn is moved to a space with a
	 * slide, the pawn will be moved a second time the distance of the slide.
	 * 
	 * Return a boolean true if move successful or false otherwise.
	 * 
	 * @param pawn
	 * @param card
	 * @return boolean
	 */
	public boolean movePawnTo(SRPawn pawn, int location){
		//int location = (pawn.getTrackIndex()+distance)%SRGameBoard.trackLength;
		//check for starting pawns
		if (location >= 0 && location < this.track.length){
			location += this.track[location].getSlideLength();
		}
		//check for pawns going to start
		else if (location < 0){
			pawn.setOnStart(true);
			return true;
		}
		//check for pawns going out of bounds
		else if (location > SRGameBoard.trackLength){
			return false;
		}
		//check for pawns that are going home
		else if (this.track[location].isHome){
			pawn.setOnHome(true);
			return true;
		}
		//check for pawns that are already home?  do we need to?  why not.
		else if (pawn.isOnHome()){
			return false;
		}
		
		//bump (only bump the pawns of the opposing player)
		for (int i=pawn.player;i<this.pawns.length; i++){
			boolean sameSquare = pawns[i].getTrackIndex() == location;
			//bump the opponent
			if (sameSquare && pawn.player != pawns[i].player){
				pawns[i].bump();
				if (SRGameBoard.debug){
					System.out.print("Bumped a piece and ");
				}
			}
			//but don't move if you'll land on yourself
			else if (sameSquare){
				return false;
			}
		}

		if (SRGameBoard.debug){
			System.out.println("Moved player"+pawn.player+" pawn4 "+(location-pawn.getTrackIndex())+
					           " squares from "+pawn.trackIndex+" to "+location+".");
		}
		
		//move the pawn and slide too if we need it.
		pawn.setTrackIndex(location);
		return true;
	}
	
	/**
	 * Determines the location that the move will take the pawn to, and calls
	 * the movePawnTo function.
	 * 
	 * @param pawn
	 * @param card
	 * @return
	 * 
	 */
	
	public void movePawn(SRPawn pawn, int distance){		
		int location = pawn.getTrackIndex()+distance;
		this.movePawnTo(pawn, location);// isSafety);
	} 
	
	
	/*
	 * Determines the location that the move will take the pawn to, and calls
	 * the movePawnTo function.
	 * 
	 * @param pawn
	 * @param card
	 * @param isSafety
	 * @return
	 
	public void movePawn(SRPawn pawn, int distance, boolean isSafety){	
		int location = pawn.getTrackIndex()+distance;
		this.movePawnTo(pawn, location); isSafety);
		
	}
	Don't think isSafety is necessary.  -SB
	*/
	
	/**
	 * This function moves Pawn pawn back onto its start square.
	 * 
	 * @param pawn
	 */
	public void bumpPawn(SRPawn pawn){
		this.movePawnTo(pawn, SRGameBoard.startIndex[pawn.player]);
	}
	
	/**
	 * This function moves Pawn pawn from its start square onto the square directly
	 * adjacent to the start square.
	 * 
	 * @param pawn
	 */
	public void startPawn(SRPawn pawn){
		this.movePawnTo(pawn, SRGameBoard.startIndex[pawn.player]);
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
}

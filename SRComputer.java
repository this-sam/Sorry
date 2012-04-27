/**
 *This class will handle all of the AI for the game Sorry!  It will take a card that is drawn,
 *find all of the possible moves for each pawn based on that card, and will then choose the 
 *optimal move based on the options.  Will also distinguish between a "nice" and "mean"
 *computer.
 *
 *@author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang.
 */

import java.util.Random;

public class SRComputer{

	public SRGameBoard board;
	public SRDeck deck;
	public SRCard currentCard;
	public SRPawn pawn;
	
	public int computerType;
		
	public SRComputer(int computerType){

	}
	
	public void setComputerType(int computerType) {
		this.computerType = computerType;
	}

	public int getComputerType() {
		return computerType;
	}
	
	public int findMove(SRGameBoard board, SRCard card) {
		Random rand = new Random();
		int i = rand.nextInt(40);
		System.out.println(i);
		return i;
	}
	
	/**
	 * This function uses the currentCard to find, based on that card, which 
	 * pawn can get the closest to home
	 * 
	 * @param currentCard
	 * @return
	 */
	public void nearestToHome(SRCard currentCard){
		int nearest = 100;
		int newNearest = 100;
		int [] pawnMoves;
		int [] nearestMove;
		SRPawn closePawn = board.pawns[0];
		nearestMove = new int[4];
		for(int i=0;i<=3;i++){
			pawnMoves = board.findMoves(board.pawns[i], currentCard);
			for(int j=0;j<pawnMoves.length;j++){
				if(pawnMoves[j] == 61){
					nearest = pawnMoves[j];
				}else{
					int d = 61 - pawnMoves[j];
					if(d < nearest){
						nearest = d;
					}
				}
			}
			nearestMove[i] = nearest;
			int finalCalc = 61 - nearestMove[i];
			for(int k=0;k<=3;k++){
				if(finalCalc <= nearestMove[k]){
					newNearest = finalCalc;
					closePawn = board.pawns[i];
				}
			}
		}
		if(board.pawns[0].getTrackIndex() == 4){
			board.movePawnTo(board.pawns[0], newNearest);
		}else if(board.pawns[1].getTrackIndex() == 4){
			board.movePawnTo(board.pawns[1], newNearest);
		}else if(board.pawns[2].getTrackIndex() == 4){
			board.movePawnTo(board.pawns[2], newNearest);
		}else if(board.pawns[3].getTrackIndex() == 4){
			board.movePawnTo(board.pawns[3], newNearest);
		}else{
			board.movePawnTo(closePawn, newNearest);
		}
	}
	
	/**
	 * This function uses the currentCard to find, based on that card, which 
	 * pawn should swap places with an opponent pawn
	 * 
	 * @param currentCard
	 * @return
	 */
	public void doSwap(SRCard currentCard){
		int cards = currentCard.getcardNum();
		int [] pawnMoves;
		for(int i=0;i<=3;i++){
			pawnMoves = board.findMoves(board.pawns[i], currentCard);
			for(int j=0;j<pawnMoves.length;j++){
				for(int L=4;L<=7;L++){
					if(pawnMoves[j] == board.pawns[L].getTrackIndex()){
						board.movePawnTo(board.pawns[i], pawnMoves[j]);
					}else{
						nearestToHome(currentCard);
					}
				}
			}
		}
	}
	
	/**
	 * This function uses the currentCard to find, based on that card, which 
	 * pawn should be moved out of start, onto the board
	 * 
	 * @param currentCard
	 * @return
	 */
	public void moveFromStart(SRCard currentCard){
		int [] pawnMoves;
		for(int i=0;i<=3;i++){
			pawnMoves = board.findMoves(board.pawns[i], currentCard);
			for(int j=0;j<pawnMoves.length;j++){
				board.movePawnTo(board.pawns[i], 4);
			}
		}
	}
	
	/**
	 * This function uses the currentCard to find, based on that card, if 
	 * any pawns can bump an opponents pawn
	 * 
	 * @param currentCard
	 * @return bool
	 */
	public boolean canBump(SRCard currentCard){
		int [] pawnMoves;
		int a = 0;
		for(int i=0;i<=3;i++){
			pawnMoves = board.findMoves(board.pawns[i], currentCard);
			for(int j=0;j<pawnMoves.length;j++){
				for(int L=4;L<=7;L++){
					if(pawnMoves[j] == board.pawns[L].getTrackIndex()){
						a=1;
					}else{
						a=0;
					}
				}
			}
		}
		if(a == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * This function uses the currentCard to make the best possible move for 
	 * the computer, based on the level of difficulty chosen by the user.
	 * 
	 * @param SRGameBoard
	 * @param SRCard
	 * @return
	 */
	 
	public boolean computerPlay(SRGameBoard board, SRCard currentCard){
		int cards = currentCard.getcardNum();
		if(computerType == 0){	
			if(cards==0){
				doSwap(currentCard);
			}else	if(cards	==	1){
				if(pawn.isOnHome()){
					moveFromStart(currentCard);		
				}else{
					nearestToHome(currentCard);
				}	
			}else	if(cards	==	2){
				if(pawn.isOnHome()){
					moveFromStart(currentCard);	
				}else{
					nearestToHome(currentCard);
				}
			}else	if(cards	==	7){				
				nearestToHome(currentCard);
			}else	if(cards	==	10){
				nearestToHome(currentCard);		
			}else	if(cards	==	11){
				doSwap(currentCard);				
			}else{
					nearestToHome(currentCard);
			}
		}else if(computerType == 1){
			if(canBump(currentCard) == true){
				doSwap(currentCard);
			}else if(cards==0){
				doSwap(currentCard);
			}else	if(cards	==	1){
				if(pawn.isOnHome()){
					moveFromStart(currentCard);		
				}else{
					nearestToHome(currentCard);
				}	
			}else	if(cards	==	2){
				if(pawn.isOnHome()){
					moveFromStart(currentCard);	
				}else{
					nearestToHome(currentCard);
				}
			}else	if(cards	==	7){				
				nearestToHome(currentCard);
			}else	if(cards	==	10){
				nearestToHome(currentCard);		
			}else	if(cards	==	11){
				doSwap(currentCard);					
			}else{
					nearestToHome(currentCard);
			}
		}
		return true;
	}
}
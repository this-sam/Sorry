/**
 *This class will handle all of the AI for the game Sorry!  It will take a card that is drawn,
 *find all of the possible moves for each pawn based on that card, and will then choose the 
 *optimal move based on the options.  Will also distinguish between a "nice" and "mean"
 *computer.
 *
 *@author Sam Brown, Taylor Krammen, and Yucan Zhang.
 */

public class SRComputer{

    public SRGameBoard board;
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
    
    /**
     * This function uses the board and the currentCard to find, 
     * based on that card, which pawn would be the best to move.
     * pawn can get the closest to home
     * 
     * @param board
     * @param currentCard
     * @return pawn
     */
    public SRPawn findPawn(SRGameBoard board, SRCard currentCard){
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
        int cards = currentCard.getcardNum();
        if(computerType == 0){  
            if(cards==0 || cards==11){
                doSwapPawn(board, currentCard);
            }else   if(cards==1 || cards==2){
                moveFromStartPawn(board, pawn, currentCard);
            }else{
                return closePawn;
            }                   
        }else if(computerType == 1){
            if(canBump(board, currentCard) == true){
                doSwapPawn(board, currentCard);
            }else if(cards==0 || cards==11){
                doSwapPawn(board, currentCard);
            }else   if(cards==1 || cards==2){
                moveFromStartPawn(board, pawn, currentCard);
            }else{
                return closePawn;
            }
        }
        return closePawn;
    }
    
    /**
     * This function uses the board and the currentCard to find, 
     * based on that card, which pawn can get the closest to home
     * 
     * @param board
     * @param currentCard
     * @return location
     */
    public int findMove(SRGameBoard board, SRCard currentCard){
        int nearest = 100;
        int newNearest = 100;
        int [] pawnMoves;
        int [] nearestMove;
        int newLocation = pawn.getTrackIndex();
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
                    newLocation = nearestMove[i];
                }
            }
        }
        int cards = currentCard.getcardNum();
        if(computerType == 0){  
            if(cards==0 || cards==11){
                doSwapMove(board, currentCard);
            }else   if(cards==1 || cards==2){
                moveFromStartMove(board, pawn, currentCard);
            }else{
                return newLocation;
            }                   
        }else if(computerType == 1){
            if(canBump(board, currentCard) == true){
            doSwapMove(board, currentCard);
            }else if(cards==0 || cards==11){
                doSwapMove(board, currentCard);
            }else   if(cards==1 || cards==2){
                moveFromStartMove(board, pawn, currentCard);
            }else{
                return newLocation;
            }
        }
        return newLocation;
    }

    /**
     * This function uses the board and currentCard to find, based on that card, 
     * which pawn should swap places with an opponent pawn
     * 
     * @param board
     * @param currentCard
     * @return pawn
     */
    public SRPawn doSwapPawn(SRGameBoard board, SRCard currentCard){
        int cards = currentCard.getcardNum();
        int [] pawnMoves;
        for(int i=0;i<=3;i++){
            pawnMoves = board.findMoves(board.pawns[i], currentCard);
            for(int j=0;j<pawnMoves.length;j++){
                for(int L=4;L<=7;L++){
                    if(pawnMoves[j] == board.pawns[L].getTrackIndex()){
                        return board.pawns[j];
                    }else{
                        findPawn(board, currentCard);
                    }
                }
            }
        }
        return board.pawns[-1];
    }
    
    /**
     * This function uses the board and currentCard to find, based on that card, 
     * what location the pawn should swap to.
     * 
     * @param board
     * @param currentCard
     * @return location
     */
    public int doSwapMove(SRGameBoard board, SRCard currentCard){
        int cards = currentCard.getcardNum();
        int [] pawnMoves;
        pawnMoves = new int[0];
        for(int i=0;i<=3;i++){
            pawnMoves = board.findMoves(board.pawns[i], currentCard);
            for(int j=0;j<pawnMoves.length;j++){
                for(int L=4;L<=7;L++){
                    if(pawnMoves[j] == board.pawns[L].getTrackIndex()){
                        return pawnMoves[j];
                    }else{
                        findMove(board, currentCard);
                    }
                }
            }
        }
        return pawnMoves[0];
    }
    
    /**
     * This function uses the board, pawn, and currentCard to find, based on that card, which 
     * pawn should be moved out of start, onto the board
     * 
     * @param board
     * @param pawn
     * @param currentCard
     * @return pawn
     */
    public SRPawn moveFromStartPawn(SRGameBoard board, SRPawn pawn, SRCard currentCard){
        int [] pawnMoves;
        for(int i=0;i<=3;i++){
            if(board.pawns[i].getTrackIndex() == -1){
                return board.pawns[i];
            }else{
                findPawn(board, currentCard);
            }                   
        }
        return board.pawns[-1];
    }
    
    /**
     * This function uses the board, pawn, and currentCard to find, based on that card, which 
     * location the pawn should be moved to
     * 
     * @param board
     * @param pawn
     * @param currentCard
     * @return
     */
    public int moveFromStartMove(SRGameBoard board, SRPawn pawn, SRCard currentCard){
        int [] pawnMoves;
        for(int i=0;i<=3;i++){
            if(board.pawns[i].getTrackIndex() == -1){
                return 4;
            }else{
                findMove(board, currentCard);
            }                   
        }
        return 4;
    }
    
    /**
     * This function uses the board and currentCard to find, based on that card, if 
     * any pawns can bump an opponents pawn
     * 
     * @param board
     * @param currentCard
     * @return bool
     */
    public boolean canBump(SRGameBoard board, SRCard currentCard){
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
}
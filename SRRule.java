public class SRRule {
	
	public int numMoves;
	
	public SRRule() {
	numMoves = 0;
	}
	
	public 
	
	numMoves == cardNum;
	
	if (type == move)
		//allow player to move cards value length from current position
		//move = SRPawn.getPosition + cardNum
	else if (type == sorry)
		//set player x's pawn = track index of player y's pawn
		//send player y's pawn back to start
	else if (type == start)
		//allow user to place on pawn on start
		SRPawn.setOnStart();
	else if (type == drawAgain)
		//allow player to play this turn, and then get another turn
		SRCard.drawCard();
	else if (type == split)
		//allow user to split number of moves between two pawns
	else if (type == swap)
		//set player x's pawn = track index of player y's pawn
		//set player y's pawn = old track index of player x's pawn
}

/**
 *SRStartSquare is a special type of square (the Start square)
 *which has a slide length of 0 and can hold up to 4 pawns.
 */

public class SRStartSquare extends SRSquare {
	
	public SRStartSquare() {
		slideLength = 0;
		maxPawns = 4;
		isHome = false;
		isStart = true;
	}
	
	//**********TEST FUNCTION**************
	public static void main(String[] args) {
	
		SRStartSquare startSquare = new SRStartSquare();
		
		System.out.print("-Start Square-\nslideLength: " + startSquare.getSlideLength() + "\nmaxPawns: " + startSquare.getMaxPawns() +
							  "\nisHome: " + startSquare.isHome() + "\nisStart: " + startSquare.isStart());
	
		System.exit(0);
	}
}
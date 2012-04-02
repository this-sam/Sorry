/**
 *SRHomeSquare is a special type of square (the Home square)
 *which has a slide length of 0 and can hold up to 4 pawns.
 */

public class SRHomeSquare extends SRSquare {

	public SRHomeSquare() {
		slideLength = 0;
		maxPawns = 4;
		isHome = true;
		isStart = false;
	}
	
	//**********TEST FUNCTION**************
	public static void main(String[] args) {
	
		SRHomeSquare homeSquare = new SRHomeSquare();
		
		System.out.print("-Home Square-\nslideLength: " + homeSquare.getSlideLength() + "\nmaxPawns: " + homeSquare.getMaxPawns() +
							  "\nisHome: " + homeSquare.isHome() + "\nisStart: " + homeSquare.isStart());
	
		System.exit(0);
	}
}
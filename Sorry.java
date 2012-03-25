import java.util.Random;


/**
 * Sorry game driver class.
 *
 * @author Sam Brown, Caleb Cousins, Taylor Krammen, and Yucan Zhang
 */
public class Sorry {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		//first we need a game board
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

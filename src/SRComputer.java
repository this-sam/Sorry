import java.util.Random;


public class SRComputer {

	public int findMove(SRGameBoard board, SRCard card) {
		Random rand = new Random();
		int i = rand.nextInt(40);
		System.out.println(i);
		return i;
	}
	
}

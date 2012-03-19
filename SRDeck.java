import java.util.*;

public class SRDeck {

	public SRCard[] drawPile;
	public SRCard[] discardPile;
	
	public ArrayList<SRCard> deck;
	
	public SRDeck() {
		deck = new ArrayList<SRCard>(45);
		for(int i = 0; i<4; i++) {
			deck.add(new SRCard(1));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(2));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(3));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(4));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(5));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(7));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(8));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(10));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(11));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(12));
		}for(int i = 0; i<3; i++) {
			deck.add(new SRCard(13));
		}
	}
	
	public void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < deck.size(); i++) {
			int j = rand.nextInt(deck.size());
			SRCard temp = deck.get(i);
			deck.set(i, deck.get(j));
			deck.set(j, temp);			
		}
	}		
	
	public SRCard drawCard() {
		SRCard drawCard = deck.get(0);
		deck.remove(0);
		return drawCard;		
	}
	
	public boolean isEmpty() {
		if (deck.size() == 0) {
		return true;
		}
		else return false;
	}
}